package com.yorer.persely;

import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;

public class Nevjegy extends JDialog {

	private static final long serialVersionUID = 1L;

	public Nevjegy() {
		getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		setBackground(SystemColor.inactiveCaptionBorder);
		JPanel contentPanel = new JPanel();
		setBounds(100, 100, 381, 124);
		contentPanel.setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setTitle("Névjegy");
		setResizable(false);
		setModal(true);
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - getWidth()) / 2;
		final int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
		getContentPane().setLayout(null);
		
		JLabel lblPerselyProgram = new JLabel("Persely program");
		lblPerselyProgram.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPerselyProgram.setBounds(127, 11, 130, 17);
		getContentPane().add(lblPerselyProgram);
		
		JTextArea txtrCopyrightYorer = new JTextArea();
		txtrCopyrightYorer.setWrapStyleWord(true);
		txtrCopyrightYorer.setText("A program m\u00E1sol\u00E1sa, m\u00F3dos\u00EDt\u00E1sa megengedett.");
		txtrCopyrightYorer.setLineWrap(true);
		txtrCopyrightYorer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrCopyrightYorer.setEditable(false);
		txtrCopyrightYorer.setBackground(SystemColor.inactiveCaptionBorder);
		txtrCopyrightYorer.setBounds(68, 39, 247, 17);
		getContentPane().add(txtrCopyrightYorer);
		
		JTextArea txtrCopyrightYorer_1 = new JTextArea();
		txtrCopyrightYorer_1.setWrapStyleWord(true);
		txtrCopyrightYorer_1.setText("Copyright: Yorer");
		txtrCopyrightYorer_1.setLineWrap(true);
		txtrCopyrightYorer_1.setFont(new Font("Tahoma", Font.ITALIC, 11));
		txtrCopyrightYorer_1.setEditable(false);
		txtrCopyrightYorer_1.setBackground(SystemColor.inactiveCaptionBorder);
		txtrCopyrightYorer_1.setBounds(140, 67, 94, 17);
		getContentPane().add(txtrCopyrightYorer_1);
		setVisible(true);
	}
}
