package com.sap.globalit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PomFile {

	private String modelVersion;
	private String groupId;
	private String artifacId;
	private String version;
	private String packaging;
	
	public static final String DEFAULT_POM_FILE_NAME = "mypom.xml";

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifacId() {
		return artifacId;
	}

	public void setArtifacId(String artifacId) {
		this.artifacId = artifacId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getXML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<project>").append("<modelVersion>")
				.append(this.modelVersion).append("</modelVersion>")
				.append("<groupId>").append(this.groupId).append("</groupId>")
				.append("<artifactId>").append(this.artifacId)
				.append("</artifactId>").append("<version>")
				.append(this.version).append("</version>")
				.append("<packaging>").append(this.packaging)
				.append("</packaging>").append("</project>");
		return sb.toString();
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(this.getXML().getBytes());
		fos.flush();
		fos.close();
	}
}
