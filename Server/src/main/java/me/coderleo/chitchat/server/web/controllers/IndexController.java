package me.coderleo.chitchat.server.web.controllers;

import me.coderleo.chitchat.server.data.SqlDataManager;
import me.coderleo.chitchat.server.managers.ConversationManager;
import me.coderleo.chitchat.server.web.Stats;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController
{
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String showIndex()
    {
        return "Hi!";
    }

    @RequestMapping(value = "/stats", method = {RequestMethod.GET})
    @ResponseBody
    public Stats showStats()
    {
        return new Stats(
                SqlDataManager.getInstance().getAllUsers().size(),
                ConversationManager.getInstance().getAllUsers().size(),
                ConversationManager.getInstance().getConversations().size()
        );
    }
}
