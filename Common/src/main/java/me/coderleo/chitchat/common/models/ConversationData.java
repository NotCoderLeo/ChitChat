package me.coderleo.chitchat.common.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConversationData implements Serializable
{
    private static final long serialVersionUID = -8228690534652292411L;

    private final String name;
    private final AbstractUser[] members;
}
