package sh.tinywifi.quietlogs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogSuppression {
    private static final Logger LOGGER = LogManager.getLogger();
    private static int suppressedCount = 0;
    
    public static void incrementSuppressed() {
        suppressedCount++;
    }
    
    public static void printSuppressedCount() {
        if (suppressedCount == 0) {
            LOGGER.info("QuietLogs: No log entries have been suppressed yet");
        } else {
            LOGGER.info("QuietLogs: {} log entries have been suppressed", suppressedCount);
        }
    }
}