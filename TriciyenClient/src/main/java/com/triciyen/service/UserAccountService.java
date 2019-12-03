package com.triciyen.service;

import com.triciyen.entity.UserAccount;
import com.triciyen.entity.auxiliary.AuthData;
import com.triciyen.query.handler.UserAccountQueryHandler;

import java.io.IOException;
import java.util.Optional;

public class UserAccountService {
    private static final UserAccountService instance = new UserAccountService();

    private UserAccountService() {

    }
    public static UserAccountService getInstance() {
        return instance;
    }

    public boolean authenticate(String login, String password) {
        UserAccountQueryHandler queryHandler = UserAccountQueryHandler.getInstance();
        StateService stateService = StateService.getInstance();
        AuthData authData = new AuthData(login, password);

        try {
            Optional<UserAccount> account = queryHandler.authenticateQuery(authData);
            if (account.isPresent()) {
                stateService.setLogged(account.get());
                return true;
            }
        } catch (IOException e) {
            stateService.setServerErrorMessage(e.getMessage());
        }

        return false;
    }
}
