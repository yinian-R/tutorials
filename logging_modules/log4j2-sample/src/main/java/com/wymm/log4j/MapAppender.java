package com.wymm.log4j;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.Serializable;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 自定义Appender
 */
@Plugin(
		name = "MapAppender",
		category = Core.CATEGORY_NAME,
		elementType = Appender.ELEMENT_TYPE
)
public class MapAppender extends AbstractAppender {
	
	private ConcurrentMap<String, LogEvent> eventMap = new ConcurrentHashMap<>();
	
	protected MapAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
		super(name, filter, layout, ignoreExceptions, properties);
	}
	
	@Override
	public void append(LogEvent event) {
		eventMap.put(Instant.now().toString(), event);
		System.out.println("custom appender:~~~~~~~~~~~~~~~~");
	}
	
	@PluginFactory
	public static MapAppender createAppender(
			@PluginAttribute("name") String name,
			@PluginElement("Filter") Filter filter) {
		return new MapAppender(name, filter, null, false, null);
	}
}
