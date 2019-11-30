package root.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.Conversation;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Integer> {
}
