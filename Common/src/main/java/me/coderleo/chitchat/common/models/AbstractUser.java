package me.coderleo.chitchat.common.models;

import me.coderleo.chitchat.common.api.IUser;
import me.coderleo.chitchat.common.tempdata.StatusCache;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class AbstractUser implements IUser
{
    public enum UserStatus
    {
        ONLINE, AWAY, DO_NOT_DISTURB, INVISIBLE, OFFLINE
    }

    private int userId;
    private boolean verified = false;
    private String username, displayName;
    private UserStatus status;

    protected AbstractUser()
    {
        this("", "", -1, false);
        this.setStatus(UserStatus.OFFLINE);
    }

    public AbstractUser(String name, String displayName, int userId, boolean verified)
    {
        this.username = name;
        this.displayName = displayName;
        this.userId = userId;
        this.verified = verified;
    }

    public String getUsername()
    {
        return username;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public UserStatus getStatus()
    {
        return status;
    }

    public void setStatus(UserStatus status)
    {
        this.status = status;

        StatusCache.getInstance().setForUser(this, status);
    }

    public String getEncoded()
    {
        return StringUtils.join(Arrays.asList(getUsername(), getDisplayName(), getUserId()), ";");
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public boolean isVerified()
    {
        return verified;
    }

    public void setVerified(boolean verified)
    {
        this.verified = verified;
    }
}
