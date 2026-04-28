package com.github.dkw87.chronostt.controller;

import com.github.dkw87.chronostt.StageManager;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ManageProjectsController {

    @FXML
    public VBox projectsContainer;

    @FXML
    public void addNewRow() {}

    @FXML
    public void cancel() {
        StageManager.getInstance().hideManageProjectsView();
    }

    @FXML
    public void saveProjects() {}

}
