package com.sap.globalit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MavenBuildCommand {
	
	private ConfigFile cfgFile;
	private List<File> files;
	private File tarFile;
	private Setting setting;
	public static final String FILES_KEY = 			"filesKey";
	public static final String TYPES_KEY = 			"TypesKey";
	public static final String CLASSIFIERS_KEY = 	"classifiersKey";
	
	public String getCommandLine() {
		String filesNames = getFilesAndType().get(FILES_KEY);
		String types = getFilesAndType().get(TYPES_KEY);
		String classifier = getFilesAndType().get(CLASSIFIERS_KEY);
		StringBuilder sb = new StringBuilder();
		sb.append("mvn ").
		append(setting.getProperty("plugin","org.apache.maven.plugins:maven-deploy-plugin:2.7:deploy-file ")).
		append(" ").
		append("-DrepositoryId="+setting.getProperty("repositoryId","build.snapshots.ios ")).
		append(" ").
		append("-Durl="+setting.getProperty("url")). 
		append(" ").
		append("-Dfile="+tarFile.getName()).
		append(" ").
		append("-Dfiles="+filesNames).
		append(" ").
		append("-Dtypes="+types).
		append(" ").
		append("-DDclassifiers="+classifier).
		append(" ").
		append("-DartifactId="+ cfgFile.artifacId).
		append(" ").
		append("-DgroupId="+cfgFile.groupId).
		append(" ").
		append("-Dversion="+ cfgFile.version).
		append(" ").
		append("-Dpackaging=tar").
		append(" ").
		append("-DpomFile=pom.xml");
		return sb.toString();
	}
	
	public List<String> getCmd()
	{
		List<String> cmds = new ArrayList<String>();
		String filesNames = getFilesAndType().get(FILES_KEY);
		String types = getFilesAndType().get(TYPES_KEY);
		String classifier = getFilesAndType().get(CLASSIFIERS_KEY);
		cmds.add("mvn");
		cmds.add(setting.getProperty("plugin","org.apache.maven.plugins:maven-deploy-plugin:2.7:deploy-file"));
		cmds.add("-DrepositoryId="+setting.getProperty("repositoryId","build.snapshots.ios"));
		cmds.add("-Durl="+setting.getProperty("url")); 
		cmds.add("-Dfile="+tarFile.getName());
		cmds.add("-Dfiles="+filesNames);
		cmds.add("-Dtypes="+types);
		cmds.add("-DDclassifiers="+classifier);
		cmds.add("-DartifactId="+ cfgFile.artifacId);
		cmds.add("-DgroupId="+cfgFile.groupId);
		cmds.add("-Dversion="+ cfgFile.version);
		cmds.add("-Dpackaging=tar");
		cmds.add("-DpomFile=pom.xml");
		return cmds;
	}
	
	private Map<String, String>getFilesAndType()
	{
		Map<String,String> result = new HashMap<String, String>();
		StringBuilder fileNames = new StringBuilder();
		StringBuilder types = new StringBuilder();
		StringBuilder classifers = new StringBuilder();
		for (File aFile : this.files) {
			String aFileName = aFile.getName();
			System.out.println(aFileName);
			fileNames.append(aFileName).append(",");	
			String aType = aFileName.substring(aFileName.lastIndexOf(".")+1);
			types.append(aType).append(",");
			String aClassifier = null;
			if (aFileName.contains("Release"))
			{
				aClassifier = aFileName.substring(aFileName.indexOf("Release"), aFileName.lastIndexOf("."));
			}
			else if (aFileName.contains("Debug"))
			{
				aClassifier = aFileName.substring(aFileName.indexOf("Debug"), aFileName.lastIndexOf("."));
			}
			else
			{
				aClassifier = aFileName.substring(aFileName.indexOf(cfgFile.version)+cfgFile.version.length()+1, aFileName.lastIndexOf("."));
			}
			classifers.append(aClassifier);
		}
		
		result.put(FILES_KEY, fileNames.toString().substring(0,fileNames.toString().length()-1));
		result.put(TYPES_KEY, types.toString().substring(0,types.toString().length()-1));
		result.put(CLASSIFIERS_KEY, classifers.toString().substring(0,classifers.toString().length()-1));
		return result;
	}
	
	public MavenBuildCommand(ConfigFile cfgFile, List<File> files, File tarFile)
	{
		this.cfgFile = cfgFile;
		this.files = files;
		this.tarFile = tarFile;
		setting = Setting.getInstance();
	}
	
}
