package me.coderleo.chitchat.client.packethandlers;

import me.coderleo.chitchat.common.api.Packet;

public abstract class PacketHandler<T extends Packet>
{
    private final Class<T> packetClass;

    public PacketHandler(Class<T> packetClass)
    {
        this.packetClass = packetClass;
    }

    Class<? extends Packet> getPacketClass()
    {
        return packetClass;
    }

    public abstract void handle(T packet);
}