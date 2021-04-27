package com.csxy.core.util;


import org.apache.logging.log4j.LogManager;

public class Logger {

	private org.apache.logging.log4j.Logger logger;

	
	private Logger(org.apache.logging.log4j.Logger log4jLogger) {
		logger = log4jLogger;
	}

	public static Logger getLogger(Class classObject) {
		return new Logger(LogManager.getLogger(classObject));
	}

	public static Logger getLogger(String loggerName) {
		return new Logger(LogManager.getLogger(loggerName));
	}

	public void info(Object object) {
		logger.info(object);
	}

	public void error(Object object) {
		logger.error(object);
	}

	public void error(Object object, Throwable e) {
		logger.error(object, e);
	}

	public String getName() {
		return logger.getName();
	}
}