package com.github.dkw87.chronostt.service;

import com.github.dkw87.chronostt.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrackingService {

    private static final Logger LOG = LoggerFactory.getLogger(TrackingService.class);

    public static TrackingService getInstance() {
        return TrackingService.SingletonHolder.INSTANCE;
    }

    public void startTracking(Project project) {
    }

    public void stopTracking() {
    }

    private static class SingletonHolder {
        private static final TrackingService INSTANCE = new TrackingService();
    }
    
}
