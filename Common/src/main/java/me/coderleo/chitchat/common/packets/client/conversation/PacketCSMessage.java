package me.coderleo.chitchat.common.packets.client.conversation;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractConversation;
import me.coderleo.chitchat.common.packets.annotations.ClientSided;

@ClientSided
public class PacketCSMessage extends Packet
{
    private final String message;
    private final AbstractConversation conversation;

    public PacketCSMessage(String message, AbstractConversation conversation)
    {
        this.message = message;
        this.conversation = conversation;
    }

    public String getMessage()
    {
        return message;
    }

    public AbstractConversation getConversation()
    {
        return conversation;
    }
}
