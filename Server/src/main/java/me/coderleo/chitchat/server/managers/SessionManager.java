package me.coderleo.chitchat.server.managers;

import me.coderleo.chitchat.server.User;

import java.util.HashMap;
import java.util.Map;

public class SessionManager
{
    private static SessionManager ourInstance = new SessionManager();

    public static SessionManager getInstance()
    {
        return ourInstance;
    }

    private final Map<String, User> sessionMap;

    private SessionManager()
    {
        sessionMap = new HashMap<>();
    }

    public Map<String, User> getSessionMap()
    {
        return sessionMap;
    }
}
