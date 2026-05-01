package com.github.dkw87.chronostt.service;

import com.github.dkw87.chronostt.model.DayEntry;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.repository.memory.MemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        LOG.info("Submitting today's data");
        MemoryRepository.getInstance().submitToday(today);
    }

    private void shutdown() {
        submitTrackingData();
        LOG.info("{} stopped", CLASS_NAME);
    }

    public void startTracking(Project project) {
        queue.add(() -> {
            LOG.info("{}", project.getName());
        });
    }

    public void stopTracking() {
        queue.add(() -> {
            LOG.info("Stopped time tracking");
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
