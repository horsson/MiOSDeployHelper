package com.sap.globalit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.kamranzafar.jtar.TarEntry;
import org.kamranzafar.jtar.TarOutputStream;

public class Utils {

	private Utils() {

	}

	public static void zipFolder(File srcFolder, File destZipFile)
			throws IOException {
		zipFolder(srcFolder.getCanonicalPath(), destZipFile.getCanonicalPath());
	}

	public static void zipFolder(String srcFolder, String destZipFile)
			throws IOException {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;
		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);
		addFolderToZip("", srcFolder, zip);
		zip.flush();
		zip.close();
	}

	private static void addFileToZip(String path, String srcFile,
			ZipOutputStream zip) throws IOException {

		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
			in.close();
		}
	}

	private static void addFolderToZip(String path, String srcFolder,
			ZipOutputStream zip) throws IOException {
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/"
						+ fileName, zip);
			}
		}
	}

	/**
	 * Copy one file from source to destination.
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File destFile)
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
	 * Create a Tar file with file list
	 * 
	 * @param tarFile
	 * @param fileList
	 * @throws IOException
	 */
	public static void createTarFile(File tarFile, ArrayList<File> fileList)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(tarFile);
		TarOutputStream tos = new TarOutputStream(new BufferedOutputStream(fos));

		for (File aFile : fileList) {
			tos.putNextEntry(new TarEntry(aFile, aFile.getName()));
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(aFile));
			int count;
			byte[] data = new byte[2048];
			while ((count = bis.read(data)) != -1) {
				tos.write(data, 0, count);
			}
			tos.flush();
			bis.close();
		}
		tos.close();
	}
}
