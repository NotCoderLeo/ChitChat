package me.coderleo.chitchat.common.packets.universal;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractConversation;
import me.coderleo.chitchat.common.models.AbstractUser;

import java.util.Calendar;

public class PacketMessage extends Packet
{
    private static final long serialVersionUID = -4098200773356894785L;

    public PacketMessage(AbstractConversation conversation, AbstractUser sender, String msg)
    {
        this(conversation.getName(), sender.getUsername(), msg, conversation.getId(), false);
    }

    public PacketMessage(AbstractConversation conversation, AbstractUser sender, String msg, boolean isSystem)
    {
        this(conversation.getName(), isSystem ? null : sender.getUsername(), msg, conversation.getId(), isSystem);
    }

    private final String conversation;
    private final int id;
    private final String sender;
    private final String msg;
    private final Calendar when;
    private final boolean isSystem;

    public PacketMessage(String conversation, String sender, String msg, int id, boolean isSystem)
    {
        this.conversation = conversation;
        this.sender = sender;
        this.msg = msg.replaceAll(" ", "%20");
        this.when = Calendar.getInstance();
        this.id = id;
        this.isSystem = isSystem;
    }

    public boolean isSystem()
    {
        return isSystem;
    }

    public String getConversation()
    {
        return conversation;
    }

    public int getId()
    {
        return id;
    }

    public String getSender()
    {
        return sender;
    }

    public String getMessage()
    {
        return msg;
    }

    public Calendar getWhen()
    {
        return when;
    }
}
