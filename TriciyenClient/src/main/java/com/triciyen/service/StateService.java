package com.triciyen.service;

import com.triciyen.entity.UserAccount;

public class StateService {
    private static final StateService instance = new StateService();

    private StateService() {

    }
    public static StateService getInstance() {
        return instance;
    }

    private boolean logged;
    private UserAccount loggedUserAccount;


    public void setDefaultState() {
        this.logged = false;
        this.loggedUserAccount = UserAccount.builder().build();
    }

    public void setLogged(UserAccount account) {
        this.logged = true;
        this.loggedUserAccount = account;
    }

    public void setLogout() {
        this.logged = false;
        this.loggedUserAccount = null;
    }
}
