package me.coderleo.chitchat.server;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.packets.server.PacketConversationList;
import me.coderleo.chitchat.common.packets.server.PacketUserList;
import me.coderleo.chitchat.common.packets.universal.PacketUserJoin;
import me.coderleo.chitchat.common.packets.universal.PacketUserLeave;
import me.coderleo.chitchat.common.util.LogUtil;
import me.coderleo.chitchat.server.data.SqlDataManager;
import me.coderleo.chitchat.server.managers.ConversationManager;
import me.coderleo.chitchat.server.models.Conversation;
import me.coderleo.chitchat.server.models.ConversationData;
import me.coderleo.chitchat.server.packethandlers.PacketHandlerManager;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class User extends AbstractUser
{
    private SecretKey key;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ExecutorService executor = Executors.newFixedThreadPool(100);

    User(final Socket socket, final SecretKey key)
    {
        try
        {
            this.key = key;
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());

            outputStream.writeObject(key);

            Packet firstPacket = decrypt(inputStream.readObject());

            PacketHandlerManager.getInstance().handle(firstPacket, this, () ->
            {
                ConversationManager.getInstance().getAllUsers().stream()
                        .filter(u -> !u.equals(this))
                        .forEach(u -> u.send(new PacketUserJoin(this)));
                send(new PacketUserList(ConversationManager.getInstance().getAllUsers()));

                List<Conversation> conversations = SqlDataManager.getInstance().getForUser(this);
                List<me.coderleo.chitchat.common.models.ConversationData> data = conversations.stream().map(Conversation::toLegacyData)
                        .collect(Collectors.toList());

                send(new PacketConversationList(data));
            });

            new Thread(() ->
            {
                while (true)
                {
                    try
                    {
                        Packet packet = decrypt(inputStream.readObject());
                        PacketHandlerManager.getInstance().handle(packet, this);
                    } catch (EOFException e)
                    {
                        try
                        {
                            socket.close();
                        } catch (IOException e1)
                        {
                            e1.printStackTrace();
                        }

                        ConversationManager.getInstance().removeUser(this);

                        ConversationManager.getInstance().getAllUsers().stream()
                                .filter(u -> !u.equals(this))
                                .forEach(u -> u.send(new PacketUserLeave(this)));

                        break;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e)
        {
            if (e instanceof EOFException)
            {
                return;
            }

            System.out.println("Removing " + getUsername() + " because of " + e);
            ConversationManager.getInstance().removeUser(this);

            ConversationManager.getInstance().getAllUsers().stream()
                    .filter(u -> !u.equals(this))
                    .forEach(u -> u.send(new PacketUserLeave(this)));
        }
    }

    /**
     * Send a packet to the user.
     *
     * @param packet
     */
    public void send(Packet packet)
    {
        try
        {
            outputStream.writeObject(packet);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Packet decrypt(Object o)
    {
        try
        {
            SealedObject so = (SealedObject) o;

            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);

            return (Packet) so.getObject(c);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
