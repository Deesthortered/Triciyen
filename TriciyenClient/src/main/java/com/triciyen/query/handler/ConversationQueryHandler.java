package com.triciyen.query.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.triciyen.entity.Conversation;
import com.triciyen.entity.UserAccount;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
        UserAccount account = stateService.getLoggedAccount();
        URL url = new URL(domain + urlGetAllSubscribedConversations + account.getLogin());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            List<Conversation> res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("ConversationQueryHandler", "getAllSubscribedConversations", connection);

        return Optional.empty();
    }
}
