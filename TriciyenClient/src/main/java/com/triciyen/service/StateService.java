package com.triciyen.service;

import com.triciyen.entity.UserAccount;

public class StateService {
    private static final StateService instance = new StateService();

    private StateService() {
        this.serverErrorMessage = "";
        this.logged = false;
        this.loggedUserAccount = null;
    }
    public static StateService getInstance() {
        return instance;
    }


    private String serverErrorMessage;
    private boolean logged;
    private UserAccount loggedUserAccount;


    public void setDefaultState() {
        this.logged = false;
        this.loggedUserAccount = UserAccount.builder().build();
    }

    public void setServerErrorMessage(String message) {
        this.serverErrorMessage = message;
    }
    public String getServerErrorMessage() {
        return this.serverErrorMessage;
    }

    public void setLogged(UserAccount account) {
        this.logged = true;
        this.loggedUserAccount = account;
    }
    public boolean getLogged() {
        return this.logged;
    }
    public UserAccount getLoggedAccount() {
        return this.loggedUserAccount;
    }
    public void setLogout() {
        this.logged = false;
        this.loggedUserAccount = null;
    }
}
