package com.triciyen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triciyen.entity.UserAccount;
import com.triciyen.entity.auxiliary.AuthData;
import com.triciyen.query.handler.UserAccountQueryHandler;

import java.io.IOException;

public class UserAccountService {
    private static final UserAccountService instance = new UserAccountService();

    private UserAccountService() {

    }
    public static UserAccountService getInstance() {
        return instance;
    }

    public void authenticate(String login, String password) {
        UserAccountQueryHandler queryHandler = UserAccountQueryHandler.getInstance();

        AuthData authData = new AuthData(login, password);
        UserAccount account;
        try {
            account = queryHandler
                    .authenticateQuery(authData)
                    .orElse(UserAccount.builder().login("Error").build());
            ObjectMapper jsonMapper = new ObjectMapper();
            System.out.println(jsonMapper.writeValueAsString(account));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
