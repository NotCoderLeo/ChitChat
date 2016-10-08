package me.coderleo.chitchat.server.interfaces;

import me.coderleo.chitchat.common.models.AbstractUser;
import me.coderleo.chitchat.server.models.Conversation;

import java.util.List;

/**
 * Represents a data manager.
 * <p>
 * Data managers must declare the following methods:
 *
 * - get user by id
 * - get user by name
 * - get conversation by id
 * - get conversation by name
 * - get user's conversations
 * - password validation
 * - registration validation
 * - create user
 * - create conversation
 * - update user password
 */
public interface IDataManager
{
    AbstractUser getUserById(int id);

    AbstractUser getUserByName(String name);

    Conversation getConversationById(int id);

    Conversation getConversationByName(String name);

    List<Conversation> getForUser(AbstractUser user);

    boolean validateLogin(String username, String password);

    boolean validateRegistration(String username, String password);

    AbstractUser createUser(String username, String password);

    Conversation createConversation(String name, List<AbstractUser> members);

    void updateUserPassword(AbstractUser user, String oldPass, String newPass);
}
