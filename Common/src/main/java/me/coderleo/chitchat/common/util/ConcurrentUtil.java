package me.coderleo.chitchat.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ConcurrentUtil
{
    public static final ExecutorService pool = Executors.newFixedThreadPool(150);
    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
}
