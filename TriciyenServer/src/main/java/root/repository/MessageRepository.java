package root.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.Message;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    Message getTopByConversation_ConversationIdOrderByCreationTimeDesc(Integer conversationId);

    List<Message> getAllByConversation_ConversationIdAndCreationTimeBeforeOrderByCreationTimeDesc(Integer conversationId, LocalDateTime dateTime, Pageable pageable);
    List<Message> getAllByConversation_ConversationIdAndCreationTimeAfterOrderByCreationTimeDesc(Integer conversationId, LocalDateTime dateTime);
    Message getTopByMessageId(Integer lastMessageId);
}
