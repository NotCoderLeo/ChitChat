package me.coderleo.chitchat.client.packethandlers;

import me.coderleo.chitchat.client.Main;
import me.coderleo.chitchat.client.User;
import me.coderleo.chitchat.client.models.Conversation;
import me.coderleo.chitchat.common.models.Message;
import me.coderleo.chitchat.common.packets.server.conversation.PacketSCMessage;

import java.util.Calendar;

class SCMessageHandler extends PacketHandler<PacketSCMessage>
{
    SCMessageHandler()
    {
        super(PacketSCMessage.class);
    }

    @Override
    public void handle(PacketSCMessage packet)
    {
        User user = new User(packet.getSender().getEncoded().split(";"));
        Message message = packet.getMessage();

//        Main.getInstance().getMainPanel().getChatPanel().messageReceived(
//                new Message(new User(new String[]{packet.getSender(), "", "-1"}),
//                        new Conversation("Testing"),
//                        packet.getMessage().replace("%20", " "),
//                        Calendar.getInstance())
//        );
    }
}
