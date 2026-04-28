package com.github.dkw87.chronostt.controller;

import com.github.dkw87.chronostt.StageManager;
import com.github.dkw87.chronostt.model.Project;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class TrackingController {

    @FXML
    private HBox root;

    @FXML
    public ComboBox<Project> projectComboBox;

    private double xOffset;
    private double yOffset;

    @FXML
    public void initialize() {
        draggableStage();
    }

    @FXML
    public void stopTracking() {
        StageManager.getInstance().showSettingsView();
    }

    @FXML
    public void manageProjectsView() {
        StageManager.getInstance().showManageProjectsView();
    }

    private void draggableStage() {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            StageManager.getInstance().getTrackingStage().setX(event.getScreenX() - xOffset);
            StageManager.getInstance().getTrackingStage().setY(event.getScreenY() - yOffset);
        });
    }

}