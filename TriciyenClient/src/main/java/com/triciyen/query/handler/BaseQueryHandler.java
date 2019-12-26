package com.triciyen.query.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triciyen.LocalStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

abstract class BaseQueryHandler {
    LocalStorage localStorage = LocalStorage.getInstance();
    ObjectMapper jsonMapper = new ObjectMapper();
    private String domain = "http://localhost:8080";
    String urlAuthentication = "/http_api/auth";
    String urlRegistration = "/http_api/registration";
    String urlGetAllSubscribedConversations = "/http_api/getConversations/";
    String urlGetLastReadMessageIdOfConversation = "/http_api/getLastReadMessageIdOfConversation/";
    String urlGetLastMessagesOfConversation = "/http_api/getListOfLastMessages/";
    String urlGetPageOfElderMessagesOfConversation = "/http_api/getPageOfElderMessages/";
    String urlSetLastReadMessageOfConversation = "/http_api/setLastReadMessage/";

    protected void writeStringIntoConnectionBody(HttpURLConnection connection, String data) throws IOException {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.flush();
        }
    }
    protected String readResponseBody(HttpURLConnection connection) throws IOException {
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
    protected String readResponseError(HttpURLConnection connection) throws IOException {
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
    protected void logServerError
            (String handlerName, String handlerFunction, HttpURLConnection connection) throws IOException {
        String responseError = readResponseError(connection);

        StringBuilder errorBuilder = new StringBuilder();
        errorBuilder.append("Source: ");
        errorBuilder.append(handlerName);
        errorBuilder.append(" -> ");
        errorBuilder.append(handlerFunction);
        errorBuilder.append("\n");
        errorBuilder.append("Response code: ");
        errorBuilder.append(connection.getResponseCode());
        errorBuilder.append("\n");
        errorBuilder.append("Error info: ");
        errorBuilder.append(responseError);
        errorBuilder.append("\n");

        localStorage.setErrorMessage(errorBuilder.toString(), responseError);
    }

    protected HttpURLConnection makeGetQuery(String targetUrl) throws IOException {
        URL url = new URL(domain + targetUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }
    protected HttpURLConnection makePutQuery(String targetUrl, String body) throws IOException {
        URL url = new URL(domain + targetUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        writeStringIntoConnectionBody(connection, body);
        return connection;
    }
    protected HttpURLConnection makePostQuery(String targetUrl, String body) throws IOException {
        URL url = new URL(domain + targetUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        writeStringIntoConnectionBody(connection, body);
        return connection;
    }
}
