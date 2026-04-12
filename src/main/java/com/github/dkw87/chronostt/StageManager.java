package com.github.dkw87.chronostt;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class StageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(StageManager.class);

    private final Stage settingsStage = new Stage();
    private final Stage trackingStage = new Stage();

    private StageManager() {
        LOGGER.info("Constructing stages...");
        constructSettingsStage();
        constructTrackingStage();
    }

    private void constructSettingsStage() {
        settingsStage.initStyle(StageStyle.DECORATED);
        getScene("view/SettingsView.fxml",  settingsStage);
        settingsStage.setWidth(250);
        settingsStage.setHeight(320);
        settingsStage.setTitle("Chronos-TT: Settings");
        LOGGER.info("SettingsStage constructed");
    }

    private void constructTrackingStage() {
        trackingStage.initStyle(StageStyle.UNDECORATED);
        getScene("view/TrackingView.fxml",  trackingStage);
        trackingStage.setWidth(250);
        trackingStage.setHeight(50);
        trackingStage.setAlwaysOnTop(true);
        trackingStage.setTitle("Chronos-TT: Tracking Time...");
        LOGGER.info("TrackingStage constructed");
    }

    private FXMLLoader getScene(String fxml, Stage stage) {
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

    public void showSettingsView() {
        LOGGER.info("Switching to SettingsView");
        trackingStage.hide();
        settingsStage.show();
    }

    public void showTrackingView() {
        LOGGER.info("Switching to TrackingView");
        settingsStage.hide();
        trackingStage.show();
    }

    public static StageManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final StageManager INSTANCE = new StageManager();
    }

}
