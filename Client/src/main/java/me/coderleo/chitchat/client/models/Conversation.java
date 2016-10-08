package me.coderleo.chitchat.client.models;

import me.coderleo.chitchat.client.User;
import me.coderleo.chitchat.client.gui.ChatPanel;
import me.coderleo.chitchat.common.models.AbstractConversation;
import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.models.ConversationData;
import me.coderleo.chitchat.common.models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Conversation extends AbstractConversation
{
    private final String name;
    private final ArrayList<AbstractUser> users;
    private final ArrayList<Message> messages;

    private final transient ChatPanel chatPanel;

    public Conversation(ConversationData data)
    {
        this(data.getName(), data.getMembers());
    }

    public Conversation(String name, AbstractUser... users)
    {
        this.name = name;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();

        this.chatPanel = new ChatPanel(this);

        for (AbstractUser user : users)
        {
            addUser(user);
        }
    }

    public String getName()
    {
        return name;
    }

    public AbstractUser[] getUsers()
    {
        return users.toArray(new AbstractUser[users.size()]);
    }

    public void addUser(AbstractUser user)
    {
        users.add(user);
//        chatPanel.update();
    }

    public void removeUser(AbstractUser user)
    {
        users.remove(user);
//        chatPanel.update();
    }

    public boolean hasUser(AbstractUser user)
    {
        return users.contains(user);
    }


    public void addMessage(User sender, Conversation chat, String msg, Calendar when)
    {
        Message message = new Message(sender, chat, msg, when);
        messages.add(message);
//        chatPanel.messageReceived(message);
//
//        if (!Main.getInstance().isVisible())
//        {
//            Notification.showNotification(message);
//        }
    }

    public ChatPanel getChatPanel()
    {
        return chatPanel;
    }

    @Override
    public String toString()
    {
        return "Chat name=" + name + " users=" + Arrays.toString(users.toArray());
    }
}
