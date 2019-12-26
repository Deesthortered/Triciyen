package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import root.entity.Conversation;
import root.entity.Message;
import root.entity.UserAccount;
import root.entity.UserConversation;
import root.exception.NotFoundException;
import root.repository.ConversationRepository;
import root.repository.UserAccountRepository;
import root.repository.UserConversationRepository;
import root.service.ConversationService;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserConversationRepository userConversationRepository;
    @Autowired
    private ConversationRepository conversationRepository;

    @Override
    public List<Conversation> getAllConversationsByUser(String login) {
        UserAccount userAccount = userAccountRepository
                .findById(login)
                .orElseThrow(() -> new NotFoundException("User is not found"));

        List<UserConversation> userConversations = userConversationRepository.findAllByUser(userAccount);

        List<Conversation> conversations = new ArrayList<>(userConversations.size());
        userConversations.forEach( item -> conversations.add(item.getConversation()));

        return conversations;
    }

    @Override
    public Conversation createConversation(String name, String userCreatorLogin) {
        UserAccount userAccount = userAccountRepository
                .findById(userCreatorLogin)
                .orElseThrow(() -> new NotFoundException("User is not found"));

        Conversation newConversation = Conversation
                .builder()
                .name(name)
                .build();
        newConversation = conversationRepository.save(newConversation);

        UserConversation userConversation = UserConversation
                .builder()
                .user(userAccount)
                .lastReadMessageId(-1)
                .conversation(newConversation)
                .build();

        userConversationRepository.save(userConversation);

        return newConversation;
    }

    @Override
    public Boolean deleteConversation(Integer conversationId) {
        userConversationRepository.deleteAllByConversation_ConversationId(conversationId);
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("The conversation is not found"));
        conversationRepository.delete(conversation);
        return true;
    }

    @Override
    public List<Conversation> findConversationByName(String name) {
        return conversationRepository.findAllByName(name);
    }

    @Override
    public Conversation findConversationById(Integer conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("The conversation is not found"));
    }
}
