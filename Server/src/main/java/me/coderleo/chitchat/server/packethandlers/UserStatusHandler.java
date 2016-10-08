package me.coderleo.chitchat.server.packethandlers;

import me.coderleo.chitchat.common.packets.client.user.PacketUserStatusChange;
import me.coderleo.chitchat.common.packets.server.handshake.PacketSCHi;
import me.coderleo.chitchat.common.tempdata.StatusCache;
import me.coderleo.chitchat.common.util.LogUtil;
import me.coderleo.chitchat.server.User;

class UserStatusHandler extends PacketHandler<PacketUserStatusChange>
{
    UserStatusHandler()
    {
        super(PacketUserStatusChange.class);
    }

    @Override
    public void handle(PacketUserStatusChange packet, User user)
    {
        LogUtil.info("received user status change");

        user.setStatus(packet.getNewStatus());
        user.send(new PacketSCHi());
    }
}
