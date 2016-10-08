package me.coderleo.chitchat.common.api;

import me.coderleo.chitchat.common.models.AbstractUser.UserStatus;

/**
 * Represents a user.
 */
public interface IUser
{
    String getUsername();

    void setUsername(String username);

    int getUserId();

    void setUserId(int userId);

    String getDisplayName();

    void setDisplayName(String displayName);

    UserStatus getStatus();

    void setStatus(UserStatus status);

    boolean isVerified();

    void setVerified(boolean verified);
}
