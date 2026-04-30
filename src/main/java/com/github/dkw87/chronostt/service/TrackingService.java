package com.github.dkw87.chronostt.service;

import com.github.dkw87.chronostt.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TrackingService {

    private static final Logger LOG = LoggerFactory.getLogger(TrackingService.class);
    private static final Runnable SHUTDOWN_TASK = () -> {};
    private static final String CLASS_NAME = TrackingService.class.getName();
    private static final String THREAD_NAME = String.format("%sThread", CLASS_NAME);

    private final ReadWriteLock lock;
    private final BlockingQueue<Runnable> queue;

    private TrackingService() {
        LOG.info("Initializing {}...", CLASS_NAME);
        lock = new ReentrantReadWriteLock();
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
                } catch (InterruptedException e){
                    LOG.error("{} interrupted, {} tasks left in queue", THREAD_NAME, queue.size(), e);
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            shutdown();
        }, THREAD_NAME);
        trackingServiceThread.setDaemon(true);
        trackingServiceThread.start();
    }

    private void shutdown() {
        LOG.info("{} stopped", CLASS_NAME);
        // maybe more stuff
    }

    public void startTracking(Project project) {
    }

    public void stopTracking() {
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
