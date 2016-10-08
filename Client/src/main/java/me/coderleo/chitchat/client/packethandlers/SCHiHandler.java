package me.coderleo.chitchat.client.packethandlers;

import me.coderleo.chitchat.common.packets.server.handshake.PacketSCHi;
import me.coderleo.chitchat.common.util.LogUtil;

class SCHiHandler extends PacketHandler<PacketSCHi>
{
    SCHiHandler()
    {
        super(PacketSCHi.class);
    }

    @Override
    public void handle(PacketSCHi packet)
    {
        LogUtil.info("S->C Hi received!");
    }
}
