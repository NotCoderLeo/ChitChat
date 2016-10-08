package me.coderleo.chitchat.server.packethandlers;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.server.User;

abstract class PacketHandler<T extends Packet>
{
    private final Class<T> packetClass;

    PacketHandler(Class<T> packetClass)
    {
        this.packetClass = packetClass;
    }

    Class<? extends Packet> getPacketClass()
    {
        return packetClass;
    }

    public abstract void handle(T packet, User user);

    /**
     * Send a packet to the target user.
     * Just a helper method.
     *
     * @param packet
     * @param target
     */
    public void send(Packet packet, User target)
    {
        target.send(packet);
    }
}
