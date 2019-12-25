package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import root.entity.*;
import root.exception.NotFoundException;
import root.repository.*;
import root.service.MessageService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private MessageContentTypeRepository messageContentTypeRepository;
    @Autowired
    private UserConversationRepository userConversationRepository;

    @Override
    public Integer getLastReadMessageIdOfConversation(Integer conversationId, String userLogin) {
        Optional<UserConversation> userConversationEnvelop = userConversationRepository
                .findByConversation_ConversationIdAndUser_Login(conversationId, userLogin);
        UserConversation userConversation = userConversationEnvelop
                .orElseThrow(() -> new NotFoundException("UserConversation is not found by id"));
        return userConversation.getLastReadMessageId() == null ? -1 : userConversation.getLastReadMessageId();
    }

    @Override
    public List<Message> getLastMessagesInConversation
            (Integer conversationId, Integer lastReadMessageId) {
        Message lastReadMessage = messageRepository.findById(lastReadMessageId)
                .orElseThrow(() -> new NotFoundException("Last read message is not found"));
        return messageRepository
                .getAllByConversation_ConversationIdAndCreationTimeAfterOrderByCreationTime(
                        conversationId,
                        lastReadMessage.getCreationTime()
                );
    }
}
