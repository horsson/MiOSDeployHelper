package com.sap.globalit.plugins;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.kamranzafar.jtar.TarEntry;
import org.kamranzafar.jtar.TarOutputStream;

import com.sap.globalit.ConfigFile;
import com.sap.globalit.MavenBuildCommand;
import com.sap.globalit.PomFile;

public class ThirdPartyLibDeployTask implements DeployTask {

	private ConfigFile cfgFile;
	private File rootFolder;
	/**
	 * Temp files list, all the files will be deleted.
	 * */
	private List<File> tempFileList = new ArrayList<File>();
	
	/**
	 * It stores the files that will be uploaded to the server.
	 */
	private List<File> createdFileList = new ArrayList<File>();

/**
 * A Tar file.
 */
	private File tarFile;
	private String getArtifactIdAndVersion() {
		return String.format("%s-%s", cfgFile.artifacId, cfgFile.version);
	}

	private String getBundlesFileContent() {
		String result = String.format("%s:%s:%s:%s:%s", cfgFile.groupId,cfgFile.artifacId,"bundle",cfgFile.artifacId,cfgFile.version);
		return result;
	}

	/**
	 * Create a Root folder where all files are stored.
	 * @return true successfully, false fail.
	 */
	private boolean createRootFolder() {
		this.rootFolder = new File(cfgFile.rootFolder);
		if (!rootFolder.exists()) {
			return rootFolder.mkdir();
		} else {
			return true;
		}
	}

	/**
	 * Create a new file
	 * @param file The file to be created
	 * @param content The content to be written in file.
	 * @throws IOException
	 */
	private void createFile(File file, String content) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(content.getBytes());
		fos.flush();
		fos.close();
	}

	/**
	 * Create a Tar file with file list
	 * @param tarFile
	 * @param fileList
	 * @throws IOException
	 */
	private void createTarFile(File tarFile, ArrayList<File> fileList) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(tarFile);
		TarOutputStream tos = new TarOutputStream(new BufferedOutputStream(fos));

		for (File aFile : fileList) {
			tos.putNextEntry(new TarEntry(aFile, aFile.getName()));
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(aFile));
			int count;
			byte[] data = new byte[2048];
			while ((count = bis.read(data))!= -1)
			{
				tos.write(data,0,count);
			}
			tos.flush();
			bis.close();
		}
		tos.close();
	}
	
	/**
	 * Copy one file from source to destination.
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 */
	private static void copyFile(File sourceFile, File destFile)
			throws IOException {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;
		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}

		}
	}

	/**
	 * Create Header files
	 * @param folderPath
	 * @param version Deubg or Release
	 * @param target ios or simulator
	 * @throws IOException
	 */
	private void createHeaderFile(String folderPath, String version, String target) throws IOException {
		String stringPattern = "%s-%s-%s-%s.headers.tar";
		
		ArrayList<File> releaseOsFileList = this.getFileList(folderPath);
		String releaseOsFileName = String.format(stringPattern, cfgFile.artifacId,cfgFile.version,version,target);
		File headerTagFile = new File(rootFolder,releaseOsFileName);
		createdFileList.add(headerTagFile);
		this.createTarFile(headerTagFile, releaseOsFileList);
	}
	
	/**
	 * <artifact-id>-<version>-Debug-iphonesimulator.a
	 * @param filePath
	 * @param version
	 * @param target
	 * @throws IOException 
	 */
	private void createLibFile(String filePath, String version,
			String target) throws IOException {
		String stringPattern = "%s-%s-%s-%s.a";
		File srcFile = new File(filePath);
		File destFile = new File(rootFolder,String.format(stringPattern, cfgFile.artifacId,cfgFile.version,version,target));
		createdFileList.add(destFile);
		copyFile(srcFile, destFile);
		
	}
	/**
	 * Create the POM file, the (1)
	 * 
	 * @throws IOException
	 */
	private void createPOM() throws IOException {
		PomFile pomFile = new PomFile();
		pomFile.setArtifacId(cfgFile.artifacId);
		pomFile.setGroupId(cfgFile.groupId);
		pomFile.setModelVersion("4.0.0");
		pomFile.setPackaging("xcode-lib");
		File file = new File(rootFolder, "pom.xml");
		pomFile.saveToFile(file);
	}

	/**
	 * (2)
	 * 
	 * @throws IOException
	 */
	private void createTar() throws IOException {
		// Create README.txt file.
		File readmeFile = new File(rootFolder, "README.txt");
		tempFileList.add(readmeFile);
		this.createFile(readmeFile, cfgFile.readme);
		// Create bundles.txt file.
		File bundlesFile = new File(rootFolder, "bundles.txt");
		tempFileList.add(bundlesFile);
		this.createFile(bundlesFile, this.getBundlesFileContent());

		ArrayList<File> fileList = new ArrayList<File>();
		fileList.add(readmeFile);
		fileList.add(bundlesFile);

		File tarFile = new File(rootFolder, this.getArtifactIdAndVersion()
				+ ".tar");
		this.tarFile = tarFile;
		this.createTarFile(tarFile, fileList);
	}
	
	private ArrayList<File> getFileList(String folder)
	{
		ArrayList<File> fileList = new ArrayList<File>();
		File fileFolder = new File(folder);
	    File[] allFiles = fileFolder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile()?true:false;
			}
		});
	    
	    for (File file : allFiles) {
			fileList.add(file);
		}
		return fileList;
	}
	
	/**
	 *  <artifact-id>-<version>-Release-iphonesimulator.headers.tar
	 * Create the Headers file.
	 * @throws IOException
	 */
	private void createHeaderFiles() throws IOException
	{
		createHeaderFile(cfgFile.headerFolder, "Release","iphonesimulator");
		createHeaderFile(cfgFile.headerFolder, "Release","iphoneos");
		createHeaderFile(cfgFile.headerFolder, "Debug", "iphoneos");
		createHeaderFile(cfgFile.headerFolder,"Debug","iphonesimulator");
	}
	
	
	/**
	 * Create .a files (4)
	 * @throws IOException
	 */
	private void createLibFiles() throws IOException
	{
		createLibFile(cfgFile.releaseSimFolder, "Release","iphonesimulator");
		createLibFile(cfgFile.releaseOsFolder, "Release","iphoneos");
		createLibFile(cfgFile.debugOsFolder, "Debug", "iphoneos");
		createLibFile(cfgFile.debugSimFolder,"Debug","iphonesimulator");
	}


	private void createBundleResource()
	{
		//TODO: Create BundleResource.
	}

	private String generateCmdLine()
	{
		StringBuilder sb = new StringBuilder();
		
		return sb.toString();
	}
	
	@Override
	public void setConfigFile(ConfigFile cfgFile)
	{
		this.cfgFile = cfgFile;
	}
	
	public void deploy() throws IOException
	{
		this.createRootFolder();
		this.createPOM();
		this.createTar();
		this.createHeaderFiles();
		this.createLibFiles();
		MavenBuildCommand mbc  = new MavenBuildCommand(cfgFile, createdFileList, tarFile);
		System.out.println(mbc.getCommandLine());
	}

	@Override
	public void init() throws IOException {
				
	}

	@Override
	public void cleanup() throws IOException {
		for (File aFile : tempFileList) {
			aFile.delete();
		}
	}

}
