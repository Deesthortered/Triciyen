package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import root.entity.*;
import root.exception.MultipleChangesException;
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
                .getAllByConversation_ConversationIdAndCreationTimeGreaterThanOrderByCreationTime(
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
                .getAllByConversation_ConversationIdAndCreationTimeLessThanEqualOrderByCreationTimeDesc(
                        conversationId,
                        lastReadMessageDateTime,
                        PageRequest.of(0, pageSize)
                );
    }

    @Override
    public Boolean setLastReadMessageOfTheConversation(Integer conversationId, String userLogin, Integer messageId) {
        int countOfChangedRows = userConversationRepository
                .updateLastReadOfTheConversation(conversationId, userLogin, messageId);
        if (countOfChangedRows == 0)
            throw new NotFoundException("The user conversation wasn't found by ConvID and UsLogin");
        if (countOfChangedRows > 1)
            throw new MultipleChangesException("A lot of rows were changed by setLastReadMessageQuery");
        return true;
    }

    @Override
    public Message sendMessage(Integer conversationId, String userLogin, Integer contentTypeId, String content) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("Conversation is not found"));
        UserAccount userAccount = userAccountRepository.findById(userLogin)
                .orElseThrow(() -> new NotFoundException("UserAccount is not found"));
        MessageContentType contentType = messageContentTypeRepository.findById(contentTypeId)
                .orElseThrow(() -> new NotFoundException("Content type is not found"));

        Message message = Message.builder()
                .conversation(conversation)
                .user(userAccount)
                .creationTime(LocalDateTime.now())
                .contentTypeId(contentType.getMessageContentTypeId())
                .content(content)
                .build();

        return messageRepository.save(message);
    }


    @Override
    public Message getLastMessageInConversation(Integer conversationId) {
        MessageContentType notificationType = messageContentTypeRepository.findById(2)
                .orElseThrow(() -> new NotFoundException("Notification content type is not found"));

        Optional<Message> message = messageRepository
                .findTopByConversation_ConversationIdOrderByCreationTimeDesc(conversationId);
        return message.orElse(Message
                .builder()
                .user(UserAccount.builder().name("System").build())
                .content("It is empty conversation")
                .contentTypeId(notificationType.getMessageContentTypeId())
                .build());
    }

    @Override
    public Integer getCountOfUnreadMessagesForUser(Integer conversationId, String userLogin) {
        Integer messageId = getLastReadMessageIdOfConversation(conversationId, userLogin);
        if (messageId == -1) {
            return messageRepository.countOfAllMessagesInTheConversation(conversationId);
        } else {
            LocalDateTime dateTime = messageRepository.findById(messageId)
                    .orElseThrow(() -> new NotFoundException("Message is not found"))
                    .getCreationTime();
            return messageRepository.countOfMessagesInTheConversationAfterCurrentDate(conversationId, dateTime);
        }
    }
}
