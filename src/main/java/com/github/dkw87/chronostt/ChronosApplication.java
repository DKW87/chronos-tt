package com.github.dkw87.chronostt;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ChronosApplication extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChronosApplication.class);
    private static SceneManager sceneManager;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        LOGGER.info("Chronos-TT starting...");
        primaryStage = stage;
        getSceneManager().showSettingsView();
        primaryStage.setTitle("Chronos-TT");
        primaryStage.show();
        LOGGER.info("Chronos-TT started.");
    }

    @Override
    public void stop() {
        LOGGER.info("Chronos-TT stopping...");
    }

    public static SceneManager getSceneManager() {
        if (sceneManager == null) {
            LOGGER.info("Initializing SceneManager...");
            sceneManager = new SceneManager(primaryStage);
        }
        return sceneManager;
    }

}
