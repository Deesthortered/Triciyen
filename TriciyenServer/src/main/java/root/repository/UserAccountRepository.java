package root.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.UserAccount;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, String> {
    Integer countAllByLoginAndPassword(String login, String password);
}