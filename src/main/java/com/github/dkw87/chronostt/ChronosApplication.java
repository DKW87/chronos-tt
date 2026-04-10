package com.github.dkw87.chronostt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChronosApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChronosApplication.class.getResource("view/SettingsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 250, 300);
        stage.setTitle("Chronos-TT");
        stage.setScene(scene);
        stage.show();
    }

}
