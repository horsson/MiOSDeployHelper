package com.sap.globalit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

public class OutputConsole extends JFrame {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final 	JScrollPane scrollPane;
	private final JTextArea txtOutput;
	/**
	 * Create the frame.
	 */
	public OutputConsole() {
		setBounds(100, 100, 778, 608);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		txtOutput = new JTextArea();
		scrollPane.setViewportView(txtOutput);
	}
	
	public void outputMessage(String message)
	{
		txtOutput.append(message);
		txtOutput.append("\n");
	}
	
	public void outputErr(String err)
	{
		txtOutput.append(err);
		txtOutput.append("\n");
	}
	

}
