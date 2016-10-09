package me.coderleo.chitchat.server.packethandlers;

import me.coderleo.chitchat.common.packets.universal.PacketMessage;
import me.coderleo.chitchat.server.User;
import me.coderleo.chitchat.server.managers.ConversationManager;
import me.coderleo.chitchat.server.models.Conversation;

public class MessageHandler extends PacketHandler<PacketMessage>
{
    public MessageHandler()
    {
        super(PacketMessage.class);
    }

    @Override
    public void handle(PacketMessage packet, User user)
    {
        Conversation conversation = ConversationManager.getInstance().getConversationById(packet.getId());

        conversation.getMembers().stream().filter(u -> ConversationManager.getInstance().getUser(u) != null)
                .map(ConversationManager.getInstance()::getUser)
                .forEach(u -> u.send(packet));
    }
}
