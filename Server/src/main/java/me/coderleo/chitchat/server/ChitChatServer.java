package me.coderleo.chitchat.server;

import me.coderleo.chitchat.common.util.LogUtil;
import me.coderleo.chitchat.server.data.DBStatements;
import me.coderleo.chitchat.server.data.SqlDataManager;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.net.ServerSocket;
import java.util.Random;

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
            SqlDataManager.getInstance().loadConversations();

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
