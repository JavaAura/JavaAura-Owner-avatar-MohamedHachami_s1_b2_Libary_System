package com.example.Model;

public class UserSession {
    private static Users currentUser;

    public static void setUser(Users user) {
        currentUser = user;
    }

    public static Users getUser() {
        return currentUser;
    }

    public static void clearSession() {
        currentUser = null;
    }
}
