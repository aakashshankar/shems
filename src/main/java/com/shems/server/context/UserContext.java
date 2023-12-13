package com.shems.server.context;


public class UserContext {

    private static final ThreadLocal<Long> CURRENT_USER = new ThreadLocal<>();

    public static Long getCurrentUser() {
        return CURRENT_USER.get();
    }

    public static void setCurrentUser(Long user) {
        CURRENT_USER.set(user);
    }

}
