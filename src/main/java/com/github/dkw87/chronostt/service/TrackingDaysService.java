package com.github.dkw87.chronostt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrackingDaysService {

    private static final Logger LOG = LoggerFactory.getLogger(TrackingDaysService.class);

    public static TrackingDaysService getInstance() {
        return TrackingDaysService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final TrackingDaysService INSTANCE = new TrackingDaysService();
    }
    
}
