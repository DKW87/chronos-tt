package com.github.dkw87.chronostt;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ChronosApplication extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChronosApplication.class);
    private static final StageManager STAGE_MANAGER = StageManager.getInstance();

    @Override
    public void start(Stage stage) {
        STAGE_MANAGER.showSettingsView();
        LOGGER.info("Chronos-TT started.");
    }

    @Override
    public void stop() {
        LOGGER.info("Chronos-TT stopping...");
    }

}
