package com.yorer.persely;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PerselyWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblBefizetve;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					checkIfRunning();
					PerselyWindow frame = new PerselyWindow();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PerselyWindow() {
		JPanel contentPanel;
		JTextArea log;
		URL urlToImage = this.getClass().getResource("/icon.png");
		if (urlToImage != null) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(urlToImage));
		} else {
			JOptionPane.showMessageDialog(null, "Nincs meg az ikon URL!", "URL hiba", 2);
		}

		setTitle("Persely program");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 289, 359);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("F\u00E1jl");
		menuBar.add(mnNewMenu);

		JMenuItem menuSave = new JMenuItem("Ment\u00E9s");
		mnNewMenu.add(menuSave);

		JMenuItem menuLoad = new JMenuItem("Bet\u00F6lt\u00E9s");
		mnNewMenu.add(menuLoad);

		JMenuItem menuPreferences = new JMenuItem("Beállítások");
		menuPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					new PreferencesDialog();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mnNewMenu.add(menuPreferences);

		JMenuItem mntmKilps = new JMenuItem("Kilépés");
		mntmKilps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);
		mnNewMenu.add(mntmKilps);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);

		JButton btnMehet = new JButton("Befizetés");
		btnMehet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnMehet.setBounds(10, 186, 263, 23);
		contentPanel.setLayout(null);
		contentPanel.add(btnMehet);

		log = new JTextArea();
		log.setBounds(10, 233, 263, 63);
		contentPanel.add(log);
		log.setEnabled(false);
		log.setText("Ha befizetted a hetet kattints a gombra! (log)");
		log.setEditable(false);
		log.setLineWrap(true);
		log.setFont(new Font("Tahoma", Font.PLAIN, 11));
		log.setDisabledTextColor(Color.BLACK);

		JLabel lbStatus = new JLabel("Státusz:");
		lbStatus.setBounds(58, 150, 72, 25);
		lbStatus.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPanel.add(lbStatus);

		lblBefizetve = new JLabel("Befizetve");
		lblBefizetve.setForeground(Color.GREEN);
		lblBefizetve.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBefizetve.setBounds(139, 150, 82, 25);
		contentPanel.add(lblBefizetve);

		JLabel lbPerselyben = new JLabel("Perselyben:");
		lbPerselyben.setBounds(10, 11, 161, 14);
		contentPanel.add(lbPerselyben);

		JLabel lblFont = new JLabel("₤50");
		lblFont.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFont.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFont.setBounds(195, 11, 78, 14);
		contentPanel.add(lblFont);

		JLabel lblHtenFizetett = new JLabel("Héten fizetendő:");
		lblHtenFizetett.setBounds(10, 36, 161, 14);
		contentPanel.add(lblHtenFizetett);

		JLabel label = new JLabel("₤10");
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(195, 36, 78, 14);
		contentPanel.add(label);

		JLabel lblKvetkezsszeg = new JLabel("Következő összeg:");
		lblKvetkezsszeg.setBounds(10, 61, 161, 14);
		contentPanel.add(lblKvetkezsszeg);

		JLabel lblHt = new JLabel("Hét:");
		lblHt.setBounds(10, 111, 161, 14);
		contentPanel.add(lblHt);

		JLabel lblHtralvsszeg = new JLabel("Hátralévő összeg:");
		lblHtralvsszeg.setBounds(10, 86, 161, 14);
		contentPanel.add(lblHtralvsszeg);

		JLabel label_1 = new JLabel("₤12");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(195, 61, 78, 14);
		contentPanel.add(label_1);

		JLabel label_2 = new JLabel("₤2304");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(195, 86, 78, 14);
		contentPanel.add(label_2);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 136, 263, 2);
		contentPanel.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 220, 263, 2);
		contentPanel.add(separator_2);

		JLabel lblHt_1 = new JLabel("3. hét");
		lblHt_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHt_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHt_1.setBounds(195, 111, 78, 14);
		contentPanel.add(lblHt_1);

		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - getWidth()) / 2;
		final int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
	}

	public JLabel getLblBefizetve() {
		return lblBefizetve;
	}

	private static void checkIfRunning() {
		final int PORT = 9999;
		try {
			extracted(PORT);
		} catch (BindException e) {
			JOptionPane.showMessageDialog(null, "Már fut a program!", "Figyelmeztetés", 2);
			System.exit(1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Hiba történt: " + e.getMessage(), "Error", 0);
			System.exit(2);
		}
	}

	private static ServerSocket extracted(final int PORT) throws IOException, UnknownHostException {
		return new ServerSocket(PORT, 0, InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }));
	}
}
