package me.coderleo.chitchat.client.packethandlers;

import me.coderleo.chitchat.client.User;
import me.coderleo.chitchat.client.managers.ConversationManager;
import me.coderleo.chitchat.common.packets.universal.PacketUserJoin;

public class PacketUserJoinHandler extends PacketHandler<PacketUserJoin>
{
    PacketUserJoinHandler()
    {
        super(PacketUserJoin.class);
    }

    @Override
    public void handle(PacketUserJoin packet)
    {
        ConversationManager.getInstance().addUser(new User(packet.getUser().split(";")));
    }
}