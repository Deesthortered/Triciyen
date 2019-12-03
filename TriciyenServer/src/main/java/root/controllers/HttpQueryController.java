package root.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.entity.Conversation;
import root.entity.Message;
import root.entity.UserAccount;
import root.entity.auxiliary.AuthData;
import root.service.AccountService;
import root.service.ConversationService;
import root.service.MessageService;

import java.util.List;

@RestController
@RequestMapping({"http_api"})
public class HttpQueryController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ConversationService conversationService;
    @Autowired
    private MessageService messageService;


    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody AuthData authData) {
        UserAccount user = accountService.authenticate(authData.getLogin(), authData.getEncryptedPassword());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserAccount newUserAccount) {
        UserAccount givenUserAccount = accountService.registration(newUserAccount);
        return ResponseEntity.status(HttpStatus.OK).body(givenUserAccount);
    }

    @GetMapping("/getConversations/{login}")
    public ResponseEntity<?> getUserConversations(@PathVariable String login) {
        List<Conversation> conversationList = conversationService.getAllConversationsByUser(login);
        return ResponseEntity.status(HttpStatus.OK).body(conversationList);
    }

    @GetMapping("/getLastMessage/{conversationId}")
    public ResponseEntity<?> getLastMessageInTheConversation(@PathVariable Integer conversationId) {
        Message lastMessage = messageService.getLastMessageInConversation(conversationId);
        return ResponseEntity.status(HttpStatus.OK).body(lastMessage);
    }

    @GetMapping("/getListOfLastMessages/{conversationId}")
    public ResponseEntity<?> getListOfLastMessagesInTheConversation(
            @PathVariable Integer conversationId,
            @RequestParam(value = "messageCount", defaultValue = "1") Integer messageCount
    ) {
        List<Message> lastMessages = messageService.getSetOfLastMessagesInConversation(conversationId, messageCount);
        return ResponseEntity.status(HttpStatus.OK).body(lastMessages);
    }
}
