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

    private static SceneManager instance;
    private final Stage stage;

    private SceneManager(Stage stage) {
        this.stage = stage;
    }

    public static void initialize(Stage stage) {
        LOGGER.info("Initializing Scene Manager...");
        instance = new SceneManager(stage);
    }

    public FXMLLoader getScene(String fxml) {
        LOGGER.info("Getting scene with resource: {}", fxml);
        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            return loader;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    public static SceneManager getInstance() {
        if (instance == null) throw new IllegalStateException("SceneManager not initialized");
        return instance;
    }

}
