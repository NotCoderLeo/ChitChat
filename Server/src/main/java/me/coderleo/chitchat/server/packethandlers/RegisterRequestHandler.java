package me.coderleo.chitchat.server.packethandlers;

import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.packets.client.auth.PacketRegisterRequest;
import me.coderleo.chitchat.common.packets.server.auth.PacketRegisterResponse;
import me.coderleo.chitchat.common.util.LogUtil;
import me.coderleo.chitchat.server.User;
import me.coderleo.chitchat.server.data.SqlDataManager;

class RegisterRequestHandler extends PacketHandler<PacketRegisterRequest>
{
    RegisterRequestHandler()
    {
        super(PacketRegisterRequest.class);
    }

    @Override
    public void handle(PacketRegisterRequest packet, User user)
    {
        LogUtil.info("Received registration request.");

        SqlDataManager dataManager = SqlDataManager.getInstance();

        if (!dataManager.validateRegistration(packet.getUser(), packet.getPassword()))
        {
            LogUtil.warn("Rejected registration request.");
            user.send(new PacketRegisterResponse(null, PacketRegisterResponse.RegisterResponse.FAILURE));
        } else
        {
            LogUtil.info("Accepted registration request.");
            AbstractUser result = dataManager.getUserByName(packet.getUser());
            LogUtil.info("%s", result);

            user.send(new PacketRegisterResponse(result.getEncoded(), PacketRegisterResponse.RegisterResponse.SUCCESS));
        }
//        user.send(new PacketSCMotd(
//                "Hi there, " + packet.getUser() + "! Authentication is currently in development.",
//                PacketSCMotd.Level.WARNING));
    }
}
