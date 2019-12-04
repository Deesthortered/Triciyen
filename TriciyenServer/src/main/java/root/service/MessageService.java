package root.service;

import org.springframework.stereotype.Service;
import root.entity.Message;
import root.entity.UserAccount;

import java.util.List;

@Service
public interface MessageService {
    Message getLastMessageInConversation(Integer conversationId);
    List<Message> getSetOfLastMessagesInConversation(Integer conversationId, Integer page, Integer pageSize);
    Boolean sendMessage(String content, Integer contentTypeId, String authorUserLogin, Integer conversationId);
}
