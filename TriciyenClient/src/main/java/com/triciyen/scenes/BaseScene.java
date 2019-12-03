package com.triciyen.scenes;

import com.triciyen.service.StateService;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public interface BaseScene extends EventHandler<Event> {
    StateService stateService = StateService.getInstance();

    Scene getScene();
    void initialize();
}
