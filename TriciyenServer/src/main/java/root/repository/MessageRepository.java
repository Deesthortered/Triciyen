package root.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    // Для того, что бы получить самое старое сообщение беседы, когда нет последнего прочитного
    Optional<Message> findTopByConversation_ConversationIdOrderByCreationTime(Integer conversationId);

    // Загрузка всех сообщений беседы, которые позже указаной даты.
    // Нужна для того, что бы загрузить все сообщения после последнего прочитаного сообщения
    List<Message> getAllByConversation_ConversationIdAndCreationTimeGreaterThanEqualOrderByCreationTime(
            Integer conversationId, LocalDateTime dateTime);
}
