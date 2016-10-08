package me.coderleo.chitchat.client;

import me.coderleo.chitchat.client.packethandlers.PacketHandlerManager;
import me.coderleo.chitchat.client.utils.Callback;
import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.packets.annotations.ServerSided;
import me.coderleo.chitchat.common.util.LogUtil;

import javax.crypto.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ServerConnector
{
    private static ServerConnector ourInstance = new ServerConnector();

    public static ServerConnector getInstance()
    {
        return ourInstance;
    }

    private String ip;
    private Socket socket;
    private SecretKey key;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private ServerConnector()
    {
    }

    public boolean checkConnect()
    {
        try
        {
            new Socket(ip.split(":")[0], getPort()).close();

            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    void connect(Callback callback)
    {
        try
        {
            socket = new Socket(ip.split(":")[0], getPort());

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            key = (SecretKey) inputStream.readObject();

            LogUtil.info("Got key: %s", key);

            if (callback != null) callback.run();

            new Thread(() ->
            {
                while (true)
                {
                    try
                    {
                        Packet packet = (Packet) inputStream.readObject();
                        LogUtil.info("Received packet from server: %s", packet);

                        handlePacket(packet);
                    } catch (EOFException e)
                    {
                        LogUtil.error("Lost connection to server.");

                        try
                        {
                            socket.close();
                        } catch (IOException e1)
                        {
//                            LogUtil.error("We can't close a socket? What?!");
                            e1.printStackTrace();
                        }

                        socket = null;
                        Main.getInstance().showLoginPanel();

                        break;
                    } catch (SocketException e)
                    {
                        LogUtil.error("Socket was closed.");
                        socket = null;
                        break;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private int getPort()
    {
        int port = 8181;

        if (ip.split(":").length > 1)
        {
            try
            {
                port = Integer.parseInt(ip.split(":")[1]);
            } catch (NumberFormatException ignored)
            {
            }
        }

        return port;
    }

    /**
     * Close the socket and stop receiving data.
     */
    public void disconnect()
    {
        if (socket == null) return;

        try
        {
            socket.close();
            socket = null;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Send an encrypted packet to the server.
     *
     * @param packet The packet to send
     */
    public void sendPacket(Packet packet)
    {
        if (socket == null)
            connect(null);
        if (packet.getClass().isAnnotationPresent(ServerSided.class))
        {
            throw new UnsupportedOperationException("Can't send server-sided packets from the client");
        }

        try
        {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);

            outputStream.writeObject(new SealedObject(packet, c));
            LogUtil.info("Sent packet to server: %s", packet);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Handle an incoming packet.
     *
     * @param packet The packet
     */
    private void handlePacket(Packet packet)
    {
        PacketHandlerManager.getInstance().handle(packet);
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public boolean isConnected()
    {
        return socket != null;
    }
}
