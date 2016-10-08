package me.coderleo.chitchat.server.packethandlers;

import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.models.Message;
import me.coderleo.chitchat.common.packets.client.conversation.PacketCSMessage;
import me.coderleo.chitchat.common.packets.server.conversation.PacketSCMessage;
import me.coderleo.chitchat.common.util.LogUtil;
import me.coderleo.chitchat.server.User;
import me.coderleo.chitchat.server.managers.ConversationManager;
import me.coderleo.chitchat.server.models.Conversation;

import java.util.Arrays;
import java.util.Calendar;

public class MessageHandler extends PacketHandler<PacketCSMessage>
{
    public MessageHandler()
    {
        super(PacketCSMessage.class);
    }

    @Override
    public void handle(PacketCSMessage packet, User user)
    {
        if (ConversationManager.getInstance().hasConversation(packet.getConversation()))
        {
            Conversation conversation = ConversationManager.getInstance().getConversation(packet.getConversation().getName());
            Message message = conversation.addMessage(user, packet.getConversation(), packet.getMessage(), Calendar.getInstance());

            Arrays.stream(conversation.getUsers())
                    .filter(u -> ConversationManager.getInstance().getUser(u.getUsername()) != null)
                    .forEach(u -> ConversationManager.getInstance().getUser(u.getUsername()).send(
                            new PacketSCMessage(message,
                                    new AbstractUser(user.getUsername(), user.getDisplayName(), user.getUserId(), false),
                                    packet.getConversation())
                    ));
        } else
        {
            LogUtil.warn("!!! User sent invalid message packet !!!");
        }
    }
}
