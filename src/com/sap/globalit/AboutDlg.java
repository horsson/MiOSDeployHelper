package com.sap.globalit;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutDlg extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public AboutDlg() {
		setResizable(false);
		setBounds(100, 100, 302, 195);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AboutDlg.class.getResource("/com/sap/globalit/resources/AboutIcon.jpg")));
		lblNewLabel.setBounds(131, 12, 48, 48);
		getContentPane().add(lblNewLabel);
		
		JLabel lblMiosDeployer = new JLabel("MiOS Deployer");
		lblMiosDeployer.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblMiosDeployer.setBounds(107, 72, 108, 16);
		getContentPane().add(lblMiosDeployer);
		
		JLabel lblVersion = new JLabel("Version 1.0");
		lblVersion.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblVersion.setBounds(119, 98, 71, 16);
		getContentPane().add(lblVersion);
		
		JLabel lblDevelopedByHao = new JLabel("Developed by Hao Hu");
		lblDevelopedByHao.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblDevelopedByHao.setBounds(100, 118, 125, 16);
		getContentPane().add(lblDevelopedByHao);
		
		JLabel lblHaohusapcom = new JLabel("hao.hu@sap.com");
		lblHaohusapcom.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblHaohusapcom.setBounds(107, 137, 88, 16);
		getContentPane().add(lblHaohusapcom);
		setCenter();
	}
	
	private void setCenter()
	{
		
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension screenSize = tk.getScreenSize();
	    int screenHeight = screenSize.height;
	    int screenWidth = screenSize.width;
	    int frameWidth = this.getSize().width;
	    int frameHeight = this.getSize().height;
	    
	    setLocation((screenWidth-frameWidth) / 2, (screenHeight-frameHeight) / 2);
	}
}
