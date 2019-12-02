package com.triciyen.scenes;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public interface BaseScene extends EventHandler<Event> {
    Scene getScene();
    void initialize();
}
