package me.coderleo.chitchat.server.web;

public class Stats
{
    public int totalUsers;
    public int onlineUsers;
    public int totalConversations;

    public Stats(int totalUsers, int onlineUsers, int totalConversations)
    {
        this.totalUsers = totalUsers;
        this.onlineUsers = onlineUsers;
        this.totalConversations = totalConversations;
    }
}
