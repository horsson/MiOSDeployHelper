package com.sap.globalit.plugins;

import java.io.IOException;



public class DeployManager {

	private DeployTask deployTask;
	
	public void setDeplyTask(DeployTask deployTask)
	{
		this.deployTask = deployTask;
	}
	
	public void deploy() throws IOException
	{
		deployTask.init();
		deployTask.deploy();
		deployTask.cleanup();
	}
}
