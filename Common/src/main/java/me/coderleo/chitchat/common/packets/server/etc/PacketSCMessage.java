package me.coderleo.chitchat.common.packets.server.etc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.coderleo.chitchat.common.api.Packet;

@Data
@EqualsAndHashCode
public class PacketSCMessage extends Packet
{
    private static final long serialVersionUID = 1333773773245557373L;

    public enum Level
    {
        INFO,
        WARNING,
        ERROR,
        ANNOUNCEMENT
    }

    private final String message;
    private final Level level;
}
