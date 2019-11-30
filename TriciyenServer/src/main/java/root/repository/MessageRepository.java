package root.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    Message getTopByConversation_ConversationIdOrderByCreationTimeDesc(Integer conversationId);
    List<Message> getAllByConversation_ConversationIdOrderByCreationTimeDesc(Integer conversationId, Pageable pageable);
}
