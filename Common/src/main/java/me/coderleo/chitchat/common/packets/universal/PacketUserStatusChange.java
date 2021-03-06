package me.coderleo.chitchat.common.packets.universal;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractUser;

public class PacketUserStatusChange extends Packet
{
    private static final long serialVersionUID = 5893694317968737139L;

    private final String user;
    private final String newStatus;

    public PacketUserStatusChange(AbstractUser user, AbstractUser.UserStatus newStatus)
    {
        this.user = user.getUsername();
        this.newStatus = newStatus.name();
    }

    public String getUser()
    {
        return user;
    }

    public String getNewStatus()
    {
        return newStatus;
    }
}