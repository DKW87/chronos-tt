package com.github.dkw87.chronostt.service;

import com.github.dkw87.chronostt.model.DayEntry;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.model.TimeEntry;
import com.github.dkw87.chronostt.repository.memory.MemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackingService {

    private static final Logger LOG = LoggerFactory.getLogger(TrackingService.class);
    private static final Runnable SHUTDOWN_TASK = () -> {};
    private static final String CLASS_NAME = TrackingService.class.getSimpleName();
    private static final String THREAD_NAME = String.format("%sThread", CLASS_NAME);

    private final BlockingQueue<Runnable> queue;

    private DayEntry today;

    private TrackingService() {
        LOG.info("Initializing {}...", CLASS_NAME);
        queue = new LinkedBlockingQueue<>();
        start();
    }

    private void start() {
        Thread trackingServiceThread = new Thread(() -> {
            LOG.info("{} started", THREAD_NAME);
            while (true) {
                try {
                    Runnable task = queue.take();

                    if (task == SHUTDOWN_TASK) {
                        LOG.info("{} completed all tasks", THREAD_NAME);
                        break;
                    }

                    task.run();

                    if (queue.peek() == null) {
                        submitTrackingData();
                    }
                } catch (InterruptedException e){
                    LOG.error("{} interrupted, {} tasks left in queue - attempting to store today's tracking data",
                            THREAD_NAME, queue.size(), e);
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            shutdown();
        }, THREAD_NAME);
        trackingServiceThread.setDaemon(true);
        trackingServiceThread.start();
    }

    private void submitTrackingData() {
        if (today == null) return;
        LOG.info("Submitting tracking data...");
        MemoryRepository.getInstance().submitToday(today);
    }

    private void shutdown() {
        submitTrackingData();
        LOG.info("{} stopped", CLASS_NAME);
    }

    public void startTracking(Project project) {
        if (project == null) {
            LOG.error("Project cannot be null");
            return;
        }

        queue.add(() -> {
            if (today == null) today = MemoryRepository.getInstance().getToday();
            final TimeEntry entry = TimeEntry.builder()
                    .id(MemoryRepository.getInstance().getIncrementedTimeEntryId())
                    .project(project)
                    .start(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .build();
            today.getTimeEntries().add(entry);
            LOG.info("Started tracking {} with id {}", entry.getProject().getName(), entry.getId());
        });
    }

    public void stopTracking() {
        queue.add(() -> {
            final long latestId = MemoryRepository.getInstance().getTimeEntryId();
            TimeEntry entry = today.getTimeEntries().stream()
                    .filter(t -> t.getId().equals(latestId))
                    .findFirst()
                    .get();
            today.getTimeEntries().remove(entry);
            entry.setEnd(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            today.getTimeEntries().add(entry);
            LOG.info("Stopped tracking {} with id {}", entry.getProject().getName(), entry.getId());
        });
    }

    public void stop() {
        LOG.info("{} shutting down...", CLASS_NAME);
        queue.add(SHUTDOWN_TASK);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void initialize() {
        getInstance();
    }

    public static TrackingService getInstance() {
        return TrackingService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final TrackingService INSTANCE = new TrackingService();
    }
    
}
