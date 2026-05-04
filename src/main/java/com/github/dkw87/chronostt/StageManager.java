package com.github.dkw87.chronostt;

import com.github.dkw87.chronostt.controller.ManageProjectsController;
import com.github.dkw87.chronostt.controller.SettingsController;
import com.github.dkw87.chronostt.controller.TrackingController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Getter
public class StageManager {

    private static final Logger LOG = LoggerFactory.getLogger(StageManager.class);

    // stage dimensions
    private static final int SETTINGS_STAGE_WIDTH = 250;
    private static final int SETTINGS_STAGE_HEIGHT = 410;
    private static final int TRACKING_STAGE_WIDTH = 300;
    private static final int TRACKING_STAGE_HEIGHT = 50;
    private static final int MANAGE_PROJECTS_STAGE_WIDTH = 350;
    private static final int MANAGE_PROJECTS_STAGE_HEIGHT = 500;

    // stage titles
    private static final String SETTINGS_STAGE_TITLE = "Chronos-TT: Settings";
    private static final String TRACKING_STAGE_TITLE = "Chronos-TT: Tracking Time...";
    private static final String MANAGE_PROJECTS_STAGE_TITLE = "Chronos-TT: Manage Projects";

    // stage FXML
    private static final String SETTINGS_STAGE_FXML = "view/SettingsView.fxml";
    private static final String TRACKING_STAGE_FXML = "view/TrackingView.fxml";
    private static final String MANAGE_PROJECTS_STAGE_FXML = "view/ManageProjectsView.fxml";

    private final Stage settingsStage = new Stage();
    private final Stage trackingStage = new Stage();
    private final Stage manageProjectsStage = new Stage();

    public final SettingsController settingsController;
    public final TrackingController trackingController;
    public final ManageProjectsController manageProjectsController;

    private StageManager() {
        LOG.info("Constructing stages...");
        final FXMLLoader settingsLoader = constructSettingsStage();
        settingsController = settingsLoader.getController();

        final FXMLLoader trackingLoader = constructTrackingStage();
        trackingController = trackingLoader.getController();

        final FXMLLoader manageProjectsLoader = constructManageProjectsStage();
        manageProjectsController = manageProjectsLoader.getController();
    }

    private FXMLLoader constructSettingsStage() {
        settingsStage.initStyle(StageStyle.DECORATED);
        settingsStage.setWidth(SETTINGS_STAGE_WIDTH);
        settingsStage.setHeight(SETTINGS_STAGE_HEIGHT);
        settingsStage.setResizable(false);
        settingsStage.setTitle(SETTINGS_STAGE_TITLE);

        final FXMLLoader loader = getScene(SETTINGS_STAGE_FXML,  settingsStage);
        LOG.info("SettingsStage constructed");
        return loader;
    }

    private FXMLLoader constructTrackingStage() {
        trackingStage.initStyle(StageStyle.UNDECORATED);
        trackingStage.setWidth(TRACKING_STAGE_WIDTH);
        trackingStage.setHeight(TRACKING_STAGE_HEIGHT);
        trackingStage.setResizable(false);
        trackingStage.setAlwaysOnTop(true);
        trackingStage.setTitle(TRACKING_STAGE_TITLE);

        final FXMLLoader loader = getScene(TRACKING_STAGE_FXML,  trackingStage);
        LOG.info("TrackingStage constructed");
        return loader;
    }

    private FXMLLoader constructManageProjectsStage() {
        manageProjectsStage.initStyle(StageStyle.DECORATED);
        manageProjectsStage.initModality(Modality.APPLICATION_MODAL);
        manageProjectsStage.setWidth(MANAGE_PROJECTS_STAGE_WIDTH);
        manageProjectsStage.setHeight(MANAGE_PROJECTS_STAGE_HEIGHT);
        manageProjectsStage.setResizable(false);
        manageProjectsStage.setTitle(MANAGE_PROJECTS_STAGE_TITLE);

        final FXMLLoader loader = getScene(MANAGE_PROJECTS_STAGE_FXML,  manageProjectsStage);
        LOG.info("ManageProjectsStage constructed");
        return loader;
    }

    private FXMLLoader getScene(String fxml, Stage stage) {
        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            return loader;
        } catch (IOException e) {
            LOG.error("Unable to get scene", e);
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

    public void showManageProjectsView() {
        LOG.info("Showing ManageProjectsView");
        manageProjectsStage.show();
    }

    public void hideManageProjectsView() {
        LOG.info("Hiding ManageProjectsView");
        manageProjectsStage.hide();
    }

    public static StageManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final StageManager INSTANCE = new StageManager();
    }

}
