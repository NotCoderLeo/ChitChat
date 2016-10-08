package me.coderleo.chitchat.server;

import lombok.Getter;
import lombok.Setter;

public class Credentials
{
    @Getter
    @Setter
    protected String mysqlUrl = "";

    @Getter
    @Setter
    protected String mysqlUser = "";

    @Getter
    @Setter
    protected String mysqlPass = "";
}
