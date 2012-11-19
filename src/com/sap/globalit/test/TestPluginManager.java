package com.sap.globalit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sap.globalit.plugins.Plugin;
import com.sap.globalit.plugins.PluginManager;

public class TestPluginManager {

	private PluginManager manager;
	@Before
	public void init()
	{
		manager = PluginManager.getInstance();
	}
	
	@Test
	public void testInit()
	{
		Assert.assertNotNull(manager);
	}
	
	@Test
	public void testGetPlugin()
	{
		Assert.assertNotNull(manager.getPlugin("3rdparty"));
		Assert.assertNull(manager.getPlugin("NoPlugin"));
	}
	
	@Test
	public void testGetPlugins()
	{
		Assert.assertEquals(1, manager.getPlugins().size());				
	}
	
	@Test
	public void testPluginResult()
	{
		Plugin plugin = manager.getPlugin("3rdparty");		
		Assert.assertEquals("3rdparty", plugin.getId());
		Assert.assertEquals("com.sap.global.plugins.ThirdPartyLibDeployTask", plugin.getClazz());
	}
}
