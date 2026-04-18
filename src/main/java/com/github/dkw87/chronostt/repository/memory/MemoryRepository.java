package com.github.dkw87.chronostt.repository.memory;

import com.github.dkw87.chronostt.model.DayEntry;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.model.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MemoryRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MemoryRepository.class);
    private static final Runnable SHUTDOWN_TASK = () -> {
    };

    private final ReadWriteLock lock;
    private final BlockingQueue<Runnable> queue;

    private Settings settings;
    private List<Project> projects;
    private DayEntry today;
    private List<DayEntry> allDays;

    private MemoryRepository() {
        LOG.info("Initializing MemoryRepository...");
        lock = new ReentrantReadWriteLock();
        queue = new LinkedBlockingQueue<>();
        start();
    }

    private void start() {
        Thread memoryRepositoryThread = new Thread(() -> {
            LOG.info("MemoryRepositoryThread started");
            while (true) {
                try {
                    Runnable task = queue.take();

                    if (task == SHUTDOWN_TASK) break;

                    task.run();
                } catch (InterruptedException e) {
                    LOG.warn("MemoryRepositoryThread interrupted", e);
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            LOG.info("MemoryRepositoryThread completed all queued tasks");
            shutdown();
        }, "MemoryRepositoryThread");
        memoryRepositoryThread.setDaemon(true);
        memoryRepositoryThread.start();
    }

    public void submitSettings(Settings settings) {
        queue.add(() -> {
            lock.writeLock().lock();
            try {
                this.settings = settings;
            } finally {
                lock.writeLock().unlock();
            }
        });
    }

    public Settings getSettings() {
        lock.readLock().lock();
        try {
            return settings;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void submitProjects(List<Project> projects) {
        queue.add(() -> {
            lock.writeLock().lock();
            try {
                this.projects = projects;
            } finally {
                lock.writeLock().unlock();
            }
        });
    }

    public List<Project> getProjects() {
        lock.readLock().lock();
        try {
            return projects;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void submitToday(DayEntry today) {
        queue.add(() -> {
            lock.writeLock().lock();
            try {
                this.today = today;
            } finally {
                lock.writeLock().unlock();
            }
        });
    }

    public DayEntry getToday() {
        lock.readLock().lock();
        try {
            return today;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void submitAllDays(List<DayEntry> allDays) {
        queue.add(() -> {
            lock.writeLock().lock();
            try {
                this.allDays = allDays;
            } finally {
                lock.writeLock().unlock();
            }
        });
    }

    public List<DayEntry> getAllDays() {
        lock.readLock().lock();
        try {
            return allDays;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void stop() {
        LOG.info("MemoryRepository shutting down...");
        queue.add(SHUTDOWN_TASK);
    }

    private void shutdown() {
        LOG.info("MemoryRepository has stopped");
        // maybe add some final writing to disk using StorageRepository
    }

    public static MemoryRepository getInstance() {
        return MemoryRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final MemoryRepository INSTANCE = new MemoryRepository();
    }

}
