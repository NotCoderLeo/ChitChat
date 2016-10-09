package me.coderleo.chitchat.common.packets.server;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.ConversationData;

import java.util.List;

public class PacketConversationList extends Packet
{
    private static final long serialVersionUID = 6368232371915047155L;

    private final List<ConversationData> conversations;

    public PacketConversationList(List<ConversationData> conversations)
    {
        this.conversations = conversations;
    }

    public List<ConversationData> getConversations()
    {
        return conversations;
    }

    //    private ConversationData[] conversations;
//
//    public void setConversations(ConversationData[] conversations)
//    {
//        this.conversations = conversations;
//    }
//
//    public ConversationData[] getConversations()
//    {
//        return conversations;
//    }
}
