package com.wymm._14chain_of_responsibility;

public class DemoMain {
    public static void main(String[] args) {
        AbstractLogger chainLogger = getCainLogger();
        
        chainLogger.logMessage(AbstractLogger.INFO, "info message");
        System.out.println();
        chainLogger.logMessage(AbstractLogger.DEBUG, "debug message");
        System.out.println();
        chainLogger.logMessage(AbstractLogger.ERROR, "error message");
    }
    
    public static AbstractLogger getCainLogger() {
        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);
        
        errorLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(consoleLogger);
        
        return errorLogger;
    }
}
