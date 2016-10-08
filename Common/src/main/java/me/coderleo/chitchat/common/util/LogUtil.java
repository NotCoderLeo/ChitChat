package me.coderleo.chitchat.common.util;

import org.fusesource.jansi.AnsiConsole;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class LogUtil
{
    private static final String INFO_PREFIX = "[INFO at %s] ";
    private static final String SUCCESS_PREFIX = "[SUCCESS at %s] ";
    private static final String ERROR_PREFIX = "[ERROR at %s] ";
    private static final String WARNING_PREFIX = "[WARN at %s] ";
    private static final String DEBUG_PREFIX = "[DEBUG at %s] ";

    static void setup()
    {
        AnsiConsole.systemInstall();
        info("Log component booted!");
    }

    public static void debug(String message, Object... params)
    {
        System.out.println(ansi().fg(CYAN).a(String.format(DEBUG_PREFIX, getTime().format(new Date())) + String.format(message, params)).reset());
    }

    public static void info(String message, Object... params)
    {
        System.out.println(ansi().fg(GREEN).a(String.format(INFO_PREFIX, getTime().format(new Date())) + String.format(message, params)).reset());
    }

    private static SimpleDateFormat getTime()
    {
        return new SimpleDateFormat("HH:mm:ss");
    }

    public static void success(String message, Object... params)
    {
        System.out.println(ansi().fg(BLUE).a(String.format(SUCCESS_PREFIX, getTime().format(new Date())) + String.format(message, params)).reset());
    }

    public static void warn(String message, Object... params)
    {
        System.out.println(ansi().fg(YELLOW).a(String.format(WARNING_PREFIX, getTime().format(new Date())) + String.format(message, params)).reset());
    }

    public static void error(String message, Object... params)
    {
        System.out.println(ansi().fg(RED).a(String.format(ERROR_PREFIX, getTime().format(new Date())) + String.format(message, params)).reset());
    }
}