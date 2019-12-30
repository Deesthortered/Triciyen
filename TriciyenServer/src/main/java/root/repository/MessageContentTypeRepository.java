package root.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.MessageContentType;

@Repository
public interface MessageContentTypeRepository extends CrudRepository<MessageContentType, Integer> {
}
