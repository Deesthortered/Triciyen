package root.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.UserConversation;

@Repository
public interface UserConversationRepository extends CrudRepository<UserConversation, Integer> {
}
