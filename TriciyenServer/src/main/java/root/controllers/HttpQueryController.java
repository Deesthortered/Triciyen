package root.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.entity.UserAccount;
import root.repository.UserAccountRepository;

@RestController
@RequestMapping({"http_api"})
public class HttpQueryController {

    @Autowired
    private UserAccountRepository repository;

    @GetMapping("/get/{login}")
    public ResponseEntity<?> get(@PathVariable String login) {
        UserAccount user = repository.findById(login)
                .orElse(UserAccount
                        .builder()
                        .login("Not found")
                        .build());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
