package me.coderleo.chitchat.client.packethandlers;

import me.coderleo.chitchat.client.Main;
import me.coderleo.chitchat.client.ServerConnector;
import me.coderleo.chitchat.common.packets.server.user.PacketSCLogout;
import me.coderleo.chitchat.common.util.LogUtil;

public class LogoutHandler extends PacketHandler<PacketSCLogout>
{
    public LogoutHandler()
    {
        super(PacketSCLogout.class);
    }

    @Override
    public void handle(PacketSCLogout packet)
    {
        LogUtil.info("Server->Client logout received");

        ServerConnector.getInstance().disconnect();
        Main.getInstance().showLoginPanel();
    }
}
