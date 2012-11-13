package com.sap.globalit.plugins;

import java.io.IOException;

import com.sap.globalit.ConfigFile;

public interface DeployTask {
	/**
	 * Before deploy action, do some initialize work.
	 * @throws IOException
	 */
	public void init() throws IOException;
	/**
	 * Do the really deploy work.
	 * @throws IOException
	 */
	public void deploy() throws IOException;
	/**
	 * After deploy work, do some cleanup
	 * @throws IOException
	 */
	public void cleanup() throws IOException;
	
	public void setConfigFile(ConfigFile cfgFile);
	
	public void addListener(DeployTaskListener listener);
}
