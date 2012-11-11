package com.sap.globalit;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Setting extends Properties {

	private static final long serialVersionUID = 1L;
	volatile private static Setting INSTANCE = new Setting();
	
	private Setting()
	{
		super();
		try {
			this.loadFromXML(getClass().getResourceAsStream("Setting.xml"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Setting getInstance()
	{
		return INSTANCE;
	}
	
		
}
