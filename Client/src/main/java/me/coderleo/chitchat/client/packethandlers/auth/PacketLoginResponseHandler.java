package me.coderleo.chitchat.client.packethandlers.auth;

import javafx.scene.control.Alert;
import me.coderleo.chitchat.client.Main;
import me.coderleo.chitchat.client.ServerConnector;
import me.coderleo.chitchat.client.User;
import me.coderleo.chitchat.client.managers.ConversationManager;
import me.coderleo.chitchat.client.packethandlers.PacketHandler;
import me.coderleo.chitchat.common.packets.server.auth.PacketLoginResponse;
import me.coderleo.chitchat.common.packets.server.auth.PacketLoginResponse.LoginResponse;
import me.coderleo.chitchat.common.packets.server.auth.PacketRegisterResponse;
import me.coderleo.chitchat.common.packets.server.auth.PacketRegisterResponse.RegisterResponse;

public class PacketLoginResponseHandler extends PacketHandler<PacketLoginResponse>
{
    public PacketLoginResponseHandler()
    {
        super(PacketLoginResponse.class);
    }

    @Override
    public void handle(PacketLoginResponse packet)
    {
        LoginResponse response = LoginResponse.valueOf(packet.getResponse());

        if (response == LoginResponse.SUCCESS)
        {
            ConversationManager.getInstance().setLocalUser(new User(packet.getUser().split(";")));
            Main.getInstance().showMainPanel();
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Login failed!");
            alert.setContentText(packet.getMessage() == null ? "Something went wrong while signing you in. Sorry!" : packet.getMessage());

            alert.showAndWait();
            ServerConnector.getInstance().disconnect();
        }
    }
}
