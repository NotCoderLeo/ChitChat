package me.coderleo.chitchat.server.data;

import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.common.util.CryptUtil;
import me.coderleo.chitchat.common.util.LogUtil;
import me.coderleo.chitchat.server.data.statements.Conversations;
import me.coderleo.chitchat.server.data.statements.Users;
import me.coderleo.chitchat.server.interfaces.IDataManager;
import me.coderleo.chitchat.server.managers.ConversationManager;
import me.coderleo.chitchat.server.models.Conversation;
import me.coderleo.chitchat.server.models.ConversationData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlDataManager implements IDataManager
{
    private static final SqlDataManager INSTANCE = new SqlDataManager();

    public static SqlDataManager getInstance()
    {
        return INSTANCE;
    }

    @Override
    public List<AbstractUser> getAllUsers()
    {
        PreparedStatement statement = DBStatements.getInstance().getStatement(Users.FIND_ALL);
        List<AbstractUser> results = new ArrayList<>();

        try
        {
            ResultSet rs = statement.executeQuery();

            while (rs.next())
                results.add(new AbstractUser(
                        rs.getString("username"),
                        rs.getString("displayName"),
                        rs.getInt("id"),
                        false
                ));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return results;
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
                return new Conversation(new ConversationData(rs.getString("name"), new String[0], rs.getInt("id")));
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
                return new Conversation(new ConversationData(rs.getString("name"), new String[0], rs.getInt("id")));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveConversation(Conversation conversation)
    {
        try
        {
            PreparedStatement statement = DBStatements.getInstance().getStatement(Conversations.FIND_BY_ID);
            statement.setInt(1, conversation.getId());

            ResultSet results = statement.executeQuery();

            if (results.first())
            {
                PreparedStatement update = DBStatements.getInstance().getStatement(Conversations.UPDATE_CONVERSATION);
                update.setString(1, conversation.getName());
                update.setInt(2, conversation.getId());

                update.executeUpdate();
                update.close();
            } else
            {
                PreparedStatement create = DBStatements.getInstance().getStatement(Conversations.CREATE_CONVERSATION);

                create.setString(1, conversation.getName());
                create.setBoolean(2, false);

                create.executeUpdate();
                create.close();

                for (String member : conversation.getMembers())
                {
                    AbstractUser user = getUserByName(member);

                    PreparedStatement add = DBStatements.getInstance().getStatement(Conversations.ADD_CONVERSATION_MEMBER);
                    add.setInt(1, user.getUserId());
                    add.setInt(2, conversation.getId());

                    add.executeUpdate();
                    add.close();
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<Conversation> getForUser(AbstractUser user)
    {
        PreparedStatement statement = DBStatements.getInstance().getStatement(Users.GET_CONVERSATIONS);
        List<Conversation> conversations = new ArrayList<>();

        try
        {
            statement.setInt(1, user.getUserId());

            ResultSet results = statement.executeQuery();

            while (results.next())
            {
                int id = results.getInt("conversation_id");
                Conversation conversation = getConversationById(id);

                if (conversation == null)
                {
                    continue;
                }

                List<String> members = new ArrayList<>();

                PreparedStatement memberStatement = DBStatements.getInstance().getStatement(Conversations.FIND_MEMBERS);
                memberStatement.setInt(1, id);

                ResultSet memberResults = memberStatement.executeQuery();

                while (memberResults.next())
                {
                    AbstractUser u = getUserById(memberResults.getInt("user_id"));
                    members.add(u.getUsername());
                }

                conversations.add(new Conversation(conversation.getName(), id, members.toArray(new String[members.size()])));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return conversations;
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

    @Override
    public void loadConversations()
    {
        PreparedStatement statement = DBStatements.getInstance().getStatement(Conversations.FIND_ALL);

        try
        {
            ResultSet results = statement.executeQuery();

            while (results.next())
            {
                int id = results.getInt("id");
                String name = results.getString("name");

                List<String> members = new ArrayList<>();

                PreparedStatement memberStatement = DBStatements.getInstance().getStatement(Conversations.FIND_MEMBERS);
                memberStatement.setInt(1, id);

                ResultSet memberResults = memberStatement.executeQuery();

                while (memberResults.next())
                {
                    AbstractUser user = getUserById(memberResults.getInt("user_id"));
                    members.add(user.getUsername());
                }

                ConversationManager.getInstance().addConversation(
                        new Conversation(name, id, members.toArray(new String[members.size()]))
                );
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
