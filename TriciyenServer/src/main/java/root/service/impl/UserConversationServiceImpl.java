package root.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import root.entity.Conversation;
import root.entity.UserAccount;
import root.entity.UserConversation;
import root.exception.AlreadyExistsException;
import root.exception.NotFoundException;
import root.repository.ConversationRepository;
import root.repository.UserAccountRepository;
import root.repository.UserConversationRepository;
import root.service.UserConversationService;

import java.util.Optional;

@Transactional
public class UserConversationServiceImpl implements UserConversationService {
    @Autowired
    private UserConversationRepository userConversationRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserConversation addUserToConversation(Integer conversationId, String userLogin) {
        Optional<UserConversation> userConversationEnvelop = userConversationRepository
                .findByConversation_ConversationIdAndUser_Login(conversationId, userLogin);
        if (userConversationEnvelop.isPresent()) {
            throw new AlreadyExistsException("User is already in the conversation");
        }

        UserAccount userAccount = userAccountRepository.findById(userLogin)
                .orElseThrow(() -> new NotFoundException("User is not found"));

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("Conversation is not found"));

        UserConversation userConversation = UserConversation
                .builder()
                .user(userAccount)
                .conversation(conversation)
                .lastReadMessageId(-1)
                .build();

        userConversation = userConversationRepository.save(userConversation);
        return userConversation;
    }

    @Override
    public Boolean deleteUserFromConversation(Integer conversationId, String userLogin) {
        UserConversation userConversation = userConversationRepository
                .findByConversation_ConversationIdAndUser_Login(conversationId, userLogin)
                .orElseThrow(() -> new NotFoundException("UserConversation is not found"));
        userConversationRepository.delete(userConversation);
        return true;
    }
}
