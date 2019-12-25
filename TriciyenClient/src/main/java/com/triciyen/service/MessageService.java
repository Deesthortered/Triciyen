package com.triciyen.service;

public class MessageService implements BaseService {
    private static final MessageService instance = new MessageService();
    private static final int messagePageSize = 17;

    private MessageService() {

    }
    public static MessageService getInstance() {
        return instance;
    }


}
