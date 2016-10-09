package me.coderleo.chitchat.common.packets.universal;

import me.coderleo.chitchat.common.api.Packet;
import me.coderleo.chitchat.common.models.AbstractConversation;

public class PacketConversationCreate extends Packet
{
    private static final long serialVersionUID = -6536580021206781744L;

    private final String conversation, users;

    public PacketConversationCreate(AbstractConversation conversation)
    {
        this(conversation.getName(), arrayToString(conversation.getMembers().toArray(new String[conversation.getMembers().size()])));
    }

    public PacketConversationCreate(String conversation, String... users)
    {
        this.conversation = conversation;
        this.users = join(users);
    }

    public String getConversation()
    {
        return conversation;
    }

    public String getUsers()
    {
        return users;
    }

    private static <T> String[] arrayToString(T[] array)
    {
        String[] strs = new String[array.length];

        for (int i = 0; i < strs.length; i++)
        {
            strs[i] = array[i].toString();
        }

        return strs;
    }

    private String join(String[] strs)
    {
        StringBuilder builder = new StringBuilder();

        for (String str : strs)
        {
            builder.append(str).append(",");
        }

        return builder.toString().trim();
    }
}
