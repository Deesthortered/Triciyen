package com.triciyen;

import com.triciyen.components.ChatMessageBox;
import com.triciyen.components.ConversationButton;
import com.triciyen.entity.Message;
import com.triciyen.scenes.MainScene;
import com.triciyen.service.MessageService;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.util.List;

public class MessageListener extends Thread {
    private final static int DELAY = 300;
    private final static LocalStorage localStorage = LocalStorage.getInstance();
    private final static MessageService messageService = MessageService.getInstance();

    private int currentConversationId;
    private volatile ConversationButton conversationButton;

    private volatile List<ChatMessageBox> messageButtons;
    private volatile VBox messageBox;
    private boolean first = false;
    private int lastReadMessageId = -1;

    public MessageListener(int conversationId, ConversationButton conversationButton,
                           List<ChatMessageBox> messageButtons, VBox messageBox) {
        this.currentConversationId = conversationId;
        this.conversationButton = conversationButton;

        this.messageButtons = messageButtons;
        this.messageBox = messageBox;
        this.setDaemon(true);
        this.setPriority(Thread.MAX_PRIORITY);
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
        if (first) {
            first = false;
            lastReadMessageId = messageService.getLastReadMessageIdOfConversation(localStorage.getCurrentActiveConversation());
        }

        List<Message> lastMessages = messageService
                .getLastMessagesOfConversation(localStorage.getCurrentActiveConversation(), lastReadMessageId);

        if (!lastMessages.isEmpty()) {
            lastMessages.forEach(message -> {
                ChatMessageBox messageButton = MainScene.mapMessageToButton(message);
                messageButtons.add(messageButton);
                Platform.runLater(() -> {
                    messageBox.getChildren().add(messageButton);
                });
            });

            Message lastMessage = lastMessages.get(lastMessages.size() - 1);
            lastReadMessageId = lastMessage.getMessageId();
            messageService.setLastReadMessageOfTheConversation(localStorage.getCurrentActiveConversation(), lastReadMessageId);

            conversationButton.setUnreadCounter(String.valueOf(0));
            conversationButton.setLastTime(String.valueOf(lastMessage.getCreationTime()));
            conversationButton.setLastMessage(lastMessage.getContent());
        }
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
