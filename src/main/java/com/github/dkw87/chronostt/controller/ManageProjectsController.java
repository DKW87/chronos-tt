package com.github.dkw87.chronostt.controller;

import com.github.dkw87.chronostt.StageManager;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ManageProjectsController {

    @FXML
    private VBox projectsContainer;

    @FXML
    private void addNewRow() {}

    @FXML
    private void cancel() {
        StageManager.getInstance().hideManageProjectsView();
    }

    @FXML
    private void saveProjects() {}

}
