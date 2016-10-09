package me.coderleo.chitchat.common.packets.universal;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractConversation;

public class PacketConversationRemoveUser extends Packet
{
    private static final long serialVersionUID = -9170778343018997460L;

    private final String conversation;
    private final String oldUser;

    public PacketConversationRemoveUser(AbstractConversation conversation, String oldUser)
    {
        this(conversation.getName(), oldUser);
    }

    public PacketConversationRemoveUser(String conversation, String oldUser)
    {
        this.conversation = conversation;
        this.oldUser = oldUser;
    }

    public String getConversation()
    {
        return conversation;
    }

    public String getOldUser()
    {
        return oldUser;
    }
}
