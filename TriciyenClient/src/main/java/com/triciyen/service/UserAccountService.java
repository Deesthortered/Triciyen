package com.triciyen.service;

import com.triciyen.entity.UserAccount;
import com.triciyen.entity.auxiliary.AuthData;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.Optional;

public class UserAccountService implements BaseService {
    private static final UserAccountService instance = new UserAccountService();

    private UserAccountService() {

    }
    public static UserAccountService getInstance() {
        return instance;
    }

    public boolean authenticate(String login, String password) {
        String encryptedPassword = DigestUtils.sha256Hex(password);
        AuthData authData = new AuthData(login, encryptedPassword);

        try {
            Optional<UserAccount> account = userAccountQueryHandler.authenticateQuery(authData);
            if (account.isPresent()) {
                localStorage.setLogged(account.get());
                return true;
            }
        } catch (IOException e) {
            localStorage.setServerErrorMessage(e.getMessage());
        }

        return false;
    }
    public boolean registration(UserAccount userAccount) {
        userAccount.setPassword(DigestUtils.sha256Hex(userAccount.getPassword()));

        Optional<UserAccount> givenUserAccount = Optional.empty();
        try {
            givenUserAccount = userAccountQueryHandler.registrationQuery(userAccount);
        } catch (IOException e) {
            localStorage.setServerErrorMessage(e.getMessage());
        }

        if (givenUserAccount.isEmpty()) {
            return false;
        }

        if (!userAccount.equals(givenUserAccount.get())) {
            localStorage.setServerErrorMessage("Given data is not corresponding with input\n" +
                    "Given: " + givenUserAccount.get().toString());
            return false;
        }

        return true;
    }
}
