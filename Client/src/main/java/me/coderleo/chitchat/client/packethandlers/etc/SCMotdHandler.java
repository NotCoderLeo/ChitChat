package me.coderleo.chitchat.client.packethandlers.etc;

import javafx.scene.control.Alert;
import me.coderleo.chitchat.client.packethandlers.PacketHandler;
import me.coderleo.chitchat.common.packets.server.etc.PacketSCMessage;

public class SCMotdHandler extends PacketHandler<PacketSCMessage>
{
    public SCMotdHandler()
    {
        super(PacketSCMessage.class);
    }

    @Override
    public void handle(PacketSCMessage packet)
    {
//        Platform.runLater(() -> {
        Alert.AlertType type = Alert.AlertType.INFORMATION;

        switch (packet.getLevel())
        {
            case INFO:
            case ANNOUNCEMENT:
                type = Alert.AlertType.INFORMATION;
                break;
            case WARNING:
                type = Alert.AlertType.WARNING;
                break;
            case ERROR:
                type = Alert.AlertType.ERROR;
                break;
            default:
                type = Alert.AlertType.INFORMATION;
                break;
        }

        Alert alert = new Alert(type);
        alert.setTitle("Server Message");
        alert.setHeaderText(null);
        alert.setContentText(packet.getMessage().replace("%20", " "));

        alert.showAndWait();
//        });
    }
}
