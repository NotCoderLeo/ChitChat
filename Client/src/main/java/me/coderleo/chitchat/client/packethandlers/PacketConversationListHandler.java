package me.coderleo.chitchat.client.packethandlers;

import me.coderleo.chitchat.client.managers.ConversationManager;
import me.coderleo.chitchat.client.models.Conversation;
import me.coderleo.chitchat.common.models.ConversationData;
import me.coderleo.chitchat.common.packets.server.PacketConversationList;
import me.coderleo.chitchat.common.util.LogUtil;

public class PacketConversationListHandler extends PacketHandler<PacketConversationList>
{
    public PacketConversationListHandler()
    {
        super(PacketConversationList.class);
    }

    @Override
    public void handle(PacketConversationList packet)
    {
        ConversationManager.getInstance().getConversations().clear();

        for (ConversationData conversation : packet.getConversations())
        {
            ConversationManager.getInstance().addConversation(new Conversation(conversation));
        }
    }
}