package com.triciyen.query.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triciyen.entity.UserAccount;
import com.triciyen.entity.auxiliary.AuthData;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ProfileQueryHandler implements QueryHandlerInterface {
    private static final ProfileQueryHandler instance = new ProfileQueryHandler();
    private static ObjectMapper jsonMapper = new ObjectMapper();

    private ProfileQueryHandler() {

    }
    public static ProfileQueryHandler getInstance() {
        return instance;
    }

    public UserAccount authenticateQuery(AuthData authData) throws IOException {
        URL url = new URL(domain + "/http_api/auth");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String jsonAuthData = jsonMapper.writeValueAsString(authData);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonAuthData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        String jsonResponse;
        try (var br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder responseBuilder = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                responseBuilder.append(responseLine.trim());
            }
            jsonResponse = responseBuilder.toString();
        }

        UserAccount userAccount = jsonMapper.readValue(jsonResponse, UserAccount.class);

        return userAccount;
    }
}
