package com.github.dkw87.chronostt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ChronosApplication extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChronosApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        LOGGER.info("Chronos-TT starting...");
        FXMLLoader fxmlLoader = new FXMLLoader(ChronosApplication.class.getResource("view/SettingsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 250, 300);
        stage.setTitle("Chronos-TT");
        stage.setScene(scene);
        stage.show();
        LOGGER.info("Chronos-TT started.");
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("Chronos-TT stopping...");
    }

}
