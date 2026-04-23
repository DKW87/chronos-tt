package com.github.dkw87.chronostt.service;

import com.github.dkw87.chronostt.enumeration.TimeScale;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.repository.memory.MemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataService.class);

    public List<Project> getProjects() {
        return MemoryRepository.getInstance().getProjects();
    }

    public int getDaysWeek() {
        return MemoryRepository.getInstance().getSettings().getDaysWeek();
    }

    public int getHoursDaily() {
        return MemoryRepository.getInstance().getSettings().getHoursDaily();
    }

    public TimeScale getTimeScale() {
        return MemoryRepository.getInstance().getSettings().getTimeScale();
    }

    public boolean getNotifyOvertime() {
        return MemoryRepository.getInstance().getSettings().getNotifyOvertime();
    }

    public boolean getAggragateProjectHours() {
        return MemoryRepository.getInstance().getSettings().getAggregateProjectHours();
    }

    public static DataService getInstance() {
        return DataService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DataService INSTANCE = new DataService();
    }
    
}
