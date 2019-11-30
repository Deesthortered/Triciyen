package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import root.entity.Message;
import root.repository.MessageRepository;
import root.service.MessageService;

import java.util.List;

@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message getLastMessageInConversation(Integer conversationId) {
        return messageRepository
                .getTopByConversation_ConversationIdOrderByCreationTimeDesc(conversationId);
    }

    @Override
    public List<Message> getSetOfLastMessagesInConversation(Integer conversationId, Integer count) {
        return messageRepository
                .getAllByConversation_ConversationIdOrderByCreationTimeDesc(conversationId, PageRequest.of(0, count));
    }
}
