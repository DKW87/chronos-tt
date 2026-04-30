package com.github.dkw87.chronostt;

import com.github.dkw87.chronostt.repository.memory.MemoryRepository;
import com.github.dkw87.chronostt.service.TrackingService;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;


public class ChronosApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(ChronosApplication.class);

    @Override
    public void start(Stage stage) {
        initialize();
        StageManager.getInstance().showSettingsView();
        LOG.info("Chronos-TT started.");
    }

    private void initialize() {
        CompletableFuture.allOf(
                CompletableFuture.runAsync(MemoryRepository::initialize),
                CompletableFuture.runAsync(TrackingService::initialize)
        ).join();
    }

    @Override
    public void stop() {
        LOG.info("Chronos-TT stopping...");
    }

}
