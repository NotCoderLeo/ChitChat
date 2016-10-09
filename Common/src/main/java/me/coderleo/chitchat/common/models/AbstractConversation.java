package me.coderleo.chitchat.common.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AbstractConversation
{
    protected int id;
    protected List<String> members = new ArrayList<>();
    protected String name;
    protected List<Message> messages = new ArrayList<>();
    protected Date created;
}
