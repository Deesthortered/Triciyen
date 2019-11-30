package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import root.entity.Conversation;
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
    ConversationRepository conversationRepository;

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
}
