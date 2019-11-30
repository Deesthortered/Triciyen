package root.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.entity.Conversation;
import root.entity.UserAccount;
import root.entity.auxiliary.AuthData;
import root.repository.UserAccountRepository;
import root.service.AccountService;
import root.service.ConversationService;

import java.util.List;

@RestController
@RequestMapping({"http_api"})
public class HttpQueryController {

    @Autowired
    private UserAccountRepository repository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ConversationService conversationService;

    @GetMapping("/get/{login}")
    public ResponseEntity<?> get(@PathVariable String login) {
        UserAccount user = repository.findById(login)
                .orElse(UserAccount
                        .builder()
                        .login("Not found")
                        .build());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody AuthData authData) {
        UserAccount user = accountService.authenticate(authData.getLogin(), authData.getEncryptedPassword());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/getConversations/{login}")
    public ResponseEntity<?> getUserConversations(@PathVariable String login) {
        List<Conversation> res = conversationService.getAllConversationsByUser(login);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
