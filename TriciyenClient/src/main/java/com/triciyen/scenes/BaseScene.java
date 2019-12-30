package com.triciyen.scenes;

import com.triciyen.LocalStorage;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public interface BaseScene extends EventHandler<Event> {
    LocalStorage localStorage = LocalStorage.getInstance();

    Scene getScene();
    void initialize();
    void destroy();
}
