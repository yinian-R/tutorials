package com.wymm.log4j.custom;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.LoggerContext;

public class LogAdapter {
    
    public static Log4jLog getLog(String name) {
        return new Log4jLog(name);
    }
    
    private static class Log4jLog implements Log {
        private final static String FQCN = LogAdapter.class.getSimpleName();
        private final static LoggerContext loggerContext = LogManager.getContext(LogAdapter.class.getClassLoader(), false);
        
        private final ExtendedLogger logger;
        
        public Log4jLog(String name) {
            this.logger = loggerContext.getLogger(name);
        }
        
        @Override
        public void info(Object message) {
            logger.logIfEnabled(FQCN, Level.INFO, null, (String) message);
        }
    }
}
