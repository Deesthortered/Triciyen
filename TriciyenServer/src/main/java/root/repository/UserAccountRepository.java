package root.repository;

import org.springframework.data.repository.CrudRepository;
import root.entity.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, String> {
}