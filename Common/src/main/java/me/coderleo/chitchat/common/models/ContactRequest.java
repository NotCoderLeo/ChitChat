package me.coderleo.chitchat.common.models;

import java.util.Date;

public class ContactRequest
{
    private AbstractUser from;
    private AbstractUser to;
    private String message;

    private Date sent;
}
