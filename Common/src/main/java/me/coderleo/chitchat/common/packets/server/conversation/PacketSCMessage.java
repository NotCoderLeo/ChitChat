package me.coderleo.chitchat.common.packets.server.conversation;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractConversation;
import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.models.Message;
import me.coderleo.chitchat.common.packets.annotations.ServerSided;

@ServerSided
public class PacketSCMessage extends Packet
{
    private Message message;
    private AbstractUser sender;
    private AbstractConversation conversation;

    public PacketSCMessage(Message message, AbstractUser sender, AbstractConversation conversation)
    {
        this.message = message;
        this.sender = sender;
        this.conversation = conversation;
    }

    public AbstractConversation getConversation()
    {
        return conversation;
    }

    public Message getMessage()
    {
        return message;
    }

    public AbstractUser getSender()
    {
        return sender;
    }

    public void setSender(AbstractUser sender)
    {
        this.sender = sender;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }
}
