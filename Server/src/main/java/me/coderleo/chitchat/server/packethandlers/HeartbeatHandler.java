package me.coderleo.chitchat.server.packethandlers;

import me.coderleo.chitchat.common.packets.client.handshake.PacketCSHeartbeat;
import me.coderleo.chitchat.server.Heartbeats;
import me.coderleo.chitchat.server.User;

public class HeartbeatHandler extends PacketHandler<PacketCSHeartbeat>
{
    public HeartbeatHandler()
    {
        super(PacketCSHeartbeat.class);
    }

    @Override
    public void handle(PacketCSHeartbeat packet, User user)
    {
        Heartbeats.heartbeatMap.put(user, System.currentTimeMillis());
    }
}
