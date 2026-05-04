package com.github.dkw87.chronostt.repository.memory;

import com.github.dkw87.chronostt.enumeration.SaveMethod;
import com.github.dkw87.chronostt.model.DayEntry;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.model.Settings;
import com.github.dkw87.chronostt.model.TimeEntry;
import com.github.dkw87.chronostt.repository.storage.StorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MemoryRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MemoryRepository.class);
    private static final Runnable SHUTDOWN_TASK = () -> {};
    private static final String CLASS_NAME = MemoryRepository.class.getSimpleName();
    private static final String THREAD_NAME = String.format("%sThread", CLASS_NAME);

    private static final long NO_ID = -1L;
    private static final AtomicLong DAY_ENTRY_ID = new AtomicLong(0L);
    private static final AtomicLong PROJECT_ID = new AtomicLong(0L);
    private static final AtomicLong TIME_ENTRY_ID = new AtomicLong(0L);

    private final ReadWriteLock lock;
    private final BlockingQueue<Runnable> queue;

    private Settings settings;
    private List<Project> projects;
    private DayEntry today;
    private List<DayEntry> trackedDays;

    private MemoryRepository() {
        LOG.info("Initializing {}...", CLASS_NAME);
        final long startTime = System.currentTimeMillis();

        lock = new ReentrantReadWriteLock();
        queue = new LinkedBlockingQueue<>();
        loadSavedData();
        setModelIds();
        setToday();

        final long currentTime = System.currentTimeMillis();
        LOG.info("Initialization completed in {}MS", (currentTime - startTime));

        start();
    }

    private void loadSavedData() {
        settings = StorageRepository.getInstance().loadSettings();
        projects = StorageRepository.getInstance().loadProjects();
        trackedDays = StorageRepository.getInstance().loadTrackedDays();
    }

    private void setModelIds() {
        final long projectId = projects.stream().mapToLong(Project::getId).max().orElse(NO_ID);
        final long dayEntryId = trackedDays.stream().mapToLong(DayEntry::getId).max().orElse(NO_ID);
        final List<TimeEntry> timeEntries = trackedDays.stream().flatMap(
                day -> day.getTimeEntries().stream()
        ).toList();
        final long timeEntryId = timeEntries.stream().mapToLong(TimeEntry::getId).max().orElse(NO_ID);

        if (projectId > NO_ID) PROJECT_ID.set(projectId);
        if (dayEntryId > NO_ID) DAY_ENTRY_ID.set(dayEntryId);
        if (timeEntryId > NO_ID) TIME_ENTRY_ID.set(timeEntryId);
    }

    private void setToday() {
        final LocalDate date = LocalDate.now();
        final boolean todayExists = trackedDays.stream().anyMatch(day -> day.getDay().equals(date));

        if (todayExists) {
            today = trackedDays.stream().filter(day -> day.getDay().equals(date)).findFirst().get();
            return;
        }

        createToday(date);
    }

    private void createToday(LocalDate date) {
        today = DayEntry.builder()
                .id(DAY_ENTRY_ID.incrementAndGet())
                .day(date)
                .timeEntries(List.of())
                .minutesWorked(0L)
                .build();
    }

    private void start() {
        Thread memoryRepositoryThread = new Thread(() -> {
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
                    LOG.error("{} interrupted, {} tasks left in queue ", THREAD_NAME, queue.size(), e);
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            shutdown();
        }, THREAD_NAME);
        memoryRepositoryThread.setDaemon(true);
        memoryRepositoryThread.start();
    }

    public void submitSettings(Settings settings) {
        queue.add(() -> {
            lock.writeLock().lock();
            try {
                LOG.info("Submitting settings to save");
                this.settings = settings;
                StorageRepository.getInstance().saveSettings(this.settings, SaveMethod.ASYNCHRONOUS);
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
                LOG.info("Submitting projects to save");
                this.projects = projects;
                StorageRepository.getInstance().saveProjects(projects, SaveMethod.ASYNCHRONOUS);
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

    public void submitToday(DayEntry newToday) {
        queue.add(() -> {
            lock.writeLock().lock();
            try {
                this.today = newToday;
                trackedDays.removeIf(day -> day.getDay().equals(newToday.getDay()));
                trackedDays.add(newToday);
                StorageRepository.getInstance().saveTrackedDays(trackedDays, SaveMethod.ASYNCHRONOUS);
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

    public void submitTrackedDays(List<DayEntry> trackedDays) {
        queue.add(() -> {
            lock.writeLock().lock();
            try {
                LOG.info("Submitting tracked days to save");
                this.trackedDays = trackedDays;
                StorageRepository.getInstance().saveTrackedDays(this.trackedDays, SaveMethod.ASYNCHRONOUS);
            } finally {
                lock.writeLock().unlock();
            }
        });
    }

    public List<DayEntry> getTrackedDays() {
        lock.readLock().lock();
        try {
            return trackedDays;
        } finally {
            lock.readLock().unlock();
        }
    }

    public long getTimeEntryId() {
        lock.readLock().lock();
        try {
            return TIME_ENTRY_ID.get();
        } finally {
            lock.readLock().unlock();
        }
    }

    public long getIncrementedTimeEntryId() {
        lock.readLock().lock();
        try {
            return TIME_ENTRY_ID.incrementAndGet();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void stop() {
        LOG.info("{} shutting down...", CLASS_NAME);
        queue.add(SHUTDOWN_TASK);
    }

    private void shutdown() {
        LOG.info("{} has stopped", CLASS_NAME);
        // maybe add some final writing to disk using StorageRepository
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void initialize() {
        getInstance();
    }

    public static MemoryRepository getInstance() {
        return MemoryRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final MemoryRepository INSTANCE = new MemoryRepository();
    }

}
