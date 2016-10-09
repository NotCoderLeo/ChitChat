package me.coderleo.chitchat.server.models;

import me.coderleo.chitchat.common.models.AbstractUser;

import java.io.Serializable;

public class ConversationData_old implements Serializable
{
    private static final long serialVersionUID = -8228690534652292411L;

    private final String name;
    private final AbstractUser[] members;
    private final int id;

    public ConversationData_old(String name, AbstractUser[] members, int id)
    {
        this.name = name;
        this.members = members;
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

    public AbstractUser[] getMembers()
    {
        return members;
    }
}
