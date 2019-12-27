package root.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import root.entity.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    // Для того, что бы получить самое старое сообщение беседы, когда нет последнего прочитного
    Optional<Message> findTopByConversation_ConversationIdOrderByCreationTime(Integer conversationId);

    // Получаем просто самое новое сообщение беседы (последнее) для вывода сбоку на кнопке
    Optional<Message> findTopByConversation_ConversationIdOrderByCreationTimeDesc(Integer conversationId);

    // Загрузка всех сообщений беседы, которые позже (строгое неравенство) указаной даты.
    // Нужно для того, что бы загрузить все сообщения после последнего прочитаного сообщения
    List<Message> getAllByConversation_ConversationIdAndCreationTimeGreaterThanOrderByCreationTime
            (Integer conversationId, LocalDateTime dateTime);

    // Загрузка страницы сообщений беседы, которые раньше (нестрогое неравенство) указаной даты.
    // Нужно для того, что бы загрузить страницу сообщений перед последним прочитаным
    List<Message> getAllByConversation_ConversationIdAndCreationTimeLessThanEqualOrderByCreationTimeDesc
            (Integer conversationId, LocalDateTime dateTime, Pageable pageable);

    // Берем количество всех тех сообщений беседы, которые позже указаной
    @Query("SELECT count(m) FROM Message m WHERE m.conversation.conversationId = :conversationId and m.creationTime > :dateTime")
    Integer countOfMessagesInTheConversationAfterCurrentDate(
            @Param("conversationId") Integer conversationId,
            @Param("dateTime") LocalDateTime dateTime
    );

    // Берем количество вообще всех сообщений в беседе
    @Query("SELECT count(m) FROM Message m WHERE m.conversation.conversationId = :conversationId")
    Integer countOfAllMessagesInTheConversation(
            @Param("conversationId") Integer conversationId
    );

    void deleteAllByConversation_ConversationId(Integer conversationId);
}
