package me.coderleo.chitchat.server.packethandlers;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.util.ConcurrentUtil;
import me.coderleo.chitchat.server.User;

import java.util.ArrayList;

public class PacketHandlerManager
{
    private static PacketHandlerManager ourInstance = new PacketHandlerManager();

    public static PacketHandlerManager getInstance()
    {
        return ourInstance;
    }

    private final ArrayList<PacketHandler<?>> handlers = new ArrayList<>();

    private PacketHandlerManager()
    {
        handlers.add(new RegisterRequestHandler());
        handlers.add(new LoginRequestHandler());
        handlers.add(new LogoutHandler());

        handlers.add(new UserStatusHandler());
        handlers.add(new HeartbeatHandler());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends Packet> void handle(T packet, User user)
    {
        ConcurrentUtil.pool.submit(() ->
        {
            for (PacketHandler handler : handlers)
            {
                if (handler.getPacketClass().equals(packet.getClass()))
                {
                    handler.handle(packet, user);
                }
            }
        });
    }
}
