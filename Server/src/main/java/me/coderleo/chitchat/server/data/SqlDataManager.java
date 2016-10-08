package me.coderleo.chitchat.server.data;

import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.util.CryptUtil;
import me.coderleo.chitchat.server.data.statements.Conversations;
import me.coderleo.chitchat.server.data.statements.Users;
import me.coderleo.chitchat.server.interfaces.IDataManager;
import me.coderleo.chitchat.server.models.Conversation;
import me.coderleo.chitchat.server.models.ConversationData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlDataManager implements IDataManager
{
    private static final SqlDataManager INSTANCE = new SqlDataManager();

    public static SqlDataManager getInstance()
    {
        return INSTANCE;
    }

    @Override
    public AbstractUser getUserById(int id)
    {
        PreparedStatement statement = DBStatements.getInstance().getStatement(Users.FIND_BY_ID);

        try
        {
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

//            statement.close();

            if (rs.next())
                return new AbstractUser(rs.getString("username"), rs.getString("displayName"), rs.getInt("id"), false);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AbstractUser getUserByName(String name)
    {
        PreparedStatement statement = DBStatements.getInstance().getStatement(Users.FIND_BY_NAME);

        try
        {
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();

            if (rs.next())
                return new AbstractUser(rs.getString("username"), rs.getString("displayName"), rs.getInt("id"), false);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Conversation getConversationById(int id)
    {
        PreparedStatement statement = DBStatements.getInstance().getStatement(Conversations.FIND_BY_ID);

        try
        {
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

//            statement.close();

            if (rs.next())
                return new Conversation(new ConversationData(rs.getString("name"), new AbstractUser[0]));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Conversation getConversationByName(String name)
    {
        PreparedStatement statement = DBStatements.getInstance().getStatement(Conversations.FIND_BY_NAME);

        try
        {
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();

//            statement.close();

            if (rs.next())
                return new Conversation(new ConversationData(rs.getString("name"), new AbstractUser[0]));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Conversation> getForUser(AbstractUser user)
    {
        return null;
    }

    @Override
    public boolean validateLogin(String username, String password)
    {
        PreparedStatement statement = DBStatements.getInstance().getStatement(Users.CHECK_USER_PASSWORD);
        boolean success = false;

        try
        {
            DBStatements.getInstance().getConnection().setAutoCommit(false);

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next())
                success = CryptUtil.checkBcrypt(password, rs.getString("password"));

            DBStatements.getInstance().getConnection().commit();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean validateRegistration(String username, String password)
    {
        if (getUserByName(username) != null)
        {
            return false;
        }

        createUser(username, password);

        return true;
    }

    @Override
    public AbstractUser createUser(String username, String password)
    {
        try
        {
            DBStatements.getInstance().getConnection().setAutoCommit(false);

            PreparedStatement statement = DBStatements.getInstance().getStatement(Users.CREATE_USER);
            statement.setString(1, username);
            statement.setString(2, CryptUtil.stringBcrypt(password, 10));

            int id = statement.executeUpdate();

            DBStatements.getInstance().getConnection().commit();

            return new AbstractUser(username, null, id, false);
        } catch (SQLException e)
        {
            if (!e.getMessage().contains("Duplicate entry"))
                e.printStackTrace();
        }

        return null;
    }

    @Override
    public Conversation createConversation(String name, List<AbstractUser> members)
    {
        return null;
    }

    @Override
    public void updateUserPassword(AbstractUser user, String oldPass, String newPass)
    {

    }
}
