package root.service;

import org.springframework.stereotype.Service;
import root.entity.Conversation;

import java.util.List;

@Service
public interface ConversationService {
    List<Conversation> getAllConversationsByUser(String login);
    Conversation createConversation(String name, String userCreatorLogin);
    Boolean deleteConversation(Integer conversationId);
    List<Conversation> findConversationByName(String name);
    Conversation findConversationById(Integer conversationId);
}
