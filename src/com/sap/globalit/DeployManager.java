package com.sap.globalit;

import java.io.IOException;

import com.sap.globalit.plugins.DeployTask;


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
