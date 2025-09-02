package com.nhnacademy.hello;

import org.slf4j.LoggerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;


public class LoggingExample {

    private static final Logger logger = Logger.getLogger(LoggingExample.class.getName());

    public static void main(String[] args) {
        logger.info("INFO 로그 메시지");
        logger.warning("WARNING 로그 메시지");
        logger.log(Level.SEVERE, "SEVERE 로그 메시지");
    }
}
