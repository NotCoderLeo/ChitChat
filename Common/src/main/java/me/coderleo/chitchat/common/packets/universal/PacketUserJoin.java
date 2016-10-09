package me.coderleo.chitchat.common.packets.universal;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractUser;

public class PacketUserJoin extends Packet
{
    private static final long serialVersionUID = 3852290317461257132L;

    private String user;

    public PacketUserJoin(AbstractUser user)
    {
        this.user = user.getEncoded();
    }

    public String getUser()
    {
        return user;
    }
}
