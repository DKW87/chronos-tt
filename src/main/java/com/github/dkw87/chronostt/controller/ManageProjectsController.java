package com.github.dkw87.chronostt.controller;

import com.github.dkw87.chronostt.StageManager;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.service.ProjectsService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class ManageProjectsController {

    @FXML
    private VBox projectsContainer;

    @FXML
    private void initialize() {
        ProjectsService.getInstance().getProjects().forEach(this::addRow);
    }

    @FXML
    private void addNewRow() {
        addRow(Project.empty());
    }

    @FXML
    private void cancel() {
        StageManager.getInstance().hideManageProjectsView();
        reInitialize();
    }

    @FXML
    private void saveProjects() {}

    private void addRow(Project project) {
        TextField nameField = new TextField(project.getName());
        nameField.setPrefWidth(180);

        TextField afasCodeField = new TextField(project.getAfasCode());
        afasCodeField.setPrefWidth(100);

        CheckBox billableCheckBox = new CheckBox();
        billableCheckBox.setSelected(project.getBillable());

        HBox checkboxWrapper = new HBox(billableCheckBox);
        checkboxWrapper.setPrefWidth(60);
        checkboxWrapper.setAlignment(Pos.BASELINE_CENTER);

        FontIcon deleteIcon = new FontIcon();
        deleteIcon.setIconLiteral("mdi2t-trash-can-outline");
        deleteIcon.setIconSize(20);

        HBox iconWrapper = new HBox(deleteIcon);
        iconWrapper.setPrefWidth(60);
        iconWrapper.setAlignment(Pos.BASELINE_CENTER);

        HBox row = new HBox(8, nameField, afasCodeField, checkboxWrapper, iconWrapper);
        deleteIcon.setOnMouseClicked(e -> projectsContainer.getChildren().remove(row));
        projectsContainer.getChildren().add(row);
    }

    public void reInitialize() {
        projectsContainer.getChildren().clear();
        ProjectsService.getInstance().getProjects().forEach(this::addRow);
    }

}
