package com.triciyen.service;

import com.triciyen.LocalStorage;
import com.triciyen.query.handler.ConversationQueryHandler;
import com.triciyen.query.handler.MessageQueryHandler;
import com.triciyen.query.handler.UserAccountQueryHandler;

public interface BaseService {
    LocalStorage localStorage = LocalStorage.getInstance();
    UserAccountQueryHandler userAccountQueryHandler = UserAccountQueryHandler.getInstance();
    ConversationQueryHandler conversationQueryHandler = ConversationQueryHandler.getInstance();
    MessageQueryHandler messageQueryHandler = MessageQueryHandler.getInstance();
}
