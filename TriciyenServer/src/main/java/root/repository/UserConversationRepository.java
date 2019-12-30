package root.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import root.entity.UserAccount;
import root.entity.UserConversation;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserConversationRepository extends CrudRepository<UserConversation, Integer> {
    // Загрузить все беседы юзера для списка слева
    List<UserConversation> findAllByUser(UserAccount user);

    // Загружаем конкретную беседу по ID и Логину, что бы с нее выцепить ID последнего прочитаного сообщения
    Optional<UserConversation> findByConversation_ConversationIdAndUser_Login(Integer conversationId, String userLogin);

    //Задаем последнее прочитаное сообщение (его ID) для конкретного юзера для конкретной беседы
    @Modifying
    @Query("UPDATE UserConversation uc SET uc.lastReadMessageId = :messageId " +
            "WHERE uc.conversation.conversationId = :conversationId and uc.user.login = :userLogin")
    int updateLastReadOfTheConversation(
            @Param("conversationId") Integer conversationId,
            @Param("userLogin") String userLogin,
            @Param("messageId") Integer messageId
    );

    void deleteAllByConversation_ConversationId(Integer conversationId);
}
