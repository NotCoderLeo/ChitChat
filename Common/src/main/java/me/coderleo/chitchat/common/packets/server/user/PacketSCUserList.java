package me.coderleo.chitchat.common.packets.server.user;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractUser;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PacketSCUserList extends Packet
{
    private static final long serialVersionUID = -1448282288882882244L;

    private List<String> users;

    public PacketSCUserList(List<? extends AbstractUser> users)
    {
        this.users = convert(users);
    }

    public List<String> getUsers()
    {
        return users;
    }

    public void setUsers(List<String> users)
    {
        this.users = users;
    }

    private List<String> convert(List<? extends AbstractUser> users)
    {
        return users.stream().map(a ->
                StringUtils.join(
                        Arrays.asList(a.getUsername(),
                                a.getDisplayName(),
                                a.getUserId()),
                        ";"
                )).collect(Collectors.toList());
    }
}
