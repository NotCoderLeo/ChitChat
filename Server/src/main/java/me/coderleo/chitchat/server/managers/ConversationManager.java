package me.coderleo.chitchat.server.managers;

import me.coderleo.chitchat.common.models.AbstractConversation;
import me.coderleo.chitchat.common.models.ConversationData;
import me.coderleo.chitchat.server.User;
import me.coderleo.chitchat.server.models.Conversation;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConversationManager
{
    private ConversationManager()
    {
    }

    private static final ConversationManager ourInstance = new ConversationManager();

    public static ConversationManager getInstance()
    {
        return ourInstance;
    }

    private final ArrayList<Conversation> conversations = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();

    public Conversation addConversation(Conversation conversation)
    {
        conversations.add(conversation);

        return conversation;
    }

    public Conversation removeChat(String name)
    {
        return removeConversation(getConversation(name));
    }

    private Conversation removeConversation(Conversation conversation)
    {
        conversations.remove(conversation);

        return conversation;
    }

    public Conversation getConversation(String name)
    {
        return conversations.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a user's conversations.
     *
     * @param user the user
     * @return the user's conversations
     */
    public List<Conversation> getConversations(User user)
    {
        return conversations.stream()
                .filter(c -> c.hasUser(user))
                .collect(Collectors.toList());
    }

    /**
     * Get a user's conversations.
     *
     * @param user the user
     * @return the user's conversations
     */
    public List<ConversationData> getConversationData(User user)
    {
        return conversations.stream()
                .filter(c -> c.hasUser(user))
                .map(Conversation::toData)
                .collect(Collectors.toList());
    }

    public boolean hasConversation(AbstractConversation conversation)
    {
        return conversations.stream()
                .map(c -> new ImmutablePair<>(c.getName(), c.getId()))
                .anyMatch(p ->
                        p.getKey().equalsIgnoreCase(conversation.getName())
                                && p.getValue() == conversation.getId());
    }

    /**
     * Register a new user.
     *
     * @param user the user
     */
    public void addUser(User user)
    {
        users.add(user);
    }

    /**
     * Remove a registered user.
     *
     * @param user the user
     */
    public void removeUser(User user)
    {
        users.remove(user);
    }

    /**
     * Get a user by name
     *
     * @param name
     * @return
     */
    public User getUser(String name)
    {
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<User> getUsers(String... names)
    {
        return Arrays.stream(names)
                .map(this::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getAllUsers()
    {
        return new ArrayList<>(users);
    }
}
