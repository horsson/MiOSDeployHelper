package com.sap.globalit.plugins;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;

import com.sap.globalit.ConfigFile;
import com.sap.globalit.Utils;
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

	private DeployTaskListener listener;

	/**
	 * A Tar file.
	 */
	private File tarFile;

	private String getArtifactIdAndVersion() {
		return String.format("%s-%s", cfgFile.artifacId, cfgFile.version);
	}

	private String getBundlesFileContent() {
		String result = String
				.format("%s:%s:%s:%s:%s", cfgFile.groupId, cfgFile.artifacId,
						"bundle", cfgFile.artifacId, cfgFile.version);
		return result;
	}

	/**
	 * Create a Root folder where all files are stored.
	 * 
	 * @return true successfully, false fail.
	 * @throws IOException
	 */
	private boolean createRootFolder() throws IOException {
		this.rootFolder = new File(cfgFile.rootFolder);
		if (rootFolder.exists()) {
			FileUtils.deleteDirectory(rootFolder);
		}

		return rootFolder.mkdir();
	}

	/**
	 * Create a new file
	 * 
	 * @param file
	 *            The file to be created
	 * @param content
	 *            The content to be written in file.
	 * @throws IOException
	 */
	private void createFile(File file, String content) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(content.getBytes());
		fos.flush();
		fos.close();
	}

	/**
	 * Create Header files
	 * 
	 * @param folderPath
	 * @param version
	 *            Deubg or Release
	 * @param target
	 *            ios or simulator
	 * @throws IOException
	 */
	private void createHeaderFile(String folderPath, String version,
			String target) throws IOException {
		String stringPattern = "%s-%s-%s-%s.headers.tar";

		ArrayList<File> releaseOsFileList = this.getFileList(folderPath);
		String releaseOsFileName = String.format(stringPattern,
				cfgFile.artifacId, cfgFile.version, version, target);
		File headerTagFile = new File(rootFolder, releaseOsFileName);
		createdFileList.add(headerTagFile);
		Utils.createTarFile(headerTagFile, releaseOsFileList);
	}

	/**
	 * <artifact-id>-<version>-Debug-iphonesimulator.a
	 * 
	 * @param filePath
	 * @param version
	 * @param target
	 * @throws IOException
	 */
	private void createLibFile(String filePath, String version, String target)
			throws IOException {
		String stringPattern = "%s-%s-%s-%s.a";
		File srcFile = new File(filePath);
		File destFile = new File(rootFolder, String.format(stringPattern,
				cfgFile.artifacId, cfgFile.version, version, target));
		createdFileList.add(destFile);
		Utils.copyFile(srcFile, destFile);

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
		pomFile.setVersion(cfgFile.version);
		pomFile.setPackaging("xcode-lib");
		File file = new File(rootFolder, PomFile.DEFAULT_POM_FILE_NAME);
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
		Utils.createTarFile(tarFile, fileList);
	}

	private ArrayList<File> getFileList(String folder) {
		ArrayList<File> fileList = new ArrayList<File>();
		File fileFolder = new File(folder);
		File[] allFiles = fileFolder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() ? true : false;
			}
		});

		for (File file : allFiles) {
			fileList.add(file);
		}
		return fileList;
	}

	/**
	 * <artifact-id>-<version>-Release-iphonesimulator.headers.tar Create the
	 * Headers file.
	 * 
	 * @throws IOException
	 */
	private void createHeaderFiles() throws IOException {
		createHeaderFile(cfgFile.headerFolder, "Release", "iphonesimulator");
		createHeaderFile(cfgFile.headerFolder, "Release", "iphoneos");
		createHeaderFile(cfgFile.headerFolder, "Debug", "iphoneos");
		createHeaderFile(cfgFile.headerFolder, "Debug", "iphonesimulator");
	}

	/**
	 * Create .a files (4)
	 * 
	 * @throws IOException
	 */
	private void createLibFiles() throws IOException {
		createLibFile(cfgFile.releaseSimFolder, "Release", "iphonesimulator");
		createLibFile(cfgFile.releaseOsFolder, "Release", "iphoneos");
		createLibFile(cfgFile.debugOsFolder, "Debug", "iphoneos");
		createLibFile(cfgFile.debugSimFolder, "Debug", "iphonesimulator");
	}

	private void createBundleResource() throws IOException {
		File bundleFileFolder = new File(rootFolder, String.format(
				"%s-%s-%s.bundle", cfgFile.artifacId, cfgFile.version,
				cfgFile.classifier));
		File bundleFileName = new File(rootFolder, String.format(
				"%s-%s-%s.bundle.zip", cfgFile.artifacId, cfgFile.version,
				cfgFile.classifier));
		File resourceDir = new File(cfgFile.resourceFolder);
		FileUtils.copyDirectory(resourceDir, bundleFileFolder);
		Utils.zipFolder(bundleFileFolder, bundleFileName);
		FileUtils.deleteDirectory(bundleFileFolder);
		bundleFileName.renameTo(bundleFileFolder);
		createdFileList.add(bundleFileFolder);
	}
	
	

	@Override
	public void setConfigFile(ConfigFile cfgFile) {
		this.cfgFile = cfgFile;
	}

	public void deploy() throws IOException {
		// this.createRootFolder();
		// this.createPOM();
		// this.createTar();
		// this.createHeaderFiles();
		// this.createLibFiles();
		// this.createBundleResource();
		// MavenBuildCommand mbc = new MavenBuildCommand(cfgFile,
		// createdFileList, tarFile);
		new Thread(new DeployTask()).start();
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

	@Override
	public void addListener(DeployTaskListener listener) {
		this.listener = listener;
	}

	private void fireMessageEvent(final String message) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (listener != null)
					listener.onMessage(message, null);

			}
		});

	}

	private void fireErrorEvent(final String message) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (listener != null)
					listener.onError(message, null);

			}
		});
	}

	private class DeployTask implements Runnable {

		@Override
		public void run() {
			try {
				createRootFolder();
				createPOM();
				fireMessageEvent("Creating Tar files...");
				createTar();
				fireMessageEvent("Creating Header files...");
				createHeaderFiles();
				fireMessageEvent("Creating Lib files...");
				createLibFiles();
				fireMessageEvent("Creating Resource files...");
				createBundleResource();
				MavenBuildCommand mbc = new MavenBuildCommand(cfgFile,
						createdFileList, tarFile);
				
				
				ProcessBuilder pb = new ProcessBuilder(mbc.getCmd());
				pb.redirectErrorStream(true);
				pb.directory(rootFolder);
				
				Process process = pb.start();
				BufferedInputStream bis = new BufferedInputStream(process.getInputStream());
				
				byte[] buffer = new byte[512];
				int count = -1;
				
				while ((count = bis.read(buffer)) != -1)
				{
					String msg = new String(buffer, 0, count);
					fireMessageEvent(msg);
					System.out.println(msg);
				}
				int code = process.waitFor();
				System.out.println("Done ! "+code);
				
				
			} catch (IOException ex) {
				ex.printStackTrace();
				fireErrorEvent(ex.getMessage());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
