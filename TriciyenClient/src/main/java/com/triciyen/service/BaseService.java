package com.triciyen.service;

import com.triciyen.query.handler.UserAccountQueryHandler;

public interface BaseService {
    StateService stateService = StateService.getInstance();
    UserAccountQueryHandler userAccountQueryHandler = UserAccountQueryHandler.getInstance();
}
