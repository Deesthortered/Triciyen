package com.triciyen.service;

import com.triciyen.entity.Conversation;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ConversationService implements BaseService {
    private static final ConversationService instance = new ConversationService();

    private ConversationService() {

    }
    public static ConversationService getInstance() {
        return instance;
    }

    public Optional<List<Conversation>> getAllSubscribedConversations() {
        Optional<List<Conversation>> conversationList = Optional.empty();
        try {
            conversationList = conversationQueryHandler.getAllSubscribedConversationsQuery();
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with loading conversations");
        }
        return conversationList;
    }
}
