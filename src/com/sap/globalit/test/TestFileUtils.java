package com.sap.globalit.test;

import java.io.File;

import org.junit.Test;

import com.sap.globalit.Utils;

public class TestFileUtils {

	
	@Test
	public void testZipFile() throws Exception
	{
		File dir = new File("/Users/d058856/Desktop/test/iAnnotate-2.2.1-SNAPSHOT-iAnnotate.bundle");
		File zipFile = new File("/Users/d058856/Desktop/test/iAnnotate-2.2.1-SNAPSHOT-iAnnotate.bundle");
		Utils.zipFolder(dir, zipFile);
	}

}
