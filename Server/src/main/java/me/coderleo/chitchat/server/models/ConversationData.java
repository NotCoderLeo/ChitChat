package me.coderleo.chitchat.server.models;

import java.io.Serializable;

public class ConversationData implements Serializable
{
    private static final long serialVersionUID = -8228690534652292411L;

    private final String name;
    private final int id;
    private final String[] users;

    public ConversationData(String name, String[] users, int id)
    {
        this.name = name;
        this.users = users;
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

    public String[] getUsers()
    {
        return users;
    }
}
