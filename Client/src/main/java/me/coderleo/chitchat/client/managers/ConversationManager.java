package me.coderleo.chitchat.client.managers;

import me.coderleo.chitchat.client.User;
import me.coderleo.chitchat.client.models.Conversation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConversationManager
{
    private ConversationManager()
    {
    }

    private static ConversationManager ourInstance = new ConversationManager();

    public static ConversationManager getInstance()
    {
        return ourInstance;
    }

    private final ArrayList<Conversation> conversations = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();
    private User localUser;

    /**
     * Inform the manager of a new conversation.
     *
     * @param conversation The conversation
     */
    public void addConversation(Conversation conversation)
    {
        conversations.add(conversation);
    }

    /**
     * Remove a conversation by name.
     *
     * @param name The name of the conversation
     */
    public void removeConversation(String name)
    {

    }

    /**
     * Remove a conversation.
     *
     * @param conversation The conversation
     * @return The conversation that was removed
     */
    Conversation removeConversation(Conversation conversation)
    {
        conversations.remove(conversation);

        return conversation;
    }

    /**
     * Find a conversation by name.
     *
     * @param name The name of the conversation
     * @return The conversation
     */
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

    public User getLocalUser()
    {
        return localUser;
    }

    public void setLocalUser(User localUser)
    {
        this.localUser = localUser;
    }

    public User getUser(String name)
    {
        if (localUser != null && localUser.getUsername().equalsIgnoreCase(name))
            return localUser;

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
        List<User> temp = new ArrayList<>();

        temp.addAll(users);
        temp.add(localUser);

        return temp;
    }

    public void clear()
    {
        conversations.clear();
        users.clear();
        localUser = null;
    }
}
