package me.coderleo.chitchat.client.packethandlers.auth;

import javafx.scene.control.Alert;
import me.coderleo.chitchat.client.Main;
import me.coderleo.chitchat.client.ServerConnector;
import me.coderleo.chitchat.client.User;
import me.coderleo.chitchat.client.managers.ConversationManager;
import me.coderleo.chitchat.client.packethandlers.PacketHandler;
import me.coderleo.chitchat.common.packets.server.auth.PacketRegisterResponse;
import me.coderleo.chitchat.common.packets.server.auth.PacketRegisterResponse.RegisterResponse;
import me.coderleo.chitchat.common.util.LogUtil;

public class PacketRegisterResponseHandler extends PacketHandler<PacketRegisterResponse>
{
    public PacketRegisterResponseHandler()
    {
        super(PacketRegisterResponse.class);
    }

    @Override
    public void handle(PacketRegisterResponse packet)
    {
        LogUtil.info("received registration response");
        RegisterResponse response = RegisterResponse.valueOf(packet.getResponse());

        if (response == RegisterResponse.SUCCESS)
        {
            ConversationManager.getInstance().setLocalUser(new User(packet.getUser().split(";")));
            Main.getInstance().showMainPanel();
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Registration failed.");
            alert.setContentText("Something went wrong while creating your account. Sorry!");

            alert.showAndWait();
            ServerConnector.getInstance().disconnect();
        }
    }
}
