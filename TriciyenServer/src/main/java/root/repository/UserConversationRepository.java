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
    List<UserConversation> findAllByUser(UserAccount user);
    Optional<UserConversation> findByConversationAndUser(Conversation conversation, UserAccount user);
}
