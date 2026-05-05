package com.github.dkw87.chronostt.controller;

import com.github.dkw87.chronostt.StageManager;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.service.ProjectsService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ManageProjectsController {

    @FXML
    private VBox projectsContainer;

    @FXML
    private void initialize() {
        ProjectsService.getInstance().getProjects().forEach(this::addRow);
    }

    @FXML
    private void addNewRow() {}

    @FXML
    private void cancel() {
        StageManager.getInstance().hideManageProjectsView();
        reInitialize();
    }

    @FXML
    private void saveProjects() {}

    private void addRow(Project project) {
        TextField nameField = new TextField(project.getName());
        nameField.setPrefWidth(150);

        TextField afasCodeField = new TextField(project.getAfasCode());
        afasCodeField.setPrefWidth(100);

        CheckBox billableCheckBox = new CheckBox();
        billableCheckBox.setSelected(project.getBillable());
        billableCheckBox.setPrefWidth(60);

        HBox row = new HBox(8, nameField, afasCodeField, billableCheckBox);
        projectsContainer.getChildren().add(row);
    }

    public void reInitialize() {
        projectsContainer.getChildren().clear();
        ProjectsService.getInstance().getProjects().forEach(this::addRow);
    }

}
