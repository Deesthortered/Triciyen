package com.triciyen;

import com.triciyen.components.ConversationButton;
import com.triciyen.entity.Message;
import com.triciyen.service.MessageService;

public class MessageListener extends Thread {
    private final static int DELAY = 300;
    private final static LocalStorage localStorage = LocalStorage.getInstance();
    private final static MessageService messageService = MessageService.getInstance();

    private int currentConversationId;
    private ConversationButton conversationButton;

    public MessageListener(int conversationId, ConversationButton conversationButton) {
        this.currentConversationId = conversationId;
        this.conversationButton = conversationButton;
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
    }

    private void ActiveAction() {

    }
    private void PassiveAction() {
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
