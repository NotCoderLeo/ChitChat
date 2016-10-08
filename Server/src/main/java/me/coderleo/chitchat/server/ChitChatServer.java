package me.coderleo.chitchat.server;

import me.coderleo.chitchat.common.CommonConstants;
import me.coderleo.chitchat.common.util.ConcurrentUtil;
import me.coderleo.chitchat.common.util.LogUtil;
import me.coderleo.chitchat.server.data.DBStatements;
import me.coderleo.chitchat.server.managers.ConversationManager;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.net.ServerSocket;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ChitChatServer
{
    private ServerSocket socket;
    private SecretKey key;

    private ChitChatServer()
    {
        EnvironmentData.getInstance();

        try
        {
            DBStatements.getInstance();

//            LogUtil.info("%s", SqlDataManager.getInstance().validateRegistration("jack", "potatotatotato"));

            do
            {
                Random r = new Random();
                int rPort = r.nextInt(65535);

                if (rPort > 1000)
                    socket = new ServerSocket(rPort, 100);
            } while (socket == null);

            LogUtil.info("Using port: %s", socket.getLocalPort());

            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128);
            this.key = generator.generateKey();

            new Thread(() ->
            {
                while (true)
                {
                    try
                    {
                        new User(socket.accept(), key);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new ChitChatServer();
    }
}
