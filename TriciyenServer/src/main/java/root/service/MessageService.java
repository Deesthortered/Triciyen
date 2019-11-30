package root.service;

import org.springframework.stereotype.Service;
import root.entity.Message;

import java.util.List;

@Service
public interface MessageService {
    Message getLastMessageInConversation(Integer conversationId);
    List<Message> getSetOfLastMessagesInConversation(Integer conversationId, Integer count);
}
