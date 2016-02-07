package com.yorer.persely;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class StatusBar {
	JDialog dialog = new JDialog();

	public void start() {
	    dialog.getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
	    dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    dialog.setLocationRelativeTo(null);
	    dialog.setBounds(100, 100, 194, 52);
	    dialog.setLocationRelativeTo(null);
	    dialog.setUndecorated(true);
	    
	    final JLabel status = new JLabel("Connecting...");
	    status.setBounds(63, 11, 83, 14);
	    
	    final JProgressBar jProgressBar = new JProgressBar();
	    jProgressBar.setBounds(10, 29, 174, 12);
	    dialog.getContentPane().setLayout(null);
	    dialog.getContentPane().add(status);
	    dialog.getContentPane().add(jProgressBar);
	    
	    JLabel lblNewLabel = new JLabel();
	    lblNewLabel.setBounds(1, 0, 193, 52);
	    lblNewLabel.setBorder(BorderFactory.createLineBorder(Color.black));
	    dialog.getContentPane().add(lblNewLabel);

	    dialog.toFront();
	    dialog.setVisible(true);
	    jProgressBar.setIndeterminate(true);
	}
	
	public void done(){
		dialog.setVisible(false);
	}
}
