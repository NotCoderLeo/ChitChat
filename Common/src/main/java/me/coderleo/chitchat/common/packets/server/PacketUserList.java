package me.coderleo.chitchat.common.packets.server;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.packets.annotations.ServerSided;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ServerSided
public class PacketUserList extends Packet
{
    private static final long serialVersionUID = 1168341392763009280L;

    private String[] users;

    public PacketUserList(final List<? extends AbstractUser> users)
    {
        List<String> converted = convert(users);

        this.users = converted.toArray(new String[converted.size()]);
    }

    public String[] getUsers()
    {
        return users;
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
