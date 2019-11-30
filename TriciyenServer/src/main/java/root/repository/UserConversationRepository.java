package root.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.UserAccount;
import root.entity.UserConversation;

import java.util.List;

@Repository
public interface UserConversationRepository extends CrudRepository<UserConversation, Integer> {
    List<UserConversation> findAllByUser(UserAccount user);
}
