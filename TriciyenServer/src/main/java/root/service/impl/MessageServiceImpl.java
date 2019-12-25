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
                .findByConversationAndUser(
                        Conversation.builder().conversationId(conversationId).build(),
                        UserAccount.builder().login(userLogin).build()
                );
        UserConversation userConversation = userConversationEnvelop
                .orElseThrow(() -> new NotFoundException("UserConversation is not found by id"));
        return userConversation.getLastReadMessageId() == null ? -1 : userConversation.getLastReadMessageId();
    }






    @Override
    public Message getLastMessageInConversation(Integer conversationId) {
        return messageRepository
                .getTopByConversation_ConversationIdOrderByCreationTimeDesc(conversationId);
    }

    @Override
    public List<Message> getSetOfLastMessagesInConversation(Integer conversationId, Integer lastPageableId, Integer page, Integer pageSize) {
        return messageRepository
                .getAllByConversation_ConversationIdAndCreationTimeBeforeOrderByCreationTimeDesc(
                        conversationId,
                        messageRepository.getTopByMessageId(lastPageableId).getCreationTime(),
                        PageRequest.of(page, pageSize)
                );
    }

    @Override
    public void sendMessage(String content, Integer contentTypeId, String authorUserLogin, Integer conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("Conversation is not found"));

        UserAccount userAccount = userAccountRepository.findById(authorUserLogin)
                .orElseThrow(() -> new NotFoundException("UserAccount is not found"));

        MessageContentType contentType = messageContentTypeRepository.findById(contentTypeId)
                .orElseThrow(() -> new NotFoundException("ContentType is not found"));

        Message preparedMessage = Message.builder()
                .conversation(conversation)
                .user(userAccount)
                .creationTime(LocalDateTime.now())
                .contentType(contentType)
                .content(content)
                .build();

        messageRepository.save(preparedMessage);
    }

    @Override
    public List<Message> getLastMessages(Integer conversationId, Integer lastMessageId) {
        return messageRepository
                .getAllByConversation_ConversationIdAndCreationTimeAfterOrderByCreationTime(
                        conversationId,
                        messageRepository.getTopByMessageId(lastMessageId).getCreationTime()
                );
    }
}
