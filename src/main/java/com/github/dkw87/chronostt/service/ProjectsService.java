package com.github.dkw87.chronostt.service;

import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.repository.memory.MemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProjectsService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectsService.class);

    public List<Project> getProjects() {
        return MemoryRepository.getInstance().getProjects();
    }

    public static ProjectsService getInstance() {
        return ProjectsService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ProjectsService INSTANCE = new ProjectsService();
    }
    
}
