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
            if (!localStorage.wasError()) {
                localStorage.setErrorMessage(
                        "Loaded empty Optional of last read message of UserConversation",
                        "Some troubles with loading last read message");
            }
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
            if (!localStorage.wasError()) {
                localStorage.setErrorMessage(
                        "Loaded empty Optional of last messages of conversation",
                        "Some troubles with loading last messages");
            }
            return new ArrayList<>();
        }
        return messageListEnvelop.get();
    }
    public List<Message> getPageOfElderMessagesOfConversation(Integer conversationId, Integer lastReadMessageId) {
        Optional<List<Message>> messageListEnvelop = Optional.empty();
        try {
            messageListEnvelop = messageQueryHandler
                    .getPageOfElderMessagesOfConversationQuery(conversationId, lastReadMessageId, messagePageSize);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with loading page of elder messages");
        }

        if (messageListEnvelop.isEmpty()) {
            if (!localStorage.wasError()) {
                localStorage.setErrorMessage(
                        "Loaded empty Optional of page of elder messages of conversation",
                        "Some troubles with loading page of elder messages");
            }
            return new ArrayList<>();
        }
        return messageListEnvelop.get();
    }
    public Boolean setLastReadMessageOfTheConversation(Integer conversationId, Integer lastReadMessageId) {
        String userLogin = localStorage.getLoggedAccount().getLogin();
        Optional<Boolean> setLastReadResult = Optional.empty();
        try {
            setLastReadResult = messageQueryHandler
                    .setLastReadMessageOfTheConversationQuery(conversationId, userLogin, lastReadMessageId);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with setting last read message of conv");
        }

        if (setLastReadResult.isEmpty()) {
            if (!localStorage.wasError()) {
                localStorage.setErrorMessage(
                        "Loaded empty Optional of setting last read message of conversation",
                        "Some troubles with setting last read message of conversation");
            }
            return false;
        }
        return setLastReadResult.get();
    }
    public Message sendMessage(Integer conversationId, Integer contentTypeId, String content) {
        String userLogin = localStorage.getLoggedAccount().getLogin();
        Optional<Message> setLastReadResult = Optional.empty();
        try {
            setLastReadResult = messageQueryHandler
                    .sendMessageQuery(conversationId, userLogin, contentTypeId, content);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with sending message");
        }

        if (setLastReadResult.isEmpty()) {
            if (!localStorage.wasError()) {
                localStorage.setErrorMessage(
                        "Loaded empty Optional of sending message",
                        "Some troubles with sending message");
            }
            return Message.builder().build();
        }
        return setLastReadResult.get();
    }
    public Message getLastMessage(Integer conversationId) {
        Optional<Message> lastMessageEnvelop = Optional.empty();
        try {
            lastMessageEnvelop = messageQueryHandler
                    .getLastMessageOfTheConversationQuery(conversationId);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with loading last message");
        }

        if (lastMessageEnvelop.isEmpty()) {
            if (!localStorage.wasError()) {
                localStorage.setErrorMessage(
                        "Loaded empty Optional of last message",
                        "Some troubles with loading last message");
            }
            return Message.builder().build();
        }
        return lastMessageEnvelop.get();
    }
    public Integer getCountOfUnreadMessages(Integer conversationId) {
        String userLogin = localStorage.getLoggedAccount().getLogin();
        Optional<Integer> countOfUnreadEnvelop = Optional.empty();
        try {
            countOfUnreadEnvelop = messageQueryHandler
                    .getCountOfUnreadMessagesQuery(conversationId, userLogin);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with loading count of last messages");
        }

        if (countOfUnreadEnvelop.isEmpty()) {
            if (!localStorage.wasError()) {
                localStorage.setErrorMessage(
                        "Loaded empty Optional of count of last messages",
                        "Some troubles with loading count of last messages");
            }
            return -1;
        }
        return countOfUnreadEnvelop.get();
    }
}
