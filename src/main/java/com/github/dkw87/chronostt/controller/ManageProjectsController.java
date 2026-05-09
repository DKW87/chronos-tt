package com.github.dkw87.chronostt.controller;

import com.github.dkw87.chronostt.StageManager;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.service.ProjectsService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManageProjectsController {

    private static final Logger LOG = LoggerFactory.getLogger(ManageProjectsController.class);
    private static final String DELETE_ICON = "mdi2t-trash-can-outline";
    private static final String ALERT_TITLE = "Validation failed";

    private static final String MESSAGE_EMPTY = "At least one project is required.";
    private static final String MESSAGE_NAME = "For each project a name is required and cannot be empty.";

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
    private void saveProjects() {
        final List<Project> projects = projectsContainer.getChildren().stream()
                .map(node -> mapRowToProject((HBox) node))
                .toList();

        if (hasInvalidProjects(projects)) return;

        ProjectsService.getInstance().storeProjects(projects);
        StageManager.getInstance().getSettingsController().reinitProjectComboBox(projects);
        StageManager.getInstance().hideManageProjectsView();
    }

    private boolean hasInvalidProjects(List<Project> projects) {
        final String message = getValidationMessage(projects);
        if (message != null) {
            LOG.error("Projects could not be validated");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ALERT_TITLE);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.show();
            return true;
        }
        return false;
    }

    private String getValidationMessage(List<Project> projects) {

        if (projects.isEmpty()) {
            LOG.warn("No project(s) to validate");
            return MESSAGE_EMPTY;
        }

        final boolean noName = projects.stream()
                .anyMatch(p -> p.getName() == null || p.getName().isEmpty() || p.getName().isBlank());

        if (noName) {
            LOG.warn("Project(s) did not contain a Name");
            return MESSAGE_NAME;
        }

        return null;
    }

    private Project mapRowToProject(HBox row) {
        TextField nameField = (TextField) row.getChildren().get(0);
        TextField afasCodeField = (TextField) row.getChildren().get(1);
        HBox billableWrapper = (HBox) row.getChildren().get(2);
        CheckBox billableCheckBox  = (CheckBox) billableWrapper.getChildren().getFirst();

        return Project.builder()
                .id((Long) row.getUserData())
                .name(nameField.getText())
                .afasCode(afasCodeField.getText())
                .billable(billableCheckBox.isSelected())
                .deleted(false)
                .build();
    }

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
        deleteIcon.setIconLiteral(DELETE_ICON);
        deleteIcon.setIconSize(20);
        deleteIcon.setCursor(Cursor.HAND);

        HBox iconWrapper = new HBox(deleteIcon);
        iconWrapper.setPrefWidth(60);
        iconWrapper.setAlignment(Pos.BASELINE_CENTER);

        HBox row = new HBox(8, nameField, afasCodeField, checkboxWrapper, iconWrapper);
        row.setUserData(project.getId());
        deleteIcon.setOnMouseClicked(e -> projectsContainer.getChildren().remove(row));
        projectsContainer.getChildren().add(row);
    }

    public void reInitialize() {
        projectsContainer.getChildren().clear();
        ProjectsService.getInstance().getProjects().forEach(this::addRow);
    }

}
