package com.triciyen.query.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.triciyen.entity.Conversation;
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

    public Optional<Message> getLastMessageOfConversationQuery(Conversation conversation) throws IOException {
        HttpURLConnection connection = makeGetQuery(urlGetLastMessageOfConversation + conversation.getConversationId());

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            Message res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError("MessageQueryHandler", "getLastMessageOfConversation", connection);
        return Optional.empty();
    }
    public Optional<List<Message>> getMessagesOfConversationWithPaginationQuery(int conversationId, int lastPageableId, int page, int pageSize) throws IOException {
        HttpURLConnection connection = makeGetQuery(
                urlGetMessagesOfConversationWithPaginationQuery + conversationId +
                "?lastPageableId=" + lastPageableId +
                "&page=" + page +
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
    public Boolean sendMessageQuery(String content, Integer contentTypeId, String authorUserLogin, Integer conversationId) throws IOException {
        String parameterBuilder = "?content=" + URLEncoder.encode(content, StandardCharsets.UTF_8.toString()) +
                "&contentTypeId=" + contentTypeId +
                "&authorUserLogin=" + authorUserLogin +
                "&conversationId=" + conversationId;
        HttpURLConnection connection = makePostQuery(urlSentMessage + parameterBuilder, "");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            return "Message is sent.".equals(jsonResponse);
        }

        logServerError("MessageQueryHandler", "sendMessage", connection);

        return false;
    }
    public Optional<List<Message>> getLastNewestMessagesOfConversationQuery(Integer conversationId, Integer lastMessageId) throws IOException {
        HttpURLConnection connection = makeGetQuery(
                urlGetLastNewestMessages +
                        "?conversationId=" + conversationId +
                        "&lastMessageId=" + lastMessageId);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String jsonResponse = readResponseBody(connection);
            List<Message> res = jsonMapper.readValue(jsonResponse, new TypeReference<>(){});
            return Optional.of(res);
        }

        logServerError(
                "MessageQueryHandler",
                "getLastNewestMessagesOfConversationQuery",
                connection
        );

        return Optional.empty();
    }
}
