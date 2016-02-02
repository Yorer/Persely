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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class PerselyWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel lblBefizetve;
	private JLabel lblFont;
	private JLabel lblHetenFizetendo;
	private JLabel lblKoviHet;
	private JLabel lblHatralevoOsszeg;

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
		PerselyAdatok adatok = new PerselyAdatok();
		PerselyMain main = new PerselyMain();
		JPanel contentPanel = new JPanel();
		JTextArea log;
		DbConnector dbc = new DbConnector();
		boolean fileExists = new File("resources/persely.db").exists();
		dbc.open();
		if(!fileExists){
			dbc.createTable();
			defaultValueSet(adatok);
			dbc.addToDB(adatok);
		}
		dbc.getDatabaseValues(adatok);
		dbc.close();			
		
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
					new PreferencesDialog(adatok, PerselyWindow.this);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					setAlapOsszeg(adatok, main, lblFont);
					setHetenFizetendo(adatok, main, lblHetenFizetendo);
					setKovihet(adatok, main, lblKoviHet);
					setHatralevoOsszeg(adatok, main, lblHatralevoOsszeg);
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

		lblFont = new JLabel();
		setAlapOsszeg(adatok, main, lblFont);
		lblFont.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFont.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFont.setBounds(181, 11, 92, 14);
		contentPanel.add(lblFont);

		JLabel lblHtenFizetett = new JLabel("Héten fizetendő:");
		lblHtenFizetett.setBounds(10, 36, 161, 14);
		contentPanel.add(lblHtenFizetett);

		lblHetenFizetendo = new JLabel();
		setHetenFizetendo(adatok, main, lblHetenFizetendo);
		lblHetenFizetendo.setForeground(Color.RED);
		lblHetenFizetendo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHetenFizetendo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHetenFizetendo.setBounds(181, 36, 92, 14);
		contentPanel.add(lblHetenFizetendo);

		JLabel lblKvetkezsszeg = new JLabel("Következő összeg:");
		lblKvetkezsszeg.setBounds(10, 61, 161, 14);
		contentPanel.add(lblKvetkezsszeg);

		JLabel lblHt = new JLabel("Hét:");
		lblHt.setBounds(10, 111, 161, 14);
		contentPanel.add(lblHt);

		JLabel lblHtralvsszeg = new JLabel("Hátralévő összeg:");
		lblHtralvsszeg.setBounds(10, 86, 161, 14);
		contentPanel.add(lblHtralvsszeg);

		lblKoviHet = new JLabel();
		setKovihet(adatok, main, lblKoviHet);
		lblKoviHet.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKoviHet.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblKoviHet.setBounds(181, 61, 92, 14);
		contentPanel.add(lblKoviHet);

		lblHatralevoOsszeg = new JLabel();
		setHatralevoOsszeg(adatok, main, lblHatralevoOsszeg);
		lblHatralevoOsszeg.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHatralevoOsszeg.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHatralevoOsszeg.setBounds(181, 86, 92, 14);
		contentPanel.add(lblHatralevoOsszeg);

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

	private void defaultValueSet(PerselyAdatok adatok) {
		adatok.setAlapOsszeg(0);
		adatok.setNovRata(1);
		adatok.setArfolyam("Font");
		
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
	
	private void setAlapOsszeg(PerselyAdatok adatok, PerselyMain main, JLabel label){
		if(adatok.getArfolyam() == null){
			adatok.setArfolyam("Font");
		}
		if(!adatok.getArfolyam().equals("Forint")){
			label.setText(main.arfolyam(adatok.getArfolyam()) + new Integer(adatok.getAlapOsszeg()).toString());
		} else {
			label.setText(new Integer(adatok.getAlapOsszeg()).toString() + " " + main.arfolyam(adatok.getArfolyam()));
		}
	}
	
	private void setHetenFizetendo(PerselyAdatok adatok, PerselyMain main, JLabel label){
		if(!adatok.getArfolyam().equals("Forint")){
			label.setText(main.arfolyam(adatok.getArfolyam()) + new Integer(main.hetenFizetendo(adatok, 3)).toString()); //FIXME NINCS KÉSZ! Van benne konstans!			
		} else {
			label.setText(new Integer(main.hetenFizetendo(adatok, 3)).toString() + " " + main.arfolyam(adatok.getArfolyam()));
		}
	}
	
	private void setKovihet(PerselyAdatok adatok, PerselyMain main, JLabel label){
		if(!adatok.getArfolyam().equals("Forint")){
			label.setText(main.arfolyam(adatok.getArfolyam()) + new Integer(main.koviHet(adatok, 3)).toString()); //FIXME NINCS KÉSZ! Van benne konstans!			
		} else {
			label.setText(new Integer(main.koviHet(adatok, 3)).toString() + " " + main.arfolyam(adatok.getArfolyam()));
		}
	}
	
	private void setHatralevoOsszeg(PerselyAdatok adatok, PerselyMain main, JLabel label){
		if(!adatok.getArfolyam().equals("Forint")){
			label.setText(main.arfolyam(adatok.getArfolyam()) + new Integer(main.hatraVan(adatok)).toString());			
		} else {
			label.setText(new Integer(main.hatraVan(adatok)).toString() + " " + main.arfolyam(adatok.getArfolyam()));
		}
	}
}
