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

    @GetMapping("/getLastReadMessageIdOfConversation/{conversationId}")
    public ResponseEntity<?> getLastReadMessageIdOfConversation(
            @PathVariable Integer conversationId,
            @RequestParam(value = "userLogin") String userLogin
    ) {
        Integer result = messageService.getLastReadMessageIdOfConversation(conversationId, userLogin);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/getListOfLastMessages/{conversationId}")
    public ResponseEntity<?> getListOfLastMessagesInTheConversation(
            @PathVariable Integer conversationId,
            @RequestParam(value = "lastReadMessageId") Integer lastReadMessageId
    ) {
        List<Message> lastMessages = messageService.
                getLastMessagesInConversation(conversationId, lastReadMessageId);
        return ResponseEntity.status(HttpStatus.OK).body(lastMessages);
    }

    @GetMapping("/getPageOfElderMessages/{conversationId}")
    public ResponseEntity<?> getPageOfElderMessagesInTheConversation(
            @PathVariable Integer conversationId,
            @RequestParam(value = "lastReadMessageId") Integer lastReadMessageId,
            @RequestParam(value = "pageSize") Integer pageSize
    ) {
        List<Message> lastMessages = messageService.
                getPageOfElderMessagesInConversation(conversationId, lastReadMessageId, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(lastMessages);
    }

    @PutMapping("/setLastReadMessage/{conversationId}")
    public ResponseEntity<?> setLastReadMessageOfTheConversation(
            @PathVariable Integer conversationId,
            @RequestParam(value = "userLogin") String userLogin,
            @RequestParam(value = "lastReadMessageId") Integer lastReadMessageId
    ) {
        Boolean result = messageService
                .setLastReadMessageOfTheConversation(conversationId, userLogin, lastReadMessageId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(
            @RequestParam(value = "conversationId") Integer conversationId,
            @RequestParam(value = "userLogin") String userLogin,
            @RequestParam(value = "contentTypeId") Integer contentTypeId,
            @RequestParam(value = "content") String content
    ) {
        Message result = messageService.sendMessage(conversationId, userLogin, contentTypeId, content);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @GetMapping("/getLastMessage/{conversationId}")
    public ResponseEntity<?> getLastMessage(
            @PathVariable Integer conversationId
    ) {
        Message message = messageService.getLastMessageInConversation(conversationId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/getCountOfUnreadMessages")
    public ResponseEntity<?> getCountOfUnreadMessages(
            @RequestParam(value = "conversationId") Integer conversationId,
            @RequestParam(value = "userLogin") String userLogin
    ) {
        Integer result = messageService.getCountOfUnreadMessagesForUser(conversationId, userLogin);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/createConversation")
    public ResponseEntity<?> createConversation(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "userCreatorLogin") String userCreatorLogin
    ) {
        Conversation result = conversationService.createConversation(name, userCreatorLogin);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/deleteConversation")
    public ResponseEntity<?> deleteConversation(
            @RequestParam(value = "conversationId") Integer conversationId
    ) {
        Boolean result = conversationService.deleteConversation(conversationId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
