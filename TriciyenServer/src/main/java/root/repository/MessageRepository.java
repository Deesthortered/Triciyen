package root.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
}
