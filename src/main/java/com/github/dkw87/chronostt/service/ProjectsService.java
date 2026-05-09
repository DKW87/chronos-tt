package com.github.dkw87.chronostt.service;

import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.repository.memory.MemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class ProjectsService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectsService.class);
    private static final long NO_ID = -1;

    public List<Project> getProjects() {
        return MemoryRepository.getInstance().getProjects();
    }

    public void storeProjects(List<Project> projects) {
        projects.forEach(project -> {
            if (project.getId() == NO_ID) {
                project.setId(MemoryRepository.getInstance().getIncrementedProjectId());
            }
        });

        final Project storedTracked = SettingsService.getInstance().getLastTrackedProject();
        final Project newTracked = projects.stream()
                .filter(project -> Objects.equals(project.getId(), storedTracked.getId())).findFirst().get();

        if (storedTracked != newTracked) {
            SettingsService.getInstance().storeLastTrackedProject(newTracked);
        }

        MemoryRepository.getInstance().submitProjects(projects);
    }

    public static ProjectsService getInstance() {
        return ProjectsService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ProjectsService INSTANCE = new ProjectsService();
    }
    
}
