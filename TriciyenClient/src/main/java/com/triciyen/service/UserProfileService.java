package com.triciyen.service;

public class UserProfileService {
    private static final UserProfileService instance = new UserProfileService();

    private UserProfileService() {

    }
    public static UserProfileService getInstance() {
        return instance;
    }

    public void authenticate(String login, String password) {

    }
}
