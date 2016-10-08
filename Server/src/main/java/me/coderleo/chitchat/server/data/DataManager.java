package me.coderleo.chitchat.server.data;

import me.coderleo.chitchat.common.models.AbstractConversation;
import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.models.ConversationData;
import me.coderleo.chitchat.common.util.CryptUtil;
import me.coderleo.chitchat.common.util.LogUtil;
import me.coderleo.chitchat.server.EnvironmentData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager
{
    private static final DataManager INSTANCE = new DataManager();
    private static final String DISPLAY_NAME_FIELD = "displayName";
    private Connection connection;

    private DataManager()
    {
        //
    }

    /**
     * Set up the data manager.
     */
    public void setup()
    {
        EnvironmentData data = EnvironmentData.getInstance();

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    data.getDatabaseHost(),
                    data.getDatabaseUser(),
                    data.getDatabasePass()
            );

            LogUtil.info("Initialized MySQL connection!");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean checkPassword(final String username, final String password)
    {
        boolean success = false;

        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT password FROM users where username = ?");
            statement.setString(1, username);

            ResultSet results = statement.executeQuery();

            if (results.next())
            {
                success = CryptUtil.checkBcrypt(password, results.getString("password"));
            }

            statement.close();
            results.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return success;
    }

    public boolean validateRegistration(final String username, final String password)
    {
        boolean success = false;

        try
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM members where username = ?"
            );

            statement.setString(1, username);
            ResultSet results = statement.executeQuery();

            if (results.first())
            {
                success = false;
            } else
            {
                PreparedStatement s = connection.prepareStatement(
                        "INSERT INTO members (username, password, displayName) VALUES (?, ?, ?)"
                );

                s.setString(1, username);
                s.setString(2, CryptUtil.stringBcrypt(password));
                s.setString(3, username);

                s.executeUpdate();
                s.close();
            }

            statement.close();
            results.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return success;
    }

    public boolean changePassword(String username, String oldPass, String newPass)
    {
        return false;
    }

    public void changeDisplayName(int userId, String password, String newDisplayName)
    {
        //
    }

    public String getDisplayName(int id)
    {
        return "N/A";
    }

    private void saveConversation(final AbstractConversation conversation)
    {
        //
    }

    public List<ConversationData> getConversationsForUser(final int userId)
    {
        return new ArrayList<>();
    }

    public List<ConversationData> getAllConversations()
    {
        return new ArrayList<>();
    }

    public AbstractUser findByName(String name)
    {
        try
        {
            PreparedStatement s = connection.prepareStatement("SELECT * FROM members WHERE username = ?");
            s.setString(1, name);

            ResultSet results = s.executeQuery();

            if (results.next())
                return new AbstractUser(results.getString("name"), results.getString("displayName"),
                        results.getInt("id"), false);

            results.close();
            s.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public AbstractUser findById(int id)
    {
        try
        {
            PreparedStatement s = connection.prepareStatement("SELECT * FROM members WHERE id = ?");
            s.setInt(1, id);

            ResultSet results = s.executeQuery();

            if (results.next())
                return new AbstractUser(results.getString("name"), results.getString("displayName"),
                        results.getInt("id"), false);

            results.close();
            s.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public static DataManager getInstance()
    {
        return INSTANCE;
    }
}
