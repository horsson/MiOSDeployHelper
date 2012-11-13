package com.sap.globalit.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Test;

public class TestFileUtils {

	
	@Test
	public void testZipFile() throws Exception
	{
		//ProcessBuilder pb = new ProcessBuilder("ls","-al");
		//Process process = pb.start();
		
		Runtime rt = Runtime.getRuntime();
		Process process  = rt.exec("ls -al");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;
		while ((line = br.readLine())!= null)
		{
			System.out.println(line);
		}
		
		process.waitFor();
	}

}
