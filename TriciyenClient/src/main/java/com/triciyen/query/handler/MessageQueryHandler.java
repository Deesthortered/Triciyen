package com.triciyen.query.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.triciyen.entity.Conversation;
import com.triciyen.entity.Message;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Optional;

public class MessageQueryHandler extends BaseQueryHandler {
    private static final MessageQueryHandler instance = new MessageQueryHandler();

    private MessageQueryHandler() {

    }
    public static MessageQueryHandler getInstance() {
        return instance;
    }

    public Optional<Message> getLastMessageOfConversation(Conversation conversation) throws IOException {
        HttpURLConnection connection = makeGetQuery(urlGetLastMessageOfConversation + conversation.getConversationId());

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            Message res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("MessageQueryHandler", "getLastMessageOfConversation", connection);
        return Optional.empty();
    }
    public Optional<List<Message>> getMessagesOfConversationWithPaginationQuery(
            int conversationId,
            int page,
            int pageSize) throws IOException {

        HttpURLConnection connection = makeGetQuery(
                urlGetMessagesOfConversationWithPaginationQuery + conversationId +
                "?page=" + page +
                "&pageSize=" + pageSize);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            List<Message> res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError(
                "MessageQueryHandler",
                "getMessagesOfConversationWithPaginationQuery",
                connection
        );

        return Optional.empty();
    }
}
