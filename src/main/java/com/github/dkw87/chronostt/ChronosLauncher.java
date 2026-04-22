package com.github.dkw87.chronostt;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChronosLauncher {

    private static final Logger LOG = LoggerFactory.getLogger(ChronosLauncher.class);

    public static void main(String[] args) {
        LOG.info("Chronos-TT starting...");
        Application.launch(ChronosApplication.class, args);
    }

}
