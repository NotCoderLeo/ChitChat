package me.coderleo.chitchat.common.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;

@Data
public class Message
{
    private final String sender;
    private final String body;
    private final Calendar when;
}
