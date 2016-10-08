package me.coderleo.chitchat.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.coderleo.chitchat.common.util.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class EnvironmentData
{
    private static EnvironmentData ourInstance = new EnvironmentData();
    private final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    public static EnvironmentData getInstance()
    {
        return ourInstance;
    }

    private String databaseHost;
    private String databaseUser;
    private String databasePass;

    private EnvironmentData()
    {
        Credentials credentials = readCredentials();

        this.databaseHost = credentials.getMysqlUrl();
        this.databasePass = credentials.getMysqlPass();
        this.databaseUser = credentials.getMysqlUser();
    }

    public String getDatabaseHost()
    {
        return databaseHost;
    }

    public String getDatabaseUser()
    {
        return databaseUser;
    }

    public String getDatabasePass()
    {
        return databasePass;
    }

    /**
     * Reads credentials from the credentials.json file.
     *
     * @return a credentials object
     */
    private Credentials readCredentials()
    {
        final Path credentialsFile = new File(".").toPath().resolve("credentials.json");
        Credentials credentials = null;

        if (!credentialsFile.toFile().exists())
        {
            LogUtil.info("No credentials were found. Creating the file.");
            LogUtil.info("You will need to edit the file to use the correct credentials.");

            credentials = new Credentials();
            String jsonOut = prettyGson.toJson(credentials);

            try (BufferedWriter writer = Files.newBufferedWriter(credentialsFile, StandardCharsets.UTF_8))
            {
                writer.append(jsonOut);
                writer.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            System.exit(0);

            return credentials;
        } else
        {
            try (BufferedReader reader = Files.newBufferedReader(credentialsFile, StandardCharsets.UTF_8))
            {
                credentials = prettyGson.fromJson(reader, Credentials.class);
                reader.close();

                LogUtil.info("Loaded credentials.");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return credentials;
    }
}
