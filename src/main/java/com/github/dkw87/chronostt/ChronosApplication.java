package com.github.dkw87.chronostt;

import com.github.dkw87.chronostt.repository.memory.MemoryRepository;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ChronosApplication extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChronosApplication.class);

    @Override
    public void start(Stage stage) {
        MemoryRepository.initialize();
        StageManager.getInstance().showSettingsView();
        LOGGER.info("Chronos-TT started.");
    }

    @Override
    public void stop() {
        LOGGER.info("Chronos-TT stopping...");
    }

}
