package me.coderleo.chitchat.common.util;

import org.mindrot.BCrypt;

import java.security.MessageDigest;

public class CryptUtil
{
    public static String stringToSHA256(String base)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte aHash : hash)
            {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Encrypt a string with BCrypt
     *
     * @param input the string to encrypt
     * @return
     */
    public static String stringBcrypt(String input)
    {
        return stringBcrypt(input, 10);
    }

    /**
     * Encrypt a string with BCrypt
     *
     * @param input  the string to encrypt
     * @param rounds log_rounds parameter
     * @return
     */
    public static String stringBcrypt(String input, int rounds)
    {
        return BCrypt.hashpw(input, BCrypt.gensalt(rounds));
    }

    /**
     * Check to see if a plaintext input matches the encrypted form.
     *
     * @param input
     * @param encrypted
     * @return
     */
    public static boolean checkBcrypt(String input, String encrypted)
    {
        return BCrypt.checkpw(input, encrypted);
    }
}
