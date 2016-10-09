package me.coderleo.chitchat.client.packethandlers;

import me.coderleo.chitchat.client.User;
import me.coderleo.chitchat.client.managers.ConversationManager;
import me.coderleo.chitchat.common.packets.universal.PacketUserJoin;
import me.coderleo.chitchat.common.packets.universal.PacketUserLeave;

public class PacketUserLeaveHandler extends PacketHandler<PacketUserLeave>
{
    PacketUserLeaveHandler()
    {
        super(PacketUserLeave.class);
    }

    @Override
    public void handle(PacketUserLeave packet)
    {
        ConversationManager.getInstance().removeUser(ConversationManager.getInstance().getUser(packet.getUser()));
    }
}