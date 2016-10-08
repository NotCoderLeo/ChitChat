package me.coderleo.chitchat.common.packets.client.user;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractUser;

public class PacketUserStatusChange extends Packet
{
    private static final long serialVersionUID = 5893694317968737139L;

    private AbstractUser user;
    private AbstractUser.UserStatus newStatus;

    public PacketUserStatusChange(AbstractUser user, AbstractUser.UserStatus newStatus)
    {
        this.user = user;
        this.newStatus = newStatus;
    }

    public AbstractUser getUser()
    {
        return user;
    }

    public AbstractUser.UserStatus getNewStatus()
    {
        return newStatus;
    }
}
