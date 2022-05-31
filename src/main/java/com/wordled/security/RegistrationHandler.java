package com.wordled.security;

public class RegistrationHandler {

    /**
     * Simple method to check if passwords are matching
     * @param pass pass
     * @param pass1 pass1
     * @return true if passwords match, false if not
     */
    public static boolean doPasswordsMatch(String pass, String pass1) {
        return pass.equals(pass1);
    }

}
