package com.sap.globalit.plugins;

public interface DeployTaskListener {

	public void onDeployStart(String message, Object context);
	public void onDeployDone(String message, Object context);
	public void onMessage(String message, Object context);
	public void onError(String message, Object context);
}
