package root.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import root.entity.UserAccount;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, String> {
    // Получаем число = сколько юзеров с указаным логином и паролем. Либо 0, либо 1.
    // Нужно для проверки правильности данных для входа
    Integer countAllByLoginAndPassword(String login, String password);
}