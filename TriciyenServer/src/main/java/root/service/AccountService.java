package root.service;

import org.springframework.stereotype.Service;
import root.entity.UserAccount;

@Service
public interface AccountService {
    UserAccount authenticate(String login, String hashedPassword);
    UserAccount registration(UserAccount newUserAccount);
}
