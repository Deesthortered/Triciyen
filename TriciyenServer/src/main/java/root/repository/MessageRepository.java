package root.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.Message;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> getAllByConversation_ConversationIdAndCreationTimeAfterOrderByCreationTime(
            Integer conversationId, LocalDateTime dateTime);
}
