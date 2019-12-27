package com.triciyen.query.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.triciyen.entity.Conversation;
import com.triciyen.entity.UserAccount;
import com.triciyen.entity.UserConversation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Optional;

public class ConversationQueryHandler extends BaseQueryHandler {
    private static final ConversationQueryHandler instance = new ConversationQueryHandler();

    private ConversationQueryHandler() {

    }
    public static ConversationQueryHandler getInstance() {
        return instance;
    }

    public Optional<List<Conversation>> getAllSubscribedConversationsQuery() throws IOException {
        UserAccount account = localStorage.getLoggedAccount();
        HttpURLConnection connection = makeGetQuery(urlGetAllSubscribedConversations + account.getLogin());

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            List<Conversation> res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("ConversationQueryHandler",
                "getAllSubscribedConversationsQuery", connection);

        return Optional.empty();
    }
    public Optional<Conversation> createConversationQuery(String name, String userCreatorLogin) throws IOException {
        HttpURLConnection connection = makePostQuery(urlCreateConversation +
                "?name=" + name +
                "&userCreatorLogin=" + userCreatorLogin,
                "");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            Conversation res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("ConversationQueryHandler", "createConversationQuery", connection);
        return Optional.empty();
    }
    public Optional<Boolean> deleteConversationQuery(Integer conversationId) throws IOException {
        HttpURLConnection connection = makePostQuery(urlDeleteConversation +
                        "?conversationId=" + conversationId,
                "");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            Boolean res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("ConversationQueryHandler", "deleteConversationQuery", connection);
        return Optional.empty();
    }
    public Optional<List<Conversation>> findConversationByNameQuery(String name) throws IOException {
        HttpURLConnection connection = makeGetQuery(urlFindConversationByName +
                        "?name=" + name);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            List<Conversation> res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("ConversationQueryHandler", "findConversationByNameQuery", connection);
        return Optional.empty();
    }
    public Optional<Conversation> findConversationByIdQuery(Integer id) throws IOException {
        HttpURLConnection connection = makeGetQuery(urlFindConversationById +
                "?id=" + id);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            Conversation res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("ConversationQueryHandler", "findConversationByIdQuery", connection);
        return Optional.empty();
    }
    public Optional<UserConversation> addUserToConversationQuery(Integer conversationId, String userLogin) throws IOException {
        HttpURLConnection connection = makePostQuery(urlAddUserToConversation +
                        "?conversationId=" + conversationId +
                        "&userLogin=" + userLogin,
                "");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            UserConversation res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("ConversationQueryHandler", "addUserToConversationQuery", connection);
        return Optional.empty();
    }
    public Optional<Boolean> deleteUserFromConversationQuery(Integer conversationId, String userLogin) throws IOException {
        HttpURLConnection connection = makePostQuery(urlDeleteUserFromConversation +
                        "?conversationId=" + conversationId +
                        "&userLogin=" + userLogin,
                "");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            Boolean res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("ConversationQueryHandler", "deleteUserFromConversationQuery", connection);
        return Optional.empty();
    }
}
