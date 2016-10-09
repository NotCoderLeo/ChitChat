package me.coderleo.chitchat.client.packethandlers;

import me.coderleo.chitchat.client.managers.ConversationManager;
import me.coderleo.chitchat.common.packets.universal.PacketMessage;

public class PacketMessageHandler extends PacketHandler<PacketMessage>
{
    public PacketMessageHandler()
    {
        super(PacketMessage.class);
    }

    @Override
    public void handle(PacketMessage packet)
    {
        ConversationManager.getInstance().getConversation(packet.getId())
                .addMessage(packet.getSender(),
                        ConversationManager.getInstance().getConversation(packet.getId()),
                        packet.getMessage().replaceAll("%20", " "),
                        packet.getWhen(),
                        packet.isSystem());
    }
}