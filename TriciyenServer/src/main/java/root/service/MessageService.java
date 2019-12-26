package root.service;

import org.springframework.stereotype.Service;
import root.entity.Message;
import root.entity.MessageContentType;
import root.entity.UserAccount;

import java.util.List;

@Service
public interface MessageService {
    Integer getLastReadMessageIdOfConversation(Integer conversationId,  String userLogin);
    List<Message> getLastMessagesInConversation(Integer conversationId, Integer lastReadMessageId);
    List<Message> getPageOfElderMessagesInConversation
            (Integer conversationId, Integer lastReadMessageId, Integer pageSize);
    Boolean setLastReadMessageOfTheConversation(Integer conversationId,  String userLogin, Integer messageId);
    Message sendMessage(Integer conversationId, String userLogin, Integer contentTypeId, String content);
}
