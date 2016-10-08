package me.coderleo.chitchat.common.tempdata;

import me.coderleo.chitchat.common.models.AbstractUser;

import java.util.HashMap;
import java.util.Map;

public class StatusCache
{
    private static StatusCache ourInstance = new StatusCache();

    public static StatusCache getInstance()
    {
        return ourInstance;
    }

    private Map<AbstractUser, AbstractUser.UserStatus> statusMap = new HashMap<>();

    private StatusCache()
    {
    }

    public void setForUser(AbstractUser user, AbstractUser.UserStatus status)
    {
        statusMap.put(user, status);
    }

    public AbstractUser.UserStatus getForUser(AbstractUser user)
    {
        return statusMap.getOrDefault(user, AbstractUser.UserStatus.OFFLINE);
    }
}
