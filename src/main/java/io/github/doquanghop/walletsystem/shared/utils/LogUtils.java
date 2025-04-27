package io.github.doquanghop.walletsystem.shared.utils;

import org.slf4j.Logger;

public class LogUtils {

    public static void log(Logger logger, String level, String message, Object... args) {

        switch (level.toUpperCase()) {
            case "DEBUG":
                logger.debug(message, args);
                break;
            case "WARN":
                logger.warn(message, args);
                break;
            case "ERROR":
                logger.error(message, args);
                break;
            case "INFO":
            default:
                logger.info(message, args);
                break;
        }
    }
}