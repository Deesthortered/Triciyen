package root.service;

import org.springframework.stereotype.Service;
import root.entity.Message;
import root.entity.UserAccount;

import java.util.List;

@Service
public interface MessageService {
    Integer getLastReadMessageIdOfConversation(Integer conversationId,  String userLogin);



    Message getLastMessageInConversation(Integer conversationId);
    void sendMessage(String content, Integer contentTypeId, String authorUserLogin, Integer conversationId);
    List<Message> getSetOfLastMessagesInConversation(Integer conversationId, Integer lastPageableId, Integer page, Integer pageSize);
    List<Message> getLastMessages(Integer conversationId, Integer lastMessageId);
}
