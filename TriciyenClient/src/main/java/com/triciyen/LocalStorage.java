package com.triciyen;

import com.triciyen.entity.UserAccount;
import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LocalStorage {
    private static final LocalStorage instance = new LocalStorage();

    private LocalStorage() {
        this.wasError = false;
        this.internalErrorMessage = "";
        this.interfaceErrorMessage = "";
        this.logged = false;
        this.loggedUserAccount = null;

        this.conversationLock = new ReentrantReadWriteLock();
        this.currentActiveConversation = -1;

        try {
            baseAccountImage = new Image(new File("baseAccountImage.png").toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public static LocalStorage getInstance() {
        return instance;
    }

    private Image baseAccountImage;

    private boolean wasError;
    private String internalErrorMessage;
    private String interfaceErrorMessage;

    private boolean logged;
    private UserAccount loggedUserAccount;

    private ReadWriteLock conversationLock;
    private int currentActiveConversation;


    public void setDefaultState() {
        this.logged = false;
        this.loggedUserAccount = UserAccount.builder().build();
    }
    public Image getBaseAccountImage() {
        return baseAccountImage;
    }

    public boolean wasError() {
        return wasError;
    }
    public void    setErrorMessage(String internalMessage, String interfaceMessage) {
        this.wasError = true;
        this.internalErrorMessage = internalMessage;
        this.interfaceErrorMessage = interfaceMessage;
    }
    public String  getInternalErrorMessage() {
        return this.internalErrorMessage;
    }
    public String  getInterfaceErrorMessage() {
        return this.interfaceErrorMessage;
    }
    public void    closeError() {
        this.wasError = false;
        this.internalErrorMessage = "";
        this.interfaceErrorMessage = "";
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

    public int getCurrentActiveConversation() {
        this.conversationLock.readLock().lock();
        int res = currentActiveConversation;
        this.conversationLock.readLock().unlock();
        return res;
    }
    public void setCurrentActiveConversation(int currentActiveConversation) {
        this.conversationLock.writeLock().lock();
        this.currentActiveConversation = currentActiveConversation;
        this.conversationLock.writeLock().unlock();
    }
}
