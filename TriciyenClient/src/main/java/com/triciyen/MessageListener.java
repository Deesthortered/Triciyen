package com.triciyen;

import com.triciyen.components.ConversationButton;
import com.triciyen.entity.Message;
import com.triciyen.scenes.MainScene;
import com.triciyen.service.MessageService;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.List;

public class MessageListener extends Thread {
    private final static int DELAY = 500;
    private final static LocalStorage localStorage = LocalStorage.getInstance();
    private final static MessageService messageService = MessageService.getInstance();

    private int currentConversationId;
    private ConversationButton conversationButton;

    private List<Button> messageButtons;
    private VBox messageBox;
    private boolean first = false;
    private int lastReadMessageId = -1;

    public MessageListener(int conversationId, ConversationButton conversationButton, List<Button> messageButtons, VBox messageBox) {
        this.currentConversationId = conversationId;
        this.conversationButton = conversationButton;

        this.messageButtons = messageButtons;
        this.messageBox = messageBox;
        this.setDaemon(true);
    }
    @Override
    public void run() {
        while (!interrupted()) {
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                System.out.println("Message listener #" + this.currentConversationId + " is interrupted.");
                break;
            }

            if (this.currentConversationId == localStorage.getCurrentActiveConversation()) {
                ActiveAction();
            } else {
                PassiveAction();
            }
        }
        System.out.println("Finished #" + this.currentConversationId + " message listener");
    }

    private void ActiveAction() {
        if (messageButtons == null)
            return;

        if (first) {
            first = false;
            lastReadMessageId = messageService.getLastReadMessageIdOfConversation(localStorage.getCurrentActiveConversation());
        }
        List<Message> lastMessages = messageService
                .getLastMessagesOfConversation(localStorage.getCurrentActiveConversation(), lastReadMessageId);

        lastMessages.forEach(message -> {
            Button messageButton = MainScene.mapMessageToButton(message);
            messageButtons.add(messageButton);
            messageBox.getChildren().add(messageButton);
        });

        lastReadMessageId = lastMessages.get(lastMessages.size() - 1).getMessageId();
        messageService.setLastReadMessageOfTheConversation(localStorage.getCurrentActiveConversation(), lastReadMessageId);
    }
    private void PassiveAction() {
        first = true;
        Message lastMessage = messageService.getLastMessage(currentConversationId);
        if (localStorage.wasError()) {
            System.err.println(localStorage.getInternalErrorMessage());
            localStorage.closeError();
        }
        Integer countUnread = messageService.getCountOfUnreadMessages(currentConversationId);
        if (localStorage.wasError()) {
            System.err.println(localStorage.getInternalErrorMessage());
            localStorage.closeError();
        }

        conversationButton.setUnreadCounter(String.valueOf(countUnread));
        conversationButton.setLastTime(String.valueOf(lastMessage.getCreationTime()));
        conversationButton.setLastMessage(lastMessage.getContent());
    }
}
