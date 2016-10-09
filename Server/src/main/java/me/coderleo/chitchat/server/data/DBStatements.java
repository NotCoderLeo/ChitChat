package me.coderleo.chitchat.server.data;

import me.coderleo.chitchat.common.util.LogUtil;
import me.coderleo.chitchat.server.EnvironmentData;
import me.coderleo.chitchat.server.data.statements.Conversations;
import me.coderleo.chitchat.server.data.statements.Users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class DBStatements
{
    private static DBStatements ourInstance = new DBStatements();

    public static DBStatements getInstance()
    {
        return ourInstance;
    }

    private HashMap<String, PreparedStatement> preparedStatements;
    private Connection connection;

    private DBStatements()
    {
        EnvironmentData data = EnvironmentData.getInstance();

        preparedStatements = new HashMap<>();

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(data.getDatabaseHost(), data.getDatabaseUser(), data.getDatabasePass());

            LogUtil.info("Connected to MySQL!");

            addUserStatements();
            addConversationStatements();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void addConversationStatements() throws SQLException
    {
        preparedStatements.put(Conversations.CREATE_CONVERSATION, connection.prepareStatement(
                "INSERT INTO conversations (name, isGroup) VALUES (?, ?)"
        ));

        preparedStatements.put(Conversations.UPDATE_CONVERSATION, connection.prepareStatement(
                "UPDATE conversations SET name = ? WHERE id = ?"
        ));

        preparedStatements.put(Conversations.ADD_CONVERSATION_MEMBER, connection.prepareStatement(
                "INSERT INTO conversation_members (user_id, conversation_id) VALUES (?, ?)"
        ));

        preparedStatements.put(Conversations.FIND_BY_ID, connection.prepareStatement(
                "SELECT * FROM conversations WHERE id = ?"
        ));

        preparedStatements.put(Conversations.FIND_BY_NAME, connection.prepareStatement(
                "SELECT * FROM conversations WHERE name = ?"
        ));

        preparedStatements.put(Conversations.FIND_ALL, connection.prepareStatement(
                "SELECT * FROM conversations"
        ));

        preparedStatements.put(Conversations.FIND_MEMBERS, connection.prepareStatement(
                "SELECT * FROM conversation_members WHERE conversation_id = ?"
        ));
    }

    private void addUserStatements() throws SQLException
    {
        preparedStatements.put(Users.CREATE_USER, connection.prepareStatement(
                "INSERT INTO users (username, password) VALUES (?, ?)"
        ));

        preparedStatements.put(Users.CHECK_USER_PASSWORD, connection.prepareStatement(
                "SELECT * FROM users WHERE username = ?"
        ));

        preparedStatements.put(Users.FIND_BY_ID, connection.prepareStatement(
                "SELECT * FROM users WHERE id = ?"
        ));

        preparedStatements.put(Users.FIND_BY_NAME, connection.prepareStatement(
                "SELECT * FROM users WHERE username = ?"
        ));

        preparedStatements.put(Users.FIND_ALL, connection.prepareStatement(
                "SELECT * FROM users"
        ));

        preparedStatements.put(Users.UPDATE_PASSWORD, connection.prepareStatement(
                "UPDATE users SET password = ? WHERE username = ? AND password = ?"
        ));

        preparedStatements.put(Users.GET_CONVERSATIONS, connection.prepareStatement(
                "SELECT * FROM conversation_members WHERE user_id = ?"
        ));
    }

    public Connection getConnection()
    {
        return connection;
    }

    public HashMap<String, PreparedStatement> getPreparedStatements()
    {
        return preparedStatements;
    }

    public PreparedStatement getStatement(String statementName)
    {
        if (!preparedStatements.containsKey(statementName))
            throw new RuntimeException("The statement: '" + statementName + "' does not exist.");
        return preparedStatements.get(statementName);
    }
}
