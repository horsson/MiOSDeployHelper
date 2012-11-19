package com.sap.globalit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import com.apple.eawt.AboutHandler;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.AppEvent.PreferencesEvent;
import com.apple.eawt.Application;
import com.apple.eawt.PreferencesHandler;
import com.sap.globalit.plugins.DeployManager;
import com.sap.globalit.plugins.DeployTask;
import com.sap.globalit.plugins.DeployTaskListener;
import com.sap.globalit.plugins.Plugin;
import com.sap.globalit.plugins.PluginManager;
import com.sap.globalit.plugins.ThirdPartyLibDeployTask;

public class MiOSDeployHelper extends JFrame {

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
	private JTextField txtClassifier;
	private JTextArea txtReadme;

	private static Logger logger = Logger.getLogger(MiOSDeployHelper.class);

	/**
	 * Launch the application. See:
	 * https://developer.apple.com/library/mac/#documentation
	 * /Java/Reference/JavaSE6_AppleExtensionsRef/api/index.html
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					System.setProperty("apple.laf.useScreenMenuBar", "true");

					MiOSDeployHelper frame = new MiOSDeployHelper();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setMacOsFeatures() {
		Application app = Application.getApplication();
		app.setPreferencesHandler(new PreferencesHandler() {

			@Override
			public void handlePreferences(PreferencesEvent event) {
				// TODO Do it later.

			}
		});

		app.setAboutHandler(new AboutHandler() {

			@Override
			public void handleAbout(AboutEvent event) {

			}
		});

	}

	/**
	 * Create the frame.
	 */
	public MiOSDeployHelper() {
		setMacOsFeatures();
		MouseHandler mouseHandler = new MouseHandler();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 509, 367);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuPlugins = new JMenu("Plugins");
		menuBar.add(menuPlugins);

		createMenuItems(menuPlugins);

		JMenuItem loadMore = new JMenuItem("Load more...");
		menuPlugins.add(loadMore);

		JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);

		JMenuItem menuWiki = new JMenuItem("MiOS Wiki");
		menuHelp.add(menuWiki);
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
		txtRootFolder.setToolTipText("Double click to open file dialog");
		txtRootFolder.addMouseListener(mouseHandler);

		txtRootFolder.setBounds(103, 11, 347, 28);
		panelGeneral.add(txtRootFolder);
		txtRootFolder.setColumns(10);

		JLabel lblGroupId = new JLabel("Group ID");
		lblGroupId.setBounds(21, 57, 74, 16);
		panelGeneral.add(lblGroupId);

		txtGroupId = new JTextField();
		txtGroupId.setColumns(10);
		txtGroupId.setBounds(103, 51, 347, 28);
		panelGeneral.add(txtGroupId);

		JLabel lblArtifactId = new JLabel("Artifact ID");
		lblArtifactId.setBounds(21, 137, 74, 16);
		panelGeneral.add(lblArtifactId);

		txtVersion = new JTextField();
		txtVersion.setColumns(10);
		txtVersion.setBounds(103, 91, 347, 28);
		panelGeneral.add(txtVersion);

		JLabel lblVersion = new JLabel("Version");
		lblVersion.setBounds(21, 97, 88, 16);
		panelGeneral.add(lblVersion);

		txtArtifactId = new JTextField();
		txtArtifactId.setColumns(10);
		txtArtifactId.setBounds(103, 131, 347, 28);
		panelGeneral.add(txtArtifactId);

		JLabel lblClassifier = new JLabel("Classifier");
		lblClassifier.setBounds(21, 177, 61, 16);
		panelGeneral.add(lblClassifier);

		txtClassifier = new JTextField();
		txtClassifier.setBounds(103, 171, 347, 28);
		panelGeneral.add(txtClassifier);
		txtClassifier.setColumns(10);

		JPanel panelFiles = new JPanel();
		tabbedPane.addTab("Files", null, panelFiles, null);
		panelFiles.setLayout(null);

		JLabel lblHeaderFolder = new JLabel("Header Folder");
		lblHeaderFolder.setBounds(6, 16, 88, 16);
		panelFiles.add(lblHeaderFolder);

		txtHeaderFolder = new JTextField();
		txtHeaderFolder.addMouseListener(mouseHandler);
		txtHeaderFolder.setColumns(10);
		txtHeaderFolder.setBounds(106, 10, 341, 28);
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
		txtDI.addMouseListener(mouseHandler);
		txtDI.setColumns(10);
		txtDI.setBounds(106, 94, 341, 28);
		panelFiles.add(txtDI);

		JLabel label_2 = new JLabel("Release-Simulator");
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		label_2.setBounds(6, 135, 122, 16);
		panelFiles.add(label_2);

		txtRS = new JTextField();
		txtRS.setColumns(10);
		txtRS.setBounds(106, 128, 341, 28);
		txtRS.addMouseListener(mouseHandler);
		panelFiles.add(txtRS);

		JLabel label_3 = new JLabel("Release-iphoneos");
		label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		label_3.setBounds(6, 169, 122, 16);
		panelFiles.add(label_3);

		txtRI = new JTextField();
		txtRI.setColumns(10);
		txtRI.setBounds(106, 162, 341, 28);
		txtRI.addMouseListener(mouseHandler);
		panelFiles.add(txtRI);

		txtDS = new JTextField();
		txtDS.addMouseListener(mouseHandler);
		txtDS.setColumns(10);
		txtDS.setBounds(106, 44, 341, 28);
		panelFiles.add(txtDS);

		JLabel lblResourceFolder = new JLabel("Resource Folder");
		lblResourceFolder.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblResourceFolder.setBounds(6, 204, 88, 16);
		panelFiles.add(lblResourceFolder);

		txtResourceFolder = new JTextField();
		txtResourceFolder.setColumns(10);
		txtResourceFolder.setBounds(106, 198, 341, 28);
		txtResourceFolder.addMouseListener(mouseHandler);
		panelFiles.add(txtResourceFolder);

		final JCheckBox ckbSameFolder = new JCheckBox("Same Lib");
		ckbSameFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				boolean ckbState = !ckbSameFolder.isSelected();
				String folderPath = txtDS.getText();
				txtDI.setEnabled(ckbState);
				txtRS.setEnabled(ckbState);
				txtRI.setEnabled(ckbState);
				if (ckbState) {
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
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		txtReadme = new JTextArea();
		scrollPane.setViewportView(txtReadme);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(txtReadme, popupMenu);

		JMenuItem mntmLoadFromFile = new JMenuItem("Load from file...");
		popupMenu.add(mntmLoadFromFile);

		JButton btnDeploy = new JButton("Deploy");
		btnDeploy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ConfigFile cfgFile = getConfigFileFromUI();
				DeployManager deployManager = new DeployManager();
				//Get the current plugin.
				Plugin plugin = PluginManager.getInstance().getCurrentPlugin();
				try {

					Class<?> pluginClass = Class.forName(plugin.getClazz());
					DeployTask deployTask =(DeployTask) pluginClass.newInstance();; 
					deployManager.setDeplyTask(deployTask);
					deployTask.setConfigFile(cfgFile);
					deployManager.deploy();
					final OutputConsole console = new OutputConsole();
					// TODO: Set the console location.
					console.setVisible(true);
					deployTask.addListener(new DeployTaskListener() {

						@Override
						public void onMessage(String message, Object context) {
							console.outputMessage(message);

						}

						@Override
						public void onError(String message, Object context) {

							console.outputMessage(message);
						}

						@Override
						public void onDeployStart(String message, Object context) {

						}

						@Override
						public void onDeployDone(String message, Object context) {

						}
					});
				} catch (IOException e) {
					logger.error("Error occurs, when deploying.", e);

				} catch (ClassNotFoundException e) {
					logger.error(String.format("Class %s not found", plugin.getClazz()), e);
				} catch (InstantiationException e) {
					logger.error(String.format("Cannot instante %s", plugin.getClazz()), e);
				} catch (IllegalAccessException e) {
					logger.error("Cannot access.", e);
				}
			}
		});
		btnDeploy.setBounds(370, 308, 117, 29);
		contentPane.add(btnDeploy);

		JButton btnLoadConfig = new JButton("Load Config");
		btnLoadConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				FileDialog fd = new FileDialog(MiOSDeployHelper.this);
				fd.setVisible(true);
				if (fd.getFile() == null) {
					return;
				}

				String filePath = String.format("%s%s", fd.getDirectory(),
						fd.getFile());
				ConfigFile cfgFile = ConfigFile.loadFromFile(filePath);
				if (cfgFile == null) {
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
				FileDialog fd = new FileDialog(MiOSDeployHelper.this);
				fd.setMode(FileDialog.SAVE);
				fd.setVisible(true);
				if (fd.getFile() != null) {
					String filePath = String.format("%s%s", fd.getDirectory(),
							fd.getFile());
					ConfigFile cfgFile = getConfigFileFromUI();
					cfgFile.saveToFile(filePath);
				}
			}
		});
		btnSaveConfig.setBounds(112, 308, 117, 29);
		contentPane.add(btnSaveConfig);
	}

	private void createMenuItems(final JMenu menuPlugins) {
		for (Plugin aPlugin : PluginManager.getInstance().getPlugins()) {
			final JCheckBoxMenuItem aMenuItem = new JCheckBoxMenuItem(
					aPlugin.getId());
			aMenuItem.setSelected(aPlugin.isSelected());
			aMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					aMenuItem.setSelected(true);
					Plugin oldPlug = PluginManager.getInstance()
							.setCurrentPlugin(aMenuItem.getText());
					for (int i = 0; i < menuPlugins.getItemCount() - 1; i++) {
						JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) menuPlugins
								.getItem(i);
						if (menuItem.getText().equals(oldPlug.getId())) {
							menuItem.setSelected(false);
						}
					}
				}
			});
			menuPlugins.add(aMenuItem);
		}
	}

	private ConfigFile getConfigFileFromUI() {
		ConfigFile cfgFile = new ConfigFile();
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
		cfgFile.classifier = txtClassifier.getText();
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
		txtClassifier.setText(cfgFile.classifier);
	}

	/**
	 * 
	 * @author Hao Hu
	 * 
	 */
	class MouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() != 2)
				return;
			JTextField textField = (JTextField) event.getSource();
			if (textField == txtRootFolder || textField == txtHeaderFolder
					|| textField == txtResourceFolder)
				System.setProperty("apple.awt.fileDialogForDirectories", "true");
			else
				System.setProperty("apple.awt.fileDialogForDirectories",
						"false");
			FileDialog dialog = new FileDialog(MiOSDeployHelper.this);
			dialog.setVisible(true);
			if (dialog.getDirectory() != null && dialog.getFile() != null) {
				String folderPath = String.format("%s%s",
						dialog.getDirectory(), dialog.getFile());
				textField.setText(folderPath);
			}
			System.setProperty("apple.awt.fileDialogForDirectories", "false");
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

}
