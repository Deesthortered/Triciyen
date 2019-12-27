package com.triciyen.service;

import com.triciyen.entity.Conversation;
import com.triciyen.entity.UserConversation;

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
    public Conversation createConversation(String name) {
        String userLogin = localStorage.getLoggedAccount().getLogin();
        Optional<Conversation> newConversationEnvelop = Optional.empty();
        try {
            newConversationEnvelop = conversationQueryHandler.createConversationQuery(name, userLogin);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with creating new conversation");
        }

        if (newConversationEnvelop.isEmpty()) {
            localStorage.setErrorMessage(
                    "Loaded empty Optional of new conversation",
                    "Some troubles with creating new conversation");
            return Conversation.builder().build();
        }
        return newConversationEnvelop.get();
    }
    public Boolean deleteConversation(Integer conversationId) {
        Optional<Boolean> deleteConversationResultEnvelop = Optional.empty();
        try {
            deleteConversationResultEnvelop = conversationQueryHandler.deleteConversationQuery(conversationId);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with deleting the conversation");
        }

        if (deleteConversationResultEnvelop.isEmpty()) {
            localStorage.setErrorMessage(
                    "Loaded empty Optional of result of deleting the conversation",
                    "Some troubles with deleting the conversation");
            return false;
        }
        return deleteConversationResultEnvelop.get();
    }
    public UserConversation addUserToConversation(Integer conversationId, String userLogin) {
        Optional<UserConversation> newUserConversationResultEnvelop = Optional.empty();
        try {
            newUserConversationResultEnvelop = conversationQueryHandler.addUserToConversationQuery(conversationId, userLogin);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with adding new user to the conversation");
        }

        if (newUserConversationResultEnvelop.isEmpty()) {
            localStorage.setErrorMessage(
                    "Loaded empty Optional of result of adding new user to the conversation",
                    "Some troubles with adding new user to the conversation");
            return UserConversation.builder().build();
        }
        return newUserConversationResultEnvelop.get();
    }
    public Boolean deleteUserFromConversation(Integer conversationId, String userLogin) {
        Optional<Boolean> deleteUserFromConversationResultEnvelop = Optional.empty();
        try {
            deleteUserFromConversationResultEnvelop = conversationQueryHandler
                    .deleteUserFromConversationQuery(conversationId, userLogin);
        } catch (IOException e) {
            localStorage.setErrorMessage(e.getMessage(), "Some troubles with deleting user from the conversation");
        }

        if (deleteUserFromConversationResultEnvelop.isEmpty()) {
            localStorage.setErrorMessage(
                    "Loaded empty Optional of result of deleting user from the conversation",
                    "Some troubles with deleting user from the conversation");
            return false;
        }
        return deleteUserFromConversationResultEnvelop.get();
    }
}
