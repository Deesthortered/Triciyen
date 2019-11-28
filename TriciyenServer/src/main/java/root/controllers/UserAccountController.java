package root.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import root.entity.UserAccount;
import root.repository.UserAccountRepository;

@Controller
@RequestMapping(path="/demo")
public class UserAccountController {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUserAccount (
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String email) {

        UserAccount userAccount = new UserAccount(login, password, name, email);
        userAccountRepository.save(userAccount);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<UserAccount> getAllUsers() {
        return userAccountRepository.findAll();
    }
}