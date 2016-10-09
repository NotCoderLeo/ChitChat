package me.coderleo.chitchat.common.packets.universal;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractConversation;

public class PacketConversationAddUser extends Packet
{
    private static final long serialVersionUID = 4270772243018965156L;

    private final String conversation;
    private final String newUser;

    public PacketConversationAddUser(AbstractConversation conversation, String newUser)
    {
        this(conversation.getName(), newUser);
    }

    public PacketConversationAddUser(String conversation, String newUser)
    {
        this.conversation = conversation;
        this.newUser = newUser;
    }

    public String getConversation()
    {
        return conversation;
    }

    public String getNewUser()
    {
        return newUser;
    }
}
