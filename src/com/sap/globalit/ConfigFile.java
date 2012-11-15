package com.sap.globalit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ConfigFile {

	private static ConfigFile instance;
	public String rootFolder = "";
	public String groupId = "";
	public String version= "";
	public String artifacId= "";
	public String headerFolder= "";
	public String debugSimFolder= "";
	public String releaseSimFolder= "";
	public String debugOsFolder= "";
	public String releaseOsFolder= "";
	public String resourceFolder= "";
	public String readme= "";
	public String classifier = "";

	
	public void saveToFile(String filePath) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>").append("<Config>");
		Field[] fields = ConfigFile.class.getFields();
		for (Field field : fields) {
			sb.append(String.format("<%s>", field.getName()));
			try {
				Object obj = field.get(this);
				if (obj != null)
					sb.append(obj);
				else
					sb.append("");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			sb.append(String.format("</%s>", field.getName()));
		}
		sb.append("</Config>");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			fos.write(sb.toString().getBytes());
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	private String cfgFileName ;
	
	public ConfigFile()
	{
		
	}
	private ConfigFile(String file) {
		this.cfgFileName = file;

	}

	private void startParse()
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser parser = spf.newSAXParser();
			File aFile = new File(cfgFileName);
			parser.parse(aFile, new XmlHanlder());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static ConfigFile getInstance() {
		return instance;
	}

	public static ConfigFile loadFromFile(String file) {
		instance = new ConfigFile(file);
		instance.startParse();
		return instance;
	}

	private class XmlHanlder extends DefaultHandler {

		private String currentValue;
		
		public void characters(char[] ch, int start, int length)
				throws SAXException

		{
			currentValue = new String(ch, start, length);
		}

		@Override
		public void startElement(String arg0, String arg1, String arg2,
				Attributes arg3) throws SAXException {
			currentValue = "";
		}
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			try {
				Field aField = ConfigFile.class.getField(qName);
				aField.set(instance, currentValue);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				//Do nothing!
			}
		}
	}
}
