package com.sap.globalit.plugins;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PluginManager {
	
	private Map<String, Plugin> plugins = new HashMap<String, Plugin>();
	volatile private static PluginManager instance ;
	private static Logger logger = Logger.getLogger(PluginManager.class);
	
	
	public static PluginManager getInstance()
	{
		if (instance == null)
			instance = new PluginManager();
		return instance;
	}
	
	
	
	public List<Plugin> getPlugins()
	{
		List<Plugin> result = new ArrayList<Plugin>();
		result.addAll(plugins.values());
		Collections.sort(result,new Comparator<Plugin>() {
		    public int compare(Plugin a, Plugin b) {
		    	return a.getId().compareTo(b.getId());
		    }
		});
		return result;
	}
	
	public Plugin getPlugin(String id)
	{
		return plugins.get(id);
	}
	
	
	public Plugin setCurrentPlugin(String id)
	{
		Plugin oldItem = null;
		for (Entry<String, Plugin> aEntry : plugins.entrySet()) {
			if (aEntry.getValue().isSelected())
				oldItem = aEntry.getValue();
			aEntry.getValue().setSelected(false);
		}
		Plugin aPlugin = plugins.get(id);
		if (aPlugin != null)
			aPlugin.setSelected(true);
		return oldItem;
	}
	
	
	public Plugin getCurrentPlugin()
	{
		for (Entry<String, Plugin> aEntry : plugins.entrySet()) {
			if (aEntry.getValue().isSelected())
				return aEntry.getValue();
		}
		return null;
	}
	
	private PluginManager()
	{
		InputStream is = this.getClass().getResourceAsStream("plugins.xml");
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(is, new ContentHandler());
		} catch (ParserConfigurationException e) {
			logger.error("Cannot config the saxparser", e);
		} catch (SAXException e) {
			logger.error("SAXException",e);
		} catch (IOException e) {
			logger.error("Cannot parse plugin.xml", e);
		}
	}
	
	
	
	class ContentHandler extends DefaultHandler
	{
		private String content;
		private Plugin plugin;
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if ("item".equals(qName))
			{
				plugin = new Plugin();
				plugin.setId(attributes.getValue("id"));
				String isSelected =attributes.getValue("selected");
				plugin.setSelected(Boolean.valueOf(isSelected));
			}
			
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			content = new String(ch, start, length);
		}
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if ("class".equals(qName))
			{
				plugin.setClazz(content);
			}
			else if ("item".equals(qName))
			{
				plugins.put(plugin.getId(), plugin);
			}
		}
		
	}
}
