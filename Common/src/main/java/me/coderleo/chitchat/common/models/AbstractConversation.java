package me.coderleo.chitchat.common.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public abstract class AbstractConversation
{
    protected int id;
    protected List<AbstractUser> members = new ArrayList<>();
    protected String name;
    protected List<Message> messages;
    protected Date created;
}
