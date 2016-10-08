package me.coderleo.chitchat.server.models;

import me.coderleo.chitchat.common.models.AbstractConversation;
import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Conversation
{
    private final int id;
    private final String name;
    private final ArrayList<AbstractUser> users;
    private final ArrayList<Message> messages;

    public Conversation(ConversationData conversationData)
    {
        this(conversationData.getName(), conversationData.getId(), conversationData.getMembers());
    }

    public Conversation(String name, int id, AbstractUser... users)
    {
        this.name = name;
        this.users = new ArrayList<>(Arrays.asList(users));
        this.messages = new ArrayList<>();
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public AbstractUser[] getUsers()
    {
        return users.toArray(new AbstractUser[users.size()]);
    }

    public String getUsersWithCommas()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (AbstractUser user : users)
        {
            stringBuilder.append(user.getUsername()).append(",");
        }

        return stringBuilder.toString();
    }

    public void addUser(AbstractUser user)
    {
        users.add(user);
//        MySQL.getInstance().saveChat(this);
    }

    public void removeUser(AbstractUser user)
    {
        users.remove(user);
//        MySQL.getInstance().saveChat(this);
    }

    public boolean hasUser(AbstractUser user)
    {
        return users.contains(user);
    }

    public me.coderleo.chitchat.common.models.ConversationData toData()
    {
        return new me.coderleo.chitchat.common.models.ConversationData(
                getName(), getUsers()
        );
    }

    public Message[] getMessages()
    {
        return messages.toArray(new Message[messages.size()]);
    }

    public Message addMessage(AbstractUser sender, AbstractConversation conversation, String msg, Calendar when)
    {
        Message message = new Message(sender, conversation, msg, when);

        messages.add(message);

        return message;
    }
}
