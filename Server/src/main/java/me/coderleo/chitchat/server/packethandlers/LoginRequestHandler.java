package me.coderleo.chitchat.server.packethandlers;

import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.packets.client.auth.PacketLoginRequest;
import me.coderleo.chitchat.common.packets.server.auth.PacketLoginResponse;
import me.coderleo.chitchat.common.packets.universal.PacketUserStatusChange;
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
        SqlDataManager dm = SqlDataManager.getInstance();

        if (ConversationManager.getInstance().getUser(packet.getUser()) != null)
        {
            user.send(new PacketLoginResponse(null, PacketLoginResponse.LoginResponse.FAILURE, "This account is currently logged in from another location."));
            return;
        }

        if (!dm.validateLogin(packet.getUser(), packet.getPassword()))
        {
            user.send(new PacketLoginResponse(null, PacketLoginResponse.LoginResponse.FAILURE, "Invalid credentials."));
        } else
        {
            user.setUsername(packet.getUser());
            user.setUserId(dm.getUserByName(packet.getUser()).getUserId());
            user.setDisplayName(packet.getUser() + " - DISPLAY");
            user.setStatus(AbstractUser.UserStatus.ONLINE);
            user.send(new PacketLoginResponse(user.getEncoded(), PacketLoginResponse.LoginResponse.SUCCESS, null));
            ConversationManager.getInstance().addUser(user);

            ConversationManager.getInstance().getAllUsers().forEach(u -> u.send(new PacketUserStatusChange(user, AbstractUser.UserStatus.ONLINE)));
        }
    }
}
