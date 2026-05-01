package com.github.dkw87.chronostt;

import com.github.dkw87.chronostt.repository.memory.MemoryRepository;
import com.github.dkw87.chronostt.service.TrackingService;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class ChronosApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(ChronosApplication.class);
    private static final int THREADS = 2;
    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger(1);
    private static final String INITIALIZATION_THREAD_NAME = "InitializationThread-";

    @Override
    public void start(Stage stage) {
        initialize();
        StageManager.getInstance().showSettingsView();
        LOG.info("Chronos-TT started.");
    }

    private void initialize() {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS,
                runnable -> new Thread(runnable, INITIALIZATION_THREAD_NAME + THREAD_COUNTER.getAndIncrement())
        );

        CompletableFuture.allOf(
                CompletableFuture.runAsync(MemoryRepository::initialize, executor),
                CompletableFuture.runAsync(TrackingService::initialize, executor)
        ).join();
    }

    @Override
    public void stop() {
        LOG.info("Chronos-TT stopping...");
    }

}
