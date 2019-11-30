package root.repository;

import org.springframework.data.repository.CrudRepository;
import root.entity.UserConversation;

public interface UserConversationRepository extends CrudRepository<UserConversation, Integer> {
}
