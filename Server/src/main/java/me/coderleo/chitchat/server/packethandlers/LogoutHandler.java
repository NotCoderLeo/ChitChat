package me.coderleo.chitchat.server.packethandlers;

import me.coderleo.chitchat.common.packets.client.user.PacketCSLogout;
import me.coderleo.chitchat.common.packets.server.user.PacketSCLogout;
import me.coderleo.chitchat.common.util.LogUtil;
import me.coderleo.chitchat.server.User;

public class LogoutHandler extends PacketHandler<PacketCSLogout>
{
    public LogoutHandler()
    {
        super(PacketCSLogout.class);
    }

    @Override
    public void handle(PacketCSLogout packet, User user)
    {
        LogUtil.info("Logging user out.");

        user.send(new PacketSCLogout());
    }
}
