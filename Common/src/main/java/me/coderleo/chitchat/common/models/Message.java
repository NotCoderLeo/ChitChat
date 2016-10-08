package me.coderleo.chitchat.common.models;

import lombok.Data;

import java.util.Calendar;

@Data
public class Message
{
    private final AbstractUser sender;
    private final AbstractConversation conversation;
    private final String body;
    private final Calendar when;
}
