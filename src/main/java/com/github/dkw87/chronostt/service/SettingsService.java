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

    public void storeDaysWeek(int daysWeek) {
        Settings settings = MemoryRepository.getInstance().getSettings();
        settings.setDaysWeek(daysWeek);
        MemoryRepository.getInstance().submitSettings(settings);
    }

    public int getHoursDaily() {
        return MemoryRepository.getInstance().getSettings().getHoursDaily();
    }

    public void storeHoursDaily(int hoursDaily) {
        Settings settings = MemoryRepository.getInstance().getSettings();
        settings.setHoursDaily(hoursDaily);
        MemoryRepository.getInstance().submitSettings(settings);
    }

    public TimeScale getTimeScale() {
        return MemoryRepository.getInstance().getSettings().getTimeScale();
    }

    public void storeTimeScale(TimeScale timeScale) {
        Settings settings = MemoryRepository.getInstance().getSettings();
        settings.setTimeScale(timeScale);
        MemoryRepository.getInstance().submitSettings(settings);
    }

    public boolean getNotifyOvertime() {
        return MemoryRepository.getInstance().getSettings().getNotifyOvertime();
    }

    public void storeNotifyOvertime(boolean notifyOvertime) {
        Settings settings = MemoryRepository.getInstance().getSettings();
        settings.setNotifyOvertime(notifyOvertime);
        MemoryRepository.getInstance().submitSettings(settings);
    }

    public boolean getAggragateProjectHours() {
        return MemoryRepository.getInstance().getSettings().getAggregateProjectHours();
    }

    public void storeAggragateProjectHours(boolean aggragateProjectHours) {
        Settings settings = MemoryRepository.getInstance().getSettings();
        settings.setAggregateProjectHours(aggragateProjectHours);
        MemoryRepository.getInstance().submitSettings(settings);
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
