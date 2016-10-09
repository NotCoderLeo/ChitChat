package me.coderleo.chitchat.server.packethandlers;

import me.coderleo.chitchat.common.packets.universal.PacketConversationAddUser;
import me.coderleo.chitchat.common.packets.universal.PacketConversationCreate;
import me.coderleo.chitchat.server.User;
import me.coderleo.chitchat.server.managers.ConversationManager;
import me.coderleo.chitchat.server.models.Conversation;

public class PacketConversationAddUserHandler extends PacketHandler<PacketConversationAddUser>
{
    public PacketConversationAddUserHandler()
    {
        super(PacketConversationAddUser.class);
    }

    @Override
    public void handle(PacketConversationAddUser packet, User user)
    {
        Conversation conversation = ConversationManager.getInstance().getConversation(packet.getConversation());
//        chat.addUser(packet.getNewUser());

        if (ConversationManager.getInstance().getUser(packet.getNewUser()) != null)
        {
            ConversationManager.getInstance().getUser(packet.getNewUser()).send(new PacketConversationCreate(conversation.toAbstract()));
        }

        for (String u : conversation.getMembers())
        {
            if (ConversationManager.getInstance().getUser(u) != null)
            {
                ConversationManager.getInstance().getUser(u).send(packet);
            }
        }
    }
}
