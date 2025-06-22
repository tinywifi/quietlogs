package sh.tinywifi.quietlogs;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

public class LogFilter extends AbstractFilter {
    
    @Override
    public Result filter(LogEvent event) {
        if (event != null) {
            Message message = event.getMessage();
            if (message != null) {
                String formattedMessage = message.getFormattedMessage();
                if (shouldFilter(formattedMessage)) {
                    LogSuppression.incrementSuppressed();
                    return Result.DENY;
                }
            }
        }
        return Result.NEUTRAL;
    }
    
    private boolean shouldFilter(String message) {
        if (message == null) return false;
        
        return (message.contains("Context[") && message.contains("] updated from")) ||
               (message.contains("ContextHistory of") && message.contains("is overflowing?")) ||
               (message.contains("Unknown custom packet identifier") && message.contains("moremobvariants:main"));
    }
    
    @Override
    public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
        if (shouldFilter(msg)) {
            LogSuppression.incrementSuppressed();
            return Result.DENY;
        }
        return Result.NEUTRAL;
    }
    
    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        if (msg != null && shouldFilter(msg.toString())) {
            LogSuppression.incrementSuppressed();
            return Result.DENY;
        }
        return Result.NEUTRAL;
    }
    
    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        if (msg != null && shouldFilter(msg.getFormattedMessage())) {
            LogSuppression.incrementSuppressed();
            return Result.DENY;
        }
        return Result.NEUTRAL;
    }
}