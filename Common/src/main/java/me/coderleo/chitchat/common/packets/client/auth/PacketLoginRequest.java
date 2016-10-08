package me.coderleo.chitchat.common.packets.client.auth;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.packets.annotations.ClientSided;

@ClientSided
public class PacketLoginRequest extends Packet
{
    private static final long serialVersionUID = 3664803103471624427L;

    private final String user;
    private final String password;

    public PacketLoginRequest(String user, String password)
    {
        this.user = user;
        this.password = password;
    }

    public String getUser()
    {
        return user;
    }

    public String getPassword()
    {
        return password;
    }
}
