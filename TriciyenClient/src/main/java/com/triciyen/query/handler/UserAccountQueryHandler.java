package com.triciyen.query.handler;

import com.triciyen.entity.UserAccount;
import com.triciyen.entity.auxiliary.AuthData;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Optional;

public class UserAccountQueryHandler extends BaseQueryHandler {
    private static final UserAccountQueryHandler instance = new UserAccountQueryHandler();

    private UserAccountQueryHandler() {

    }
    public static UserAccountQueryHandler getInstance() {
        return instance;
    }

    public Optional<UserAccount> authenticateQuery(AuthData authData) throws IOException {
        String jsonAuthData = jsonMapper.writeValueAsString(authData);
        HttpURLConnection connection = makePostQuery(urlAuthentication, jsonAuthData);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            UserAccount userAccount = jsonMapper.readValue(jsonResponse, UserAccount.class);
            return Optional.of(userAccount);
        }

        logServerError("UserAccountQueryHandler", "authenticateQuery", connection);

        return Optional.empty();
    }
    public Optional<UserAccount> registrationQuery(UserAccount userAccount) throws IOException {
        String jsonUserAccount = jsonMapper.writeValueAsString(userAccount);
        HttpURLConnection connection = makePostQuery(urlRegistration, jsonUserAccount);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            UserAccount givenUserAccount = jsonMapper.readValue(jsonResponse, UserAccount.class);
            return Optional.of(givenUserAccount);
        }

        logServerError("UserAccountQueryHandler", "registrationQuery", connection);

        return Optional.empty();
    }
}
