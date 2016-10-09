package me.coderleo.chitchat.client.models;

import me.coderleo.chitchat.client.gui.ChatPanel;
import me.coderleo.chitchat.client.managers.ConversationManager;
import me.coderleo.chitchat.common.models.AbstractConversation;
import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.models.ConversationData;
import me.coderleo.chitchat.common.models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Conversation extends AbstractConversation
{
    private final String name;
    private final ArrayList<AbstractUser> users;
    private final ArrayList<Message> messages;
    private final int id;

    private final transient ChatPanel chatPanel;

    public Conversation(ConversationData data)
    {
        this(data.getName(), data.getId(),
                data.getUsers(data, ConversationManager.getInstance()::getUser).toArray(
                        new AbstractUser[data.getUsers(data, ConversationManager.getInstance()::getUser).size()]
                ));
    }

    public Conversation(String name, int id, AbstractUser... users)
    {
        this.id = id;
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

    public void addMessage(String sender, Conversation conversation, String msg, Calendar when, boolean isSystem)
    {
        Message message = new Message(sender, msg, when);
        messages.add(message);
        chatPanel.messageReceived(message, isSystem);
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
    public int getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return "Conversation{name=" + name + ",users=" + Arrays.toString(users.toArray()) + "}";
    }
}
