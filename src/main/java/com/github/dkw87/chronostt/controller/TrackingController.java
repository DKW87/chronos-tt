package com.github.dkw87.chronostt.controller;

import com.github.dkw87.chronostt.StageManager;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.service.ProjectsService;
import com.github.dkw87.chronostt.service.TrackingDaysService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

public class TrackingController {

    @FXML
    private HBox root;

    @FXML
    private ComboBox<Project> projectComboBox;

    private double xOffset;
    private double yOffset;

    @FXML
    private void initialize() {
        draggableStage();
        initProjectComboBox();
        setListeners();
    }

    @FXML
    private void stopTracking() {
        TrackingDaysService.getInstance().stopTracking();
        StageManager.getInstance().showSettingsView();
    }

    @FXML
    private void manageProjectsView() {
        StageManager.getInstance().showManageProjectsView();
    }

    private void initProjectComboBox() {
        projectComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Project project) {
                if (project == null) return "";
                return project.getName();
            }

            @Override
            public Project fromString(String string) {
                return null; // not used
            }
        });
        projectComboBox.getItems().setAll(ProjectsService.getInstance().getProjects());
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

    private void setListeners() {
        projectComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) return;
            startTracking();
        });
    }

    private void startTracking() {
        TrackingDaysService.getInstance().startTracking(projectComboBox.getValue());
    }

    public void setProjectToTrack(Project project) {
        projectComboBox.setValue(project);
    }

}