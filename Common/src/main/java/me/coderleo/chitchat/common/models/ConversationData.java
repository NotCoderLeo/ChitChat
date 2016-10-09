package me.coderleo.chitchat.common.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class ConversationData implements Serializable
{
    private static final long serialVersionUID = -8228690534652292411L;

    private final String name;
    private final int id;
    private final String[] members;

    public List<AbstractUser> getUsers(ConversationData data, Function<String, AbstractUser> mapper)
    {
        return Arrays.stream(data.getMembers())
                .map(mapper)
                .collect(Collectors.toList());
    }
}
