package me.coderleo.chitchat.client.models;

import me.coderleo.chitchat.client.User;
import me.coderleo.chitchat.client.managers.ConversationManager;

import java.util.Calendar;

public class Message
{
    private final User sender;
    private final Conversation conversation;
    private final String message;
    private final Calendar when;

    public Message(String sender, String conversation, String message, Calendar when)
    {
        this(ConversationManager.getInstance().getUser(sender),
                ConversationManager.getInstance().getConversation(conversation), message, when);
    }

    private Message(User sender, Conversation conversation, String message, Calendar when)
    {
        this.sender = sender;
        this.conversation = conversation;
        this.message = message;
        this.when = when;
    }

    public User getSender()
    {
        return sender;
    }

    public Conversation getConversation()
    {
        return conversation;
    }

    public String getMessage()
    {
        return message;
    }

    public Calendar getWhen()
    {
        return when;
    }
}
