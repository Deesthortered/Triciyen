package com.triciyen.service;

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

    public Integer getLastReadMessageIdOfConversation(Integer conversationId) {
        Optional<Integer> lastReadMessageIdEnvelop = Optional.empty();
        try {
            String userLogin = localStorage.getLoggedAccount().getLogin();
            lastReadMessageIdEnvelop = messageQueryHandler.getLastReadMessageIdOfConversationQuery(conversationId, userLogin);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with loading last read message");
        }

        if (lastReadMessageIdEnvelop.isEmpty()) {
            localStorage.setErrorMessage(
                    "Loaded empty Optional of last read message of UserConversation",
                    "Some troubles with loading last read message");
            return -1;
        }
        return lastReadMessageIdEnvelop.get();
    }
    public List<Message> getLastMessagesOfConversation(Integer conversationId, Integer lastReadMessageId) {
        Optional<List<Message>> messageListEnvelop = Optional.empty();
        try {
            messageListEnvelop = messageQueryHandler
                    .getLastMessagesOfConversationQuery(conversationId, lastReadMessageId);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with loading last messages");
        }

        if (messageListEnvelop.isEmpty()) {
            localStorage.setErrorMessage(
                    "Loaded empty Optional of last messages of conversation",
                    "Some troubles with loading last messages");
            return new ArrayList<>();
        }
        return messageListEnvelop.get();
    }
}
