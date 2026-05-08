package com.github.dkw87.chronostt.service;

import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.model.Settings;
import com.github.dkw87.chronostt.repository.memory.MemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
        MemoryRepository.getInstance().submitProjects(projects);
    }

    public static ProjectsService getInstance() {
        return ProjectsService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ProjectsService INSTANCE = new ProjectsService();
    }
    
}
