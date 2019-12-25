package com.triciyen;

public class MessageListener extends Thread {
    private final static int DELAY = 300;
    private final static LocalStorage localStorage = LocalStorage.getInstance();
    private int currentConversationId;

    public MessageListener(int conversationId) {
        this.currentConversationId = conversationId;
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

    }
}
