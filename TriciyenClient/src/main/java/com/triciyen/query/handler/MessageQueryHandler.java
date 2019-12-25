package com.triciyen.query.handler;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Optional;

public class MessageQueryHandler extends BaseQueryHandler {
    private static final MessageQueryHandler instance = new MessageQueryHandler();

    private MessageQueryHandler() {

    }
    public static MessageQueryHandler getInstance() {
        return instance;
    }

    public Optional<Integer> getLastReadMessageIdOfConversationQuery
            (Integer conversationId, String userLogin) throws IOException {
        HttpURLConnection connection = makeGetQuery
                (urlGetLastReadMessageIdOfConversation + conversationId + "?userLogin=" + userLogin);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            Integer res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("MessageQueryHandler", "getLastReadMessageIdOfConversation", connection);
        return Optional.empty();
    }

}
