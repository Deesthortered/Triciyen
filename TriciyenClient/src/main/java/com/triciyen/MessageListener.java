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
    private final static int DELAY = 150;
    private final static LocalStorage localStorage = LocalStorage.getInstance();
    private final static MessageService messageService = MessageService.getInstance();

    private int currentConversationId;
    private volatile ConversationButton conversationButton;

    private volatile List<ChatMessageBox> messageButtons;
    private volatile VBox messageBox;

    private boolean first = false;
    private int lastReadMessageId = -1;
    private int prevLastMessageId = -1;

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
            conversationButton.setUnreadCounter(String.valueOf(0));
            lastReadMessageId = messageService.getLastReadMessageIdOfConversation(localStorage.getCurrentActiveConversation());
        }
        List<Message> lastMessages = messageService
                .getLastMessagesOfConversation(localStorage.getCurrentActiveConversation(), lastReadMessageId);

        if (!lastMessages.isEmpty()) {
            lastMessages.forEach(message -> {
                ChatMessageBox messageButton = MainScene.mapMessageToButton(message);
                messageButtons.add(messageButton);
                Platform.runLater(() -> messageBox.getChildren().add(messageButton));
            });

            Message lastMessage = lastMessages.get(lastMessages.size() - 1);
            lastReadMessageId = lastMessage.getMessageId();
            conversationButton.setLastTime(String.valueOf(
                    lastMessage.getCreationTime() == null ? "" : lastMessage.getCreationTime()));
            conversationButton.setLastMessage(lastMessage.getContent());

            messageService.setLastReadMessageOfTheConversation(localStorage.getCurrentActiveConversation(), lastReadMessageId);
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

        if (lastMessage.getMessageId() == prevLastMessageId) {
            return;
        }
        prevLastMessageId = lastMessage.getMessageId();
        conversationButton.setUnreadCounter(String.valueOf(countUnread));
        conversationButton.setLastTime(String.valueOf(
                lastMessage.getCreationTime() == null ? "" : lastMessage.getCreationTime()));
        conversationButton.setLastMessage(lastMessage.getContent());
    }
}
