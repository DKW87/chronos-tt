package com.github.dkw87.chronostt.repository.storage;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.dkw87.chronostt.enumeration.SaveMethod;
import com.github.dkw87.chronostt.enumeration.TimeScale;
import com.github.dkw87.chronostt.model.DayEntry;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.model.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class StorageRepository {

    private static final Logger LOG = LoggerFactory.getLogger(StorageRepository.class);
    private static final Runnable SHUTDOWN_TASK = () -> {};
    private static final String THREAD_NAME = "StorageRepositoryThread";

    private static final String USER_HOME = System.getProperty("user.home");
    private static final String APP_DIR = ".chronos-tt";
    private static final Path PATH = Path.of(USER_HOME, APP_DIR);
    private static final String SETTINGS_FILE = "chronos-settings.json";
    private static final String PROJECTS_FILE = "chronos-projects.json";
    private static final String TRACKED_DAYS_FILE = "chronos-tracked-days.json";

    private final BlockingQueue<Runnable> queue;
    private final ObjectMapper objectMapper;
    
    private StorageRepository(){
        LOG.info("Initializing StorageRepository...");
        createAppDir();
        queue = new LinkedBlockingQueue<>();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        start();
    }

    private void createAppDir() {
        try {
            Files.createDirectories(PATH);
        } catch (IOException e) {
            LOG.error("Unable to create application directory", e);
        }
    }

    private void start() {
        Thread storageRepositoryThread = new Thread(() -> {
            LOG.info("{} started", THREAD_NAME);
            while (true) {
                try {
                    Runnable task = queue.take();

                    if (task == SHUTDOWN_TASK) {
                        LOG.info("{} completed all queued tasks", THREAD_NAME);
                        break;
                    }

                    task.run();
                } catch (InterruptedException e) {
                    LOG.error("{} interrupted, {} tasks left in queue", THREAD_NAME, queue.size(), e);
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            LOG.info("{} stopped", THREAD_NAME);
            shutdown();
        }, THREAD_NAME);
        storageRepositoryThread.setDaemon(true);
        storageRepositoryThread.start();
    }

    public Settings getSettings() {
        final File file = PATH.resolve(SETTINGS_FILE).toFile();
        Settings settings = null;

        if (!file.exists()) {
            LOG.warn("{} does not exist, returning default values", SETTINGS_FILE);
            settings = Settings.builder()
                    .daysWeek(5)
                    .hoursDaily(8)
                    .timeScale(TimeScale.THIRTY_MINUTES)
                    .notifyOvertime(false)
                    .aggregateProjectHours(true)
                    .build();
            saveSettings(settings, SaveMethod.SYNCHRONOUS);
            return settings;
        }

        try {
            settings = objectMapper.readValue(file, Settings.class);
        } catch (IOException e) {
            LOG.error("Unable to deserialize settings", e);
        }

        if (settings != null) {
            LOG.info("{} successfully loaded", SETTINGS_FILE);
            return settings;
        }

        LOG.error("Unable to load or provide {}, returning null", SETTINGS_FILE);
        return null;
    }

    public void saveSettings(Settings settings, SaveMethod method) {
        if (method == SaveMethod.ASYNCHRONOUS) {
            queue.add(() -> persistSettings(settings));
            return;
        }
        persistSettings(settings);
    }

    private void persistSettings(Settings settings) {
        LOG.info("Saving {}...", SETTINGS_FILE);
        final File file = PATH.resolve(SETTINGS_FILE).toFile();
        try {
            objectMapper.writeValue(file, settings);
        } catch (IOException e) {
            LOG.error("Unable to serialize settings", e);
        }
        LOG.info("Successfully saved {}", SETTINGS_FILE);
    }

    public List<Project> getProjects() {
        final File file = PATH.resolve(PROJECTS_FILE).toFile();
        List<Project> projects = new ArrayList<>();

        if (!file.exists()) {
            LOG.warn("{} does not exist, returning default values", PROJECTS_FILE);
            projects = List.of(
                    Project.builder().id(1L).name("CHANGE-ME").afasCode("").build()
            );
            saveProjects(projects, SaveMethod.SYNCHRONOUS);
            return projects;
        }

        try {
            projects = objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            LOG.error("Unable to deserialize projects", e);
        }

        if (!projects.isEmpty()) {
            LOG.info("{} successfully loaded", PROJECTS_FILE);
            return projects;
        }

        LOG.error("Unable to load or provide {}, returning empty list", PROJECTS_FILE);
        return null;
    }

    public void saveProjects(List<Project> projects, SaveMethod method) {
        if (method == SaveMethod.ASYNCHRONOUS) {
            queue.add(() -> persistProjects(projects));
            return;
        }
        persistProjects(projects);
    }

    private void persistProjects(List<Project> projects) {
        LOG.info("Saving {}...", PROJECTS_FILE);
        final File file = PATH.resolve(PROJECTS_FILE).toFile();
        try {
            objectMapper.writeValue(file, projects);
        } catch (IOException e) {
            LOG.error("Unable to serialize projects", e);
        }
        LOG.info("Successfully saved {}", PROJECTS_FILE);
    }

    public List<DayEntry> getTrackedDays() {
        final File file = PATH.resolve(TRACKED_DAYS_FILE).toFile();
        List<DayEntry> days = new ArrayList<>();

        if (!file.exists()) {
            LOG.warn("{} does not exist, returning empty list", TRACKED_DAYS_FILE);
            return days;
        }

        try {
            days = objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            LOG.error("Unable to deserialize tracked days", e);
        }

        if (!days.isEmpty()) {
            LOG.info("{} successfully loaded", TRACKED_DAYS_FILE);
            return days;
        }

        LOG.error("Unable to load {}, returning empty list", TRACKED_DAYS_FILE);
        return null;
    }

    public void stop() {
        LOG.info("StorageRepository shutting down...");
        queue.add(SHUTDOWN_TASK);
    }

    private void shutdown() {
        LOG.info("StorageRepository has stopped");
        // do something maybe
    }

    public static StorageRepository getInstance() {
        return StorageRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final StorageRepository INSTANCE = new StorageRepository();
    }
    
}
