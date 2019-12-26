package com.triciyen.query.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.triciyen.entity.Message;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
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

    public Optional<List<Message>> getLastMessagesOfConversationQuery
            (Integer conversationId, Integer lastReadMessageId) throws IOException {
        HttpURLConnection connection = makeGetQuery
                (urlGetLastMessagesOfConversation + conversationId + "?lastReadMessageId=" + lastReadMessageId);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            List<Message> res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("MessageQueryHandler", "getLastMessagesOfConversationQuery", connection);
        return Optional.empty();
    }

    public Optional<List<Message>> getPageOfElderMessagesOfConversationQuery
            (Integer conversationId, Integer lastReadMessageId, Integer pageSize) throws IOException {
        HttpURLConnection connection = makeGetQuery
                (urlGetPageOfElderMessagesOfConversation + conversationId +
                        "?lastReadMessageId=" + lastReadMessageId +
                        "&pageSize=" + pageSize
                );

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            List<Message> res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("MessageQueryHandler", "getPageOfElderMessagesOfConversationQuery", connection);
        return Optional.empty();
    }

    public Optional<Boolean> setLastReadMessageOfTheConversationQuery
            (Integer conversationId, String userLogin, Integer lastReadMessageId) throws IOException {
        HttpURLConnection connection = makePutQuery
                (urlSetLastReadMessageOfConversation + conversationId +
                        "?userLogin=" + userLogin +
                        "&lastReadMessageId=" + lastReadMessageId,
                ""
                );

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            Boolean res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("MessageQueryHandler", "setLastReadMessageOfTheConversationQuery", connection);
        return Optional.empty();
    }

    public Optional<Message> sendMessageQuery
            (Integer conversationId, String userLogin, Integer contentTypeId, String content) throws IOException {
        HttpURLConnection connection = makePostQuery
                (urlSendMessage +
                                "?conversationId="+ conversationId +
                                "&userLogin=" + userLogin +
                                "&contentTypeId=" + contentTypeId +
                                "&content=" + URLEncoder.encode(content, StandardCharsets.UTF_8.toString()),
                        ""
                );

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            Message res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("MessageQueryHandler", "sendMessageQuery", connection);
        return Optional.empty();
    }

    public Optional<Message> getLastMessageOfTheConversationQuery(Integer conversationId) throws IOException {
        HttpURLConnection connection = makeGetQuery(urlGetLastMessage + conversationId);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            Message res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("MessageQueryHandler", "getLastMessageOfTheConversationQuery", connection);
        return Optional.empty();
    }
}
