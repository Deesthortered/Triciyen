package root.repository;

import org.springframework.data.repository.CrudRepository;
import root.entity.MessageContentType;

public interface MessageContentTypeRepository extends CrudRepository<MessageContentType, Integer> {
}
