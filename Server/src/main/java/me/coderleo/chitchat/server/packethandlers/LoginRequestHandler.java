package me.coderleo.chitchat.server.packethandlers;

import me.coderleo.chitchat.common.packets.client.auth.PacketLoginRequest;
import me.coderleo.chitchat.common.packets.server.auth.PacketLoginResponse;
import me.coderleo.chitchat.common.util.LogUtil;
import me.coderleo.chitchat.server.User;
import me.coderleo.chitchat.server.data.SqlDataManager;
import me.coderleo.chitchat.server.managers.ConversationManager;

class LoginRequestHandler extends PacketHandler<PacketLoginRequest>
{
    LoginRequestHandler()
    {
        super(PacketLoginRequest.class);
    }

    @Override
    public void handle(PacketLoginRequest packet, User user)
    {
        LogUtil.info("Received a login request.");

        SqlDataManager dm = SqlDataManager.getInstance();

        if (!dm.validateLogin(packet.getUser(), packet.getPassword()))
        {
            user.send(new PacketLoginResponse(null, PacketLoginResponse.LoginResponse.FAILURE));
            LogUtil.warn("Denied login request.");
        } else
        {
            user.setUsername(packet.getUser());
            user.setDisplayName(packet.getUser() + " - DISPLAY");
            user.send(new PacketLoginResponse(user.getEncoded(), PacketLoginResponse.LoginResponse.SUCCESS));
            ConversationManager.getInstance().addUser(user);
            LogUtil.success("Accepted login request.");
        }
    }
}
