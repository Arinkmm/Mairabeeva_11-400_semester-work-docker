package com.racetalk.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasherUtil {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        if (hashedPassword == null || !hashedPassword.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash for checking");
        }
        return BCrypt.checkpw(password, hashedPassword);
    }
}
