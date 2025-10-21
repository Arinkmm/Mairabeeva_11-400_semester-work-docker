package com.racetalk.util;

import java.util.regex.Pattern;

public class UsernameValidator {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])[a-zA-Z0-9_]{3,20}$");

    public static boolean isValid(String username) {
        if (username == null) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }
}
