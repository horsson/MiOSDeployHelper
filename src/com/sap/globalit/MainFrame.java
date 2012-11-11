package com.sap.globalit;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sap.globalit.plugins.DeployManager;
import com.sap.globalit.plugins.DeployTask;
import com.sap.globalit.plugins.ThirdPartyLibDeployTask;

public class MainFrame extends JFrame implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -416443540029364237L;
	private JPanel contentPane;
	private JTextField txtRootFolder;
	private JTextField txtGroupId;
	private JTextField txtVersion;
	private JTextField txtArtifactId;
	private JTextField txtHeaderFolder;
	private JTextField txtDI;
	private JTextField txtRS;
	private JTextField txtRI;
	private JTextField txtDS;
	private JTextField txtResourceFolder;
	private JTextArea txtReadme;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 509, 367);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 18, 495, 278);
		contentPane.add(tabbedPane);
		
		JPanel panelGeneral = new JPanel();
		tabbedPane.addTab("General", null, panelGeneral, null);
		panelGeneral.setLayout(null);
		
		JLabel lblRootFolder = new JLabel("Root Folder");
		lblRootFolder.setBounds(21, 17, 88, 16);
		panelGeneral.add(lblRootFolder);
		
		txtRootFolder = new JTextField();
		txtRootFolder.addMouseListener(this);
		
		txtRootFolder.setBounds(103, 11, 347, 28);
		panelGeneral.add(txtRootFolder);
		txtRootFolder.setColumns(10);
		
		JLabel lblGroupId = new JLabel("Group ID");
		lblGroupId.setBounds(21, 57, 74, 16);
		panelGeneral.add(lblGroupId);
		
		txtGroupId = new JTextField();
		txtGroupId.setColumns(10);
		txtGroupId.setBounds(103, 51, 149, 28);
		panelGeneral.add(txtGroupId);
		
		JLabel lblArtifactId = new JLabel("Artifact ID");
		lblArtifactId.setBounds(21, 91, 74, 16);
		panelGeneral.add(lblArtifactId);
		
		txtVersion = new JTextField();
		txtVersion.setColumns(10);
		txtVersion.setBounds(325, 51, 123, 28);
		panelGeneral.add(txtVersion);
		
		JLabel lblVersion = new JLabel("Version");
		lblVersion.setBounds(264, 57, 88, 16);
		panelGeneral.add(lblVersion);
		
		txtArtifactId = new JTextField();
		txtArtifactId.setColumns(10);
		txtArtifactId.setBounds(103, 85, 149, 28);
		panelGeneral.add(txtArtifactId);
		
		JPanel panelFiles = new JPanel();
		tabbedPane.addTab("Files", null, panelFiles, null);
		panelFiles.setLayout(null);
		
		JLabel lblHeaderFolder = new JLabel("Header Folder");
		lblHeaderFolder.setBounds(6, 16, 88, 16);
		panelFiles.add(lblHeaderFolder);
		
		txtHeaderFolder = new JTextField();
		txtHeaderFolder.addMouseListener(this);
		txtHeaderFolder.setColumns(10);
		txtHeaderFolder.setBounds(106, 10, 295, 28);
		panelFiles.add(txtHeaderFolder);
		
		JLabel label = new JLabel("Debug-Simulator");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		label.setBounds(6, 44, 122, 16);
		panelFiles.add(label);
		
		JLabel label_1 = new JLabel("Debug-iphoneos");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		label_1.setBounds(6, 101, 122, 16);
		panelFiles.add(label_1);
		
		txtDI = new JTextField();
		txtDI.addMouseListener(this);
		txtDI.setColumns(10);
		txtDI.setBounds(106, 94, 295, 28);
		panelFiles.add(txtDI);
		
		JLabel label_2 = new JLabel("Release-Simulator");
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		label_2.setBounds(6, 135, 122, 16);
		panelFiles.add(label_2);
		
		txtRS = new JTextField();
		txtRS.setColumns(10);
		txtRS.setBounds(106, 128, 295, 28);
		txtRS.addMouseListener(this);
		panelFiles.add(txtRS);
		
		JLabel label_3 = new JLabel("Release-iphoneos");
		label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		label_3.setBounds(6, 169, 122, 16);
		panelFiles.add(label_3);
		
		txtRI = new JTextField();
		txtRI.setColumns(10);
		txtRI.setBounds(106, 162, 295, 28);
		txtRI.addMouseListener(this);
		panelFiles.add(txtRI);
		
		txtDS = new JTextField();
		txtDS.addMouseListener(this);
		txtDS.setColumns(10);
		txtDS.setBounds(106, 44, 295, 28);
		panelFiles.add(txtDS);
		
		JLabel lblResourceFolder = new JLabel("Resource Folder");
		lblResourceFolder.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblResourceFolder.setBounds(6, 204, 88, 16);
		panelFiles.add(lblResourceFolder);
		
		txtResourceFolder = new JTextField();
		txtResourceFolder.setColumns(10);
		txtResourceFolder.setBounds(106, 198, 295, 28);
		txtResourceFolder.addMouseListener(this);
		panelFiles.add(txtResourceFolder);
		
		final JCheckBox ckbSameFolder = new JCheckBox("Same Lib");
		ckbSameFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				boolean ckbState = !ckbSameFolder.isSelected();
				String folderPath = txtDS.getText();
				txtDI.setEnabled(ckbState);
				txtRS.setEnabled(ckbState);
				txtRI.setEnabled(ckbState);
				if (ckbState)
				{
					folderPath = "";
				}
				txtDI.setText(folderPath);
				txtRS.setText(folderPath);
				txtRI.setText(folderPath);
			}
		});
		ckbSameFolder.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		ckbSameFolder.setBounds(0, 72, 128, 23);
		panelFiles.add(ckbSameFolder);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("ReadMe", null, panel, null);
		panel.setLayout(null);
		
		txtReadme = new JTextArea();
		txtReadme.setBounds(6, 6, 462, 220);
		panel.add(txtReadme);
		
		JButton btnDeploy = new JButton("Deploy");
		btnDeploy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ConfigFile cfgFile = getConfigFileFromUI();
				DeployManager deployManager = new DeployManager();
				DeployTask deployTask = new ThirdPartyLibDeployTask();
				deployManager.setDeplyTask(deployTask);
				deployTask.setConfigFile(cfgFile);
				try {
					deployManager.deploy();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnDeploy.setBounds(370, 308, 117, 29);
		contentPane.add(btnDeploy);
		
		JButton btnLoadConfig = new JButton("Load Config");
		btnLoadConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				FileDialog fd = new FileDialog(MainFrame.this);
				fd.setVisible(true);
				if (fd.getFile() == null)
				{
					return;
				}
				
				String filePath = String.format("%s%s", fd.getDirectory(),fd.getFile());
				ConfigFile cfgFile = ConfigFile.loadFromFile(filePath);
				if (cfgFile == null)
				{
					return;
				}
				
				setUIFromConfigFile(cfgFile);
				
			}


		});
		btnLoadConfig.setBounds(241, 308, 117, 29);
		contentPane.add(btnLoadConfig);
		
		JButton btnSaveConfig = new JButton("Save Config");
		btnSaveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				FileDialog fd = new FileDialog(MainFrame.this);
				fd.setMode(FileDialog.SAVE);
				fd.setVisible(true);
				if (fd.getFile() != null)
				{
					String filePath = String.format("%s%s", fd.getDirectory(),fd.getFile());
					ConfigFile cfgFile = getConfigFileFromUI();
					cfgFile.saveToFile(filePath);
				}
			}
		});
		btnSaveConfig.setBounds(112, 308, 117, 29);
		contentPane.add(btnSaveConfig);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() != 2)
			return;
		JTextField textField = (JTextField) event.getSource();
		if (textField == txtRootFolder || textField == txtHeaderFolder)
			System.setProperty("apple.awt.fileDialogForDirectories", "true");
		else
			System.setProperty("apple.awt.fileDialogForDirectories", "false");
		FileDialog dialog = new FileDialog(this);
		dialog.setVisible(true);
		if (dialog.getDirectory() != null && dialog.getFile()!=null)
		{
			String folderPath = String.format("%s%s", dialog.getDirectory(), dialog.getFile());
			textField.setText(folderPath);
		}
		System.setProperty("apple.awt.fileDialogForDirectories", "false");
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		
	}
	
	
	private ConfigFile getConfigFileFromUI() {
		ConfigFile cfgFile =new ConfigFile();
		cfgFile.artifacId = txtArtifactId.getText();
		cfgFile.debugOsFolder = txtDI.getText();
		cfgFile.debugSimFolder = txtDS.getText();
		cfgFile.groupId = txtGroupId.getText();
		cfgFile.headerFolder = txtHeaderFolder.getText();
		cfgFile.readme = txtReadme.getText();
		cfgFile.releaseOsFolder = txtRI.getText();
		cfgFile.releaseSimFolder = txtRS.getText();
		cfgFile.resourceFolder = txtResourceFolder.getText();
		cfgFile.rootFolder = txtRootFolder.getText();
		cfgFile.version = txtVersion.getText();
		return cfgFile;
	}
	
	private void setUIFromConfigFile(ConfigFile cfgFile) {
		txtRootFolder.setText(cfgFile.rootFolder);
		txtGroupId.setText(cfgFile.groupId);
		txtVersion.setText(cfgFile.version);
		txtArtifactId.setText(cfgFile.artifacId);
		txtHeaderFolder.setText(cfgFile.headerFolder);
		txtDS.setText(cfgFile.debugSimFolder);
		txtDI.setText(cfgFile.debugOsFolder);
		txtRI.setText(cfgFile.releaseOsFolder);
		txtRS.setText(cfgFile.releaseSimFolder);
		txtResourceFolder.setText(cfgFile.resourceFolder);
		txtReadme.setText(cfgFile.readme);
	}
}
