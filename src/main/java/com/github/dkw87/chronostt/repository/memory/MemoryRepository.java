package com.github.dkw87.chronostt.repository.memory;

import com.github.dkw87.chronostt.model.DayEntry;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.model.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MemoryRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MemoryRepository.class);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private volatile Settings settings;
    private volatile List<Project> projects;
    private volatile DayEntry today;
    private volatile List<DayEntry> allDays;

    public static MemoryRepository getInstance() {
        return MemoryRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final MemoryRepository INSTANCE = new MemoryRepository();
    }
    
}
