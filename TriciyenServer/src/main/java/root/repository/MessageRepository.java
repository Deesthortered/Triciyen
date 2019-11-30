package root.repository;

import org.springframework.data.repository.CrudRepository;
import root.entity.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
