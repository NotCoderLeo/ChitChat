package me.coderleo.chitchat.common.models;

import lombok.Data;

import java.util.Date;

@Data
public class Contact
{
    private final AbstractUser owner;
    private final AbstractUser target;
    private String displayName;

    private Date lastSeen;
    private final Date added;
}
