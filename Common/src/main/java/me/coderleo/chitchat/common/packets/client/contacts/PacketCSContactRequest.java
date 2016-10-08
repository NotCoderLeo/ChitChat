package me.coderleo.chitchat.common.packets.client.contacts;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.packets.annotations.ClientSided;

@ClientSided
public class PacketCSContactRequest extends Packet
{
    private final AbstractUser from;
    private final AbstractUser target;

    public PacketCSContactRequest(AbstractUser from, AbstractUser target)
    {
        this.from = from;
        this.target = target;
    }

    public AbstractUser getFrom()
    {
        return from;
    }

    public AbstractUser getTarget()
    {
        return target;
    }
}
