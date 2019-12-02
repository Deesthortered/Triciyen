package com.triciyen.query.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triciyen.service.StateService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

abstract class BaseQueryHandler {
    StateService stateService = StateService.getInstance();
    ObjectMapper jsonMapper = new ObjectMapper();
    String domain = "http://localhost:8080";
    String urlAuthentication = "/http_api/auth";

    void writeStringIntoConnectionBody(HttpURLConnection connection, String data) throws IOException {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.flush();
        }
    }
    String readResponseBody(HttpURLConnection connection) throws IOException {
        String result;
        try (var br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder responseBuilder = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                responseBuilder.append(responseLine.trim());
            }
            result = responseBuilder.toString();
        }
        return result;
    }
    String readResponseError(HttpURLConnection connection) throws IOException {
        String result;
        try (var br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
            StringBuilder responseBuilder = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                responseBuilder.append(responseLine.trim());
            }
            result = responseBuilder.toString();
        }
        return result;
    }
}
