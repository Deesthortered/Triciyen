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
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with authentication");
        }

        return false;
    }
    public boolean registration(UserAccount userAccount) {
        userAccount.setPassword(DigestUtils.sha256Hex(userAccount.getPassword()));

        Optional<UserAccount> givenUserAccountEnvelop = Optional.empty();
        try {
            givenUserAccountEnvelop = userAccountQueryHandler.registrationQuery(userAccount);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Error with registration");
        }

        if (givenUserAccountEnvelop.isEmpty()) {
            return false;
        }

        UserAccount givenUserAccount = givenUserAccountEnvelop.get();
        givenUserAccount.setPassword(userAccount.getPassword());

        if (!userAccount.equals(givenUserAccount)) {
            localStorage.setErrorMessage("Given data is not corresponding with input\n" +
                    "Given: " + givenUserAccount.toString(), "Some troubles with registration");
            return false;
        }

        localStorage.setLogged(givenUserAccountEnvelop.get());
        return true;
    }
}
