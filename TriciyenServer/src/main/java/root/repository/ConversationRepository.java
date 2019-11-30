package root.repository;

import org.springframework.data.repository.CrudRepository;
import root.entity.Conversation;

public interface ConversationRepository extends CrudRepository<Conversation, Integer> {
}
