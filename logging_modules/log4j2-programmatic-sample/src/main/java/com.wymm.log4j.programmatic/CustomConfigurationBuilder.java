package com.wymm.log4j.programmatic;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

/**
 * 编程式配置Log4j
 */
public class CustomConfigurationBuilder {
	
	public static ConfigurationBuilder<BuiltConfiguration> configure() {
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
		
		// 配置 Appender
		AppenderComponentBuilder console = builder.newAppender("console", "Console");
		
		AppenderComponentBuilder file = builder.newAppender("file", "File");
		file.addAttribute("fileName", "./logs/log4j-programmatic.log");
		
		AppenderComponentBuilder rollingFile = builder.newAppender("rollingFile", "RollingFile");
		rollingFile.addAttribute("fileName", "./logs/rolling.log");
		rollingFile.addAttribute("filePattern", "./logs/rolling-%d{MM-dd-yy}-%i.log.gz");
		ComponentBuilder triggeringPolicies = builder.newComponent("Policies")
				.addComponent(builder.newComponent("CronTriggeringPolicy").addAttribute("schedule", "0 0 0 * * ?"))
				.addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "1M"));
		rollingFile.addComponent(triggeringPolicies);
		
		// 配置过滤器
		//FilterComponentBuilder markerFilter = builder.newFilter("MarkerFilter", Filter.Result.ACCEPT, Filter.Result.DENY);
		//markerFilter.addAttribute("marker", "FLOW");
		//console.add(markerFilter);
		
		// 配置布局
		LayoutComponentBuilder patternLayout = builder.newLayout("PatternLayout");
		patternLayout.addAttribute("pattern", "%d [%t] %-5level %20logger{1.}.%M:%L : %msg%n%throwable");
		console.add(patternLayout);
		file.add(patternLayout);
		rollingFile.add(patternLayout);
		
		
		builder.add(console);
		builder.add(file);
		builder.add(rollingFile);
		
		// 配置根记录器
		RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.INFO);
		rootLogger.add(builder.newAppenderRef("console"));
		rootLogger.add(builder.newAppenderRef("file"));
		rootLogger.add(builder.newAppenderRef("rollingFile"));
		builder.add(rootLogger);
		
		return builder;
	}
	
}
