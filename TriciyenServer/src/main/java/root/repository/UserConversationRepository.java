package root.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.Conversation;
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
}
