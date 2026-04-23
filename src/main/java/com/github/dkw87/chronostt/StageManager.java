package com.github.dkw87.chronostt;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Getter
public class StageManager {

    private static final Logger LOG = LoggerFactory.getLogger(StageManager.class);
    private static final int SETTINGS_STAGE_WIDTH = 250;
    private static final int SETTINGS_STAGE_HEIGHT = 410;
    private static final int TRACKING_STAGE_WIDTH = 300;
    private static final int TRACKING_STAGE_HEIGHT = 50;

    private final Stage settingsStage = new Stage();
    private final Stage trackingStage = new Stage();

    private StageManager() {
        LOG.info("Constructing stages...");
        constructSettingsStage();
        constructTrackingStage();
    }

    private void constructSettingsStage() {
        settingsStage.initStyle(StageStyle.DECORATED);
        getScene("view/SettingsView.fxml",  settingsStage);
        settingsStage.setWidth(SETTINGS_STAGE_WIDTH);
        settingsStage.setHeight(SETTINGS_STAGE_HEIGHT);
        settingsStage.setResizable(false);
        settingsStage.setTitle("Chronos-TT: Settings");
        LOG.info("SettingsStage constructed");
    }

    private void constructTrackingStage() {
        trackingStage.initStyle(StageStyle.UNDECORATED);
        getScene("view/TrackingView.fxml",  trackingStage);
        trackingStage.setWidth(TRACKING_STAGE_WIDTH);
        trackingStage.setHeight(TRACKING_STAGE_HEIGHT);
        trackingStage.setAlwaysOnTop(true);
        trackingStage.setTitle("Chronos-TT: Tracking Time...");
        LOG.info("TrackingStage constructed");
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
        LOG.info("Switching to SettingsView");
        trackingStage.hide();
        settingsStage.show();
    }

    public void showTrackingView() {
        LOG.info("Switching to TrackingView");
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
