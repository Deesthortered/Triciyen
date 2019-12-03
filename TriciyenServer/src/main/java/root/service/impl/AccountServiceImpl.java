package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import root.entity.UserAccount;
import root.exception.AlreadyExistsException;
import root.exception.InvalidCredentialsException;
import root.exception.NotFoundException;
import root.repository.UserAccountRepository;
import root.service.AccountService;

@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserAccount authenticate(String login, String hashedPassword) {
        UserAccount userAccount = userAccountRepository
                .findById(login)
                .orElseThrow(() -> new NotFoundException("User is not found."));
        if (userAccount.getPassword().equals(hashedPassword)) {
            return userAccount;
        } else {
            throw new InvalidCredentialsException("Password is incorrect.");
        }
    }

    @Override
    public UserAccount registration(UserAccount newUserAccount) {
        var alreadyInBase = userAccountRepository.findById(newUserAccount.getLogin());
        if (alreadyInBase.isPresent())
            throw new AlreadyExistsException("User login is already used in DB");

        userAccountRepository.save(newUserAccount);
        return userAccountRepository
                .findById(newUserAccount.getLogin())
                .orElseThrow(() -> new NotFoundException("User is not found after saving."));
    }
}
