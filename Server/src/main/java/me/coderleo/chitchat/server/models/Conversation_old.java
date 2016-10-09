package me.coderleo.chitchat.server.models;

import me.coderleo.chitchat.common.models.AbstractConversation;
import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Conversation_old extends AbstractConversation
{
    private final int id;
    private final String name;
    private final ArrayList<AbstractUser> users;
    private final ArrayList<Message> messages;

    public Conversation_old(ConversationData_old conversationData)
    {
        this(conversationData.getName(), conversationData.getId(), conversationData.getMembers());
    }

    public Conversation_old(String name, int id, AbstractUser... users)
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
    }

    public void removeUser(AbstractUser user)
    {
        users.remove(user);
    }

    public boolean hasUser(AbstractUser user)
    {
        return users.contains(user);
    }

    public me.coderleo.chitchat.common.models.ConversationData toData()
    {
        return new me.coderleo.chitchat.common.models.ConversationData(
                getName(), getId(), new String[0]
        );
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public AbstractConversation toAbstract()
    {
        AbstractConversation abstractConversation = new AbstractConversation();
        abstractConversation.setId(getId());
        abstractConversation.setName(getName());
        abstractConversation.setMembers(getMembers());
        abstractConversation.setMessages(getMessages());
        abstractConversation.setCreated(getCreated());

        return abstractConversation;
    }

    public void addMessage(String sender, String msg, Calendar when)
    {
        Message message = new Message(sender, msg, when);

        messages.add(message);
    }
}
