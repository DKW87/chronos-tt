package com.github.dkw87.chronostt.service;

import com.github.dkw87.chronostt.enumeration.TimeScale;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.model.Settings;
import com.github.dkw87.chronostt.repository.memory.MemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsService {

    private static final Logger LOG = LoggerFactory.getLogger(SettingsService.class);


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

    public Project getLastSelectedProject() {
        return MemoryRepository.getInstance().getSettings().getLastSelectedProject();
    }

    public void storeLastSelectedProject(Project project) {
        Settings settings = MemoryRepository.getInstance().getSettings();
        settings.setLastSelectedProject(project);
        MemoryRepository.getInstance().submitSettings(settings);
    }

    public static SettingsService getInstance() {
        return SettingsService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final SettingsService INSTANCE = new SettingsService();
    }
    
}
