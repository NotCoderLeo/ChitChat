package me.coderleo.chitchat.client.packethandlers;

import me.coderleo.chitchat.client.User;
import me.coderleo.chitchat.client.managers.ConversationManager;
import me.coderleo.chitchat.common.packets.server.PacketUserList;

public class PacketUserListHandler extends PacketHandler<PacketUserList>
{
    PacketUserListHandler()
    {
        super(PacketUserList.class);
    }

    @Override
    public void handle(PacketUserList packet)
    {
        for (String user : packet.getUsers())
        {
            ConversationManager.getInstance().addUser(new User(user.split(";")));
        }
    }
}