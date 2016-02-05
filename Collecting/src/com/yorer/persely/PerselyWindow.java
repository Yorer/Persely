package com.yorer.persely;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PerselyWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JLabel mainLblHetenFizetendo = new JLabel();
	
	protected static boolean notFirstlyOpened = new File("resources/persely.db").exists();
	protected static boolean elsoInditas = true;
	
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
					elsoInditas = false;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected PerselyWindow() throws ParseException {
		PerselyAdatok adatok = new PerselyAdatok();
		PerselyMain main = new PerselyMain();
		DbConnector dbc = new DbConnector();
		
		JPanel contentPanel = new JPanel();
		
		setupStart(dbc, adatok, main);
		
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

		JMenu mnNewMenu = new JMenu("Fájl");
		menuBar.add(mnNewMenu);

		JMenuItem menuPreferences = new JMenuItem("Beállítások", KeyEvent.VK_T);
		menuPreferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
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

		JMenuItem mntmKilps = new JMenuItem("Kilépés", KeyEvent.VK_T);
		mntmKilps.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
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
					dbc.adatbazisErtekek(adatok);
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
	
	protected void createTableIfNotExists(boolean opened, DbConnector dbc, PerselyAdatok adatok, boolean firstOpen){
		if(!notFirstlyOpened){
			new File("resources").mkdirs();
			
			opened = dbc.open();
			dbc.createTable();
			if(firstOpen){
//				defaultValueSet(adatok);				
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
	}
	
	private void setupStart(DbConnector dbc, PerselyAdatok adatok, PerselyMain main) throws ParseException{
		boolean opened = false;
		if(!notFirstlyOpened){
			elsoInditas = true;
			openElsoInditasBeallitasok(adatok, PerselyWindow.this);
		} else {
			elsoInditas = false;
		}
		createTableIfNotExists(opened, dbc, adatok, true);
		dbc.adatbazisErtekek(adatok);
		dbc.close();			
		
		main.setStatusz(adatok, PerselyWindow.this);
		setNovRataLabels(adatok, main);
		vege(main, adatok);
	}
	
	protected void openElsoInditasBeallitasok(PerselyAdatok adatok, PerselyWindow window){
		JOptionPane.showMessageDialog(null, "Most indítottad először a programot.\nHogy létrehozhassam az adatbázist, be kell állítanod.", "Üdvözöllek", 1);
		new PreferencesDialog(adatok, window);
	}
	
	private void vege(PerselyMain main, PerselyAdatok adatok){
		if(main.hatraVan(adatok) <= 0){
			if(!adatok.getArfolyam().equals("Forint")){
				JOptionPane.showMessageDialog(null, "Sikeresen végigcsináltad a kihívást.\nGRATULÁLOK!\nÖsszesen gyűjtöttél: " + main.arfolyam(adatok.getArfolyam()) + adatok.getAlapOsszeg() + "-(o)t", "Infó", 1);
			} else {
				JOptionPane.showMessageDialog(null, "Sikeresen végigcsináltad a kihívást.\nGRATULÁLOK!\nÖsszesen gyűjtöttél: " + adatok.getAlapOsszeg() + " " + main.arfolyam(adatok.getArfolyam()) + "-(o)t", "Infó", 1);
			}
			System.exit(0);
		}
	}

	protected JLabel getLblBefizetve() {
		return lblBefizetve;
	}

	protected JLabel getLblFont() {
		return lblFont;
	}

	protected JLabel getLblHetenFizetendo() {
		return lblHetenFizetendo;
	}

	protected JLabel getLblKoviHet() {
		return lblKoviHet;
	}

	protected JLabel getLblHatralevoOsszeg() {
		return lblHatralevoOsszeg;
	}
	
	protected JButton getBtnMehet() {
		return btnMehet;
	}
}
