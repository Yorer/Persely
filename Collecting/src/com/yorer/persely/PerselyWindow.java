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
import java.text.ParseException;

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
	
	JLabel mainLblHetenFizetendo = new JLabel();
	
	private JLabel lblBefizetve = new JLabel("Befizetve");
	private JLabel lblFont = new JLabel();
	private JLabel lblHetenFizetendo = new JLabel();
	private JLabel lblKoviHet = new JLabel();
	private JLabel lblHatralevoOsszeg = new JLabel();
	private JButton btnMehet = new JButton();

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

	public PerselyWindow() throws ParseException {
		PerselyAdatok adatok = new PerselyAdatok();
		PerselyMain main = new PerselyMain();
		JPanel contentPanel = new JPanel();
		DbConnector dbc = new DbConnector();
		boolean opened = false;
		boolean fileExists = new File("resources/persely.db").exists();
		createTableIfNotExists(fileExists, opened, dbc, adatok, true);;
		dbc.getDatabaseValues(adatok);
		dbc.close();			
		
		main.setStatusz(adatok, PerselyWindow.this);
		setNovRataLabels(adatok, main);
		URL urlToImage = this.getClass().getResource("/icon.png");
		if (urlToImage != null) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(urlToImage));
		} else {
			JOptionPane.showMessageDialog(null, "Nincs meg az ikon URL!", "URL hiba", 2);
		}

		setTitle("Persely program");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 289, 279);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("F\u00E1jl");
		menuBar.add(mnNewMenu);

		JMenuItem menuPreferences = new JMenuItem("Beállítások");
		menuPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					new PreferencesDialog(adatok, PerselyWindow.this);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					refresh(adatok, main);
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

		btnMehet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dbc.open();
				dbc.fizetesMentes(adatok);
				try {
					dbc.getDatabaseValues(adatok);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dbc.close();
				refresh(adatok, main);
			}
		});
		btnMehet.setBounds(10, 186, 263, 32);
		contentPanel.setLayout(null);
		contentPanel.add(btnMehet);

		JLabel lbStatus = new JLabel("Státusz:");
		lbStatus.setBounds(58, 150, 72, 25);
		lbStatus.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPanel.add(lbStatus);

		lblBefizetve.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBefizetve.setBounds(139, 150, 97, 25);
		contentPanel.add(lblBefizetve);

		JLabel lbPerselyben = new JLabel("Perselyben:");
		lbPerselyben.setBounds(10, 11, 161, 14);
		contentPanel.add(lbPerselyben);

		setAlapOsszeg(adatok, main, lblFont);
		lblFont.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFont.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFont.setBounds(181, 11, 92, 14);
		contentPanel.add(lblFont);

		mainLblHetenFizetendo.setBounds(10, 36, 161, 14);
		contentPanel.add(mainLblHetenFizetendo);

		setHetenFizetendo(adatok, main, lblHetenFizetendo);
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

		setKovihet(adatok, main, lblKoviHet);
		lblKoviHet.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKoviHet.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblKoviHet.setBounds(181, 61, 92, 14);
		contentPanel.add(lblKoviHet);

		setHatralevoOsszeg(adatok, main, lblHatralevoOsszeg);
		lblHatralevoOsszeg.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHatralevoOsszeg.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHatralevoOsszeg.setBounds(181, 86, 92, 14);
		contentPanel.add(lblHatralevoOsszeg);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 136, 263, 2);
		contentPanel.add(separator_1);

		JLabel lblHt_1 = new JLabel(main.jelenlegiHet() + ". hét");
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
	
	private void setNovRataLabels(PerselyAdatok adatok, PerselyMain main){
		
		if(main.jelenlegiHet() == main.fizetettHet(adatok)){
			lblHetenFizetendo.setForeground(Color.GREEN);
			mainLblHetenFizetendo.setText("Héten fizetett:");
		} else {
			lblHetenFizetendo.setForeground(Color.RED);
			mainLblHetenFizetendo.setText("Héten fizetendő:");
		}
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
			label.setText(main.arfolyam(adatok.getArfolyam()) + new Integer(main.hetenFizetendo(adatok, main.jelenlegiHet())).toString());
		} else {
			label.setText(new Integer(main.hetenFizetendo(adatok, main.jelenlegiHet())).toString() + " " + main.arfolyam(adatok.getArfolyam()));
		}
	}
	
	private void setKovihet(PerselyAdatok adatok, PerselyMain main, JLabel label){
		if(!adatok.getArfolyam().equals("Forint")){
			label.setText(main.arfolyam(adatok.getArfolyam()) + new Integer(main.koviHet(adatok, main.jelenlegiHet())).toString());		
		} else {
			label.setText(new Integer(main.koviHet(adatok, main.jelenlegiHet())).toString() + " " + main.arfolyam(adatok.getArfolyam()));
		}
	}
	
	private void setHatralevoOsszeg(PerselyAdatok adatok, PerselyMain main, JLabel label){
		if(!adatok.getArfolyam().equals("Forint")){
			label.setText(main.arfolyam(adatok.getArfolyam()) + new Integer(main.hatraVan(adatok)).toString());			
		} else {
			label.setText(new Integer(main.hatraVan(adatok)).toString() + " " + main.arfolyam(adatok.getArfolyam()));
		}
	}
	
	protected void createTableIfNotExists(boolean fileExists, boolean opened, DbConnector dbc, PerselyAdatok adatok, boolean firstOpen){
		if(!fileExists){
			new File("resources").mkdirs();
			
			opened = dbc.open();
			dbc.createTable();
			if(firstOpen){
				defaultValueSet(adatok);				
			}
			dbc.addToDB(adatok);
		}
		if(!opened){
			dbc.open();
		}
	}
	
	private void refresh(PerselyAdatok adatok, PerselyMain main){
		setAlapOsszeg(adatok, main, lblFont);
		setHetenFizetendo(adatok, main, lblHetenFizetendo);
		setKovihet(adatok, main, lblKoviHet);
		setHatralevoOsszeg(adatok, main, lblHatralevoOsszeg);
		setNovRataLabels(adatok, main);
		main.setStatusz(adatok, PerselyWindow.this);
		revalidate();
		repaint();
	}

	public JLabel getLblBefizetve() {
		return lblBefizetve;
	}

	public JLabel getLblFont() {
		return lblFont;
	}

	public JLabel getLblHetenFizetendo() {
		return lblHetenFizetendo;
	}

	public JLabel getLblKoviHet() {
		return lblKoviHet;
	}

	public JLabel getLblHatralevoOsszeg() {
		return lblHatralevoOsszeg;
	}
	
	public JButton getBtnMehet() {
		return btnMehet;
	}
}
