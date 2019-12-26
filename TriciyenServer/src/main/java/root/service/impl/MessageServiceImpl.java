package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import root.entity.*;
import root.exception.NotFoundException;
import root.repository.*;
import root.service.MessageService;

import java.time.LocalDateTime;
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
    public List<Message> getLastMessagesInConversation(Integer conversationId, Integer lastReadMessageId) {
        LocalDateTime lastReadMessageDateTime;
        if (lastReadMessageId == -1) {
            Optional<Message> lastReadMessage = messageRepository
                    .findTopByConversation_ConversationIdOrderByCreationTime(conversationId);
            if (lastReadMessage.isPresent()) {
                lastReadMessageDateTime = lastReadMessage.get().getCreationTime();
            } else {
                lastReadMessageDateTime = LocalDateTime.now();
            }
        } else {
            Message lastReadMessage = messageRepository.findById(lastReadMessageId)
                    .orElseThrow(() -> new NotFoundException("Last read message is not found"));
            lastReadMessageDateTime = lastReadMessage.getCreationTime();
        }

        return messageRepository
                .getAllByConversation_ConversationIdAndCreationTimeGreaterThanEqualOrderByCreationTime(
                        conversationId,
                        lastReadMessageDateTime
                );
    }

    @Override
    public List<Message> getPageOfElderMessagesInConversation
            (Integer conversationId, Integer lastReadMessageId, Integer pageSize) {
        LocalDateTime lastReadMessageDateTime;
        if (lastReadMessageId == -1) {
            Optional<Message> lastReadMessage = messageRepository
                    .findTopByConversation_ConversationIdOrderByCreationTime(conversationId);
            if (lastReadMessage.isPresent()) {
                lastReadMessageDateTime = lastReadMessage.get().getCreationTime();
            } else {
                lastReadMessageDateTime = LocalDateTime.now();
            }
        } else {
            Message lastReadMessage = messageRepository.findById(lastReadMessageId)
                    .orElseThrow(() -> new NotFoundException("Last read message is not found"));
            lastReadMessageDateTime = lastReadMessage.getCreationTime();
        }

        return messageRepository
                .getAllByConversation_ConversationIdAndCreationTimeLessThanOrderByCreationTime(
                        conversationId,
                        lastReadMessageDateTime,
                        PageRequest.of(0, pageSize)
                );
    }
}
