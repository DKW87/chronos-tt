package com.github.dkw87.chronostt;

import com.github.dkw87.chronostt.repository.memory.MemoryRepository;
import com.github.dkw87.chronostt.service.TrackingService;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ChronosApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(ChronosApplication.class);

    @Override
    public void start(Stage stage) {
        MemoryRepository.initialize();
        TrackingService.initialize();
        StageManager.getInstance().showSettingsView();
        LOG.info("Chronos-TT started.");
    }

    @Override
    public void stop() {
        LOG.info("Chronos-TT stopping...");
    }

}
