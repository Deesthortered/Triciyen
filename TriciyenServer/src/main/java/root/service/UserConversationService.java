package root.service;

import org.springframework.stereotype.Service;
import root.entity.UserConversation;

@Service
public interface UserConversationService {
    UserConversation addUserToConversation(Integer conversationId, String userLogin);
    Boolean deleteUserFromConversation(Integer conversationId, String userLogin);
}
