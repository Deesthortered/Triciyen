package com.triciyen.service;

import com.triciyen.entity.Conversation;
import com.triciyen.entity.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageService implements BaseService {
    private static final MessageService instance = new MessageService();
    private static final int messagePageSize = 17;

    private MessageService() {

    }
    public static MessageService getInstance() {
        return instance;
    }

    public Message getLastMessageOfConversation(Conversation conversation) {
        Optional<Message> packedMessage = Optional.empty();
        try {
            packedMessage = messageQueryHandler.getLastMessageOfConversationQuery(conversation);
        } catch (IOException e) {
            localStorage.setServerErrorMessage(e.getMessage());
        }

        if (packedMessage.isPresent()) {
            return packedMessage.get();
        }

        if (localStorage.isWasError()) {
            return Message.builder().content("Was error: " + localStorage.getServerErrorMessage()).build();
        }

        return Message.builder().content("No messages").build();
    }
    public List<Message> getMessagesOfConversationWithPagination(Conversation conversation, Integer lastPaginedMessage, int page) {
        Optional<List<Message>> result = Optional.empty();
        try {
            result = messageQueryHandler.getMessagesOfConversationWithPaginationQuery(
                    conversation.getConversationId(),
                    lastPaginedMessage,
                    page,
                    messagePageSize);
        } catch (IOException e) {
            localStorage.setServerErrorMessage(e.getMessage());
        }

        return result.orElseGet(ArrayList::new);
    }
    public Boolean sendMessage(String content, Integer contentTypeId, String authorUserLogin, Integer conversationId) {
        try {
            return messageQueryHandler.sendMessageQuery(content, contentTypeId, authorUserLogin, conversationId);
        } catch (IOException e) {
            localStorage.setServerErrorMessage(e.getMessage());
        }
        return false;
    }
    public List<Message> getLastNewestMessagesOfConversation(Integer conversationId, Integer lastMessageId) {
        Optional<List<Message>> result = Optional.empty();
        try {
            result = messageQueryHandler.getLastNewestMessagesOfConversationQuery(
                    conversationId,
                    lastMessageId
            );
        } catch (IOException e) {
            localStorage.setServerErrorMessage(e.getMessage());
        }

        return result.orElseGet(ArrayList::new);
    }
}
