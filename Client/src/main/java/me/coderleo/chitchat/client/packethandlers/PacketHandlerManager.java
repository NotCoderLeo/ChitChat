package me.coderleo.chitchat.client.packethandlers;

import javafx.application.Platform;
import me.coderleo.chitchat.client.packethandlers.auth.PacketLoginResponseHandler;
import me.coderleo.chitchat.client.packethandlers.auth.PacketRegisterResponseHandler;
import me.coderleo.chitchat.client.packethandlers.etc.SCMotdHandler;
import me.coderleo.chitchat.common.api.Packet;

import java.util.ArrayList;

public class PacketHandlerManager
{
    private static final PacketHandlerManager instance = new PacketHandlerManager();

    public static PacketHandlerManager getInstance()
    {
        return instance;
    }

    private final ArrayList<PacketHandler<?>> handlers = new ArrayList<>();

    private PacketHandlerManager()
    {
        handlers.add(new SCHiHandler());
        handlers.add(new SCMessageHandler());
        handlers.add(new SCMotdHandler());

        // user
        handlers.add(new PacketLoginResponseHandler());
        handlers.add(new PacketRegisterResponseHandler());
        handlers.add(new LogoutHandler());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends Packet> void handle(T packet)
    {
        for (PacketHandler handler : handlers)
        {
            if (handler.getPacketClass().equals(packet.getClass()))
            {
                // javafx thread fix?
                Platform.runLater(() -> handler.handle(packet));
            }
        }
    }
}