package com.github.dkw87.chronostt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataService.class);

    public static DataService getInstance() {
        return DataService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DataService INSTANCE = new DataService();
    }
    
}
