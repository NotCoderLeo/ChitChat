package me.coderleo.chitchat.client;

import me.coderleo.chitchat.common.models.AbstractUser;

public class User extends AbstractUser
{
    /**
     * Construct a user.
     *
     * @param information
     */
    public User(String[] information)
    {
        super(information[0], information[1], information.length > 2 ? Integer.parseInt(information[2]) : -1, false);
    }
}
