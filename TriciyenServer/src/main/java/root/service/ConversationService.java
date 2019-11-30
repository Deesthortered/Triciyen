package root.service;

import org.springframework.stereotype.Service;
import root.entity.Conversation;
import root.entity.UserConversation;

import java.util.List;

@Service
public interface ConversationService {
    List<UserConversation> getAllConversationsByUser(String login);
}
