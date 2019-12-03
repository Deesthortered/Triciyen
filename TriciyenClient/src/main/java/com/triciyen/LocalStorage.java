package com.triciyen;

import com.triciyen.entity.UserAccount;
import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;

public class LocalStorage {
    private static final LocalStorage instance = new LocalStorage();

    private LocalStorage() {
        this.wasError = false;
        this.serverErrorMessage = "";
        this.logged = false;
        this.loggedUserAccount = null;

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
    private String serverErrorMessage;
    private boolean logged;
    private UserAccount loggedUserAccount;


    public void setDefaultState() {
        this.logged = false;
        this.loggedUserAccount = UserAccount.builder().build();
    }

    public boolean isWasError() {
        return wasError;
    }
    public void setServerErrorMessage(String message) {
        this.wasError = true;
        this.serverErrorMessage = message;
    }
    public String getServerErrorMessage() {
        this.wasError = false;
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

    public Image getBaseAccountImage() {
        return baseAccountImage;
    }
}
