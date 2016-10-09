package me.coderleo.chitchat.server.models;

import me.coderleo.chitchat.common.models.AbstractConversation;
import me.coderleo.chitchat.common.packets.universal.PacketMessage;
import me.coderleo.chitchat.server.data.SqlDataManager;
import me.coderleo.chitchat.server.managers.ConversationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Conversation
{
    private final String name;
    private final int id;
    private final ArrayList<String> members;
    private final ArrayList<Message> messages;

    public Conversation(ConversationData data)
    {
        this(data.getName(), data.getId(), data.getUsers());
    }

    public Conversation(String name, int id, String... members)
    {
        this.name = name;
        this.id = id;
        this.members = new ArrayList<>(Arrays.asList(members));
        this.messages = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<String> getMembers()
    {
        return members;
    }

    public void sendSystemMessage(String message)
    {
        getMembers().stream().filter(u -> ConversationManager.getInstance().getUser(u) != null)
                .map(ConversationManager.getInstance()::getUser)
                .forEach(u -> u.send(new PacketMessage(toAbstract(), null, message, true)));
    }


    public void addUser(String user)
    {
        members.add(user);
        SqlDataManager.getInstance().saveConversation(this);
    }

    public void removeUser(String user)
    {
        members.remove(user);
        SqlDataManager.getInstance().saveConversation(this);
    }

    public boolean hasUser(String user)
    {
        return members.contains(user);
    }

    public Message[] getMessages()
    {
        return messages.toArray(new Message[messages.size()]);
    }

    public int getId()
    {
        return id;
    }

    public ConversationData toData()
    {
        return new ConversationData(
                getName(), getMembers().toArray(new String[getMembers().size()]), getId());
    }

    public me.coderleo.chitchat.common.models.ConversationData toLegacyData()
    {
        return new me.coderleo.chitchat.common.models.ConversationData(
                getName(), getId(), getMembers().toArray(new String[getMembers().size()]));
    }

    public void addMessage(String sender, String chat, String msg, Calendar when, boolean isSystem)
    {
        Message message = new Message(sender, chat, msg, when);

        messages.add(message);
    }

    public AbstractConversation toAbstract()
    {
        AbstractConversation abstractConversation = new AbstractConversation();
        abstractConversation.setId(getId());
        abstractConversation.setName(getName());
        abstractConversation.setMembers(getMembers());
        abstractConversation.setMessages(legacyMessages());
        abstractConversation.setCreated(null);

        return abstractConversation;
    }

    private List<me.coderleo.chitchat.common.models.Message> legacyMessages()
    {
        return Arrays.stream(getMessages())
                .map(m -> new me.coderleo.chitchat.common.models.Message(m.getSender().getUsername(), m.getMessage(), m.getWhen()))
                .collect(Collectors.toList());
    }
}
