package me.coderleo.chitchat.server;

import java.util.HashMap;
import java.util.Map;

public class Heartbeats
{
    public static Map<User, Long> heartbeatMap = new HashMap<>();

    public static boolean hasHeartbeat(User user)
    {
        return heartbeatMap.containsKey(user);
    }

    public static Long getLastHeartbeat(User user)
    {
        return heartbeatMap.get(user);
    }
}
