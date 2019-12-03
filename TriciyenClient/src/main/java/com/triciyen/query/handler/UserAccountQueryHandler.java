package com.triciyen.query.handler;

import com.triciyen.entity.UserAccount;
import com.triciyen.entity.auxiliary.AuthData;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class UserAccountQueryHandler extends BaseQueryHandler {
    private static final UserAccountQueryHandler instance = new UserAccountQueryHandler();

    private UserAccountQueryHandler() {

    }
    public static UserAccountQueryHandler getInstance() {
        return instance;
    }

    public Optional<UserAccount> authenticateQuery(AuthData authData) throws IOException {
        URL url = new URL(domain + urlAuthentication);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String jsonAuthData = jsonMapper.writeValueAsString(authData);
        writeStringIntoConnectionBody(connection, jsonAuthData);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            UserAccount userAccount = jsonMapper.readValue(jsonResponse, UserAccount.class);
            return Optional.of(userAccount);
        }

        logServerError("UserAccountQueryHandler", "authenticateQuery", connection);

        return Optional.empty();
    }
}
