package me.coderleo.chitchat.common.packets.universal;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractUser;

public class PacketUserLeave extends Packet
{
    private static final long serialVersionUID = 7711637711615685676L;

    private String user;

    public PacketUserLeave(AbstractUser user)
    {
        this.user = user.getUsername();
    }

    public String getUser()
    {
        return user;
    }
}
