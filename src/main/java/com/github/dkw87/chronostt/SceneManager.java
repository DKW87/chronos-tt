package com.github.dkw87.chronostt;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SceneManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SceneManager.class);

    private final Stage primaryStage;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public FXMLLoader getScene(String fxml) {
        LOGGER.info("Getting scene with resource: {}", fxml);
        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            scene = new Scene(root);
            primaryStage.setScene(scene);
            return loader;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    public void showSettingsView() {
        LOGGER.info("Switching to SettingsView");
        getScene("view/SettingsView.fxml");
        primaryStage.setWidth(250);
        primaryStage.setHeight(320);
        primaryStage.setAlwaysOnTop(false);
    }

    public void showTrackingView() {
        LOGGER.info("Switching to TrackingView");
        getScene("view/TrackingView.fxml");
        primaryStage.setAlwaysOnTop(true);
    }

}
