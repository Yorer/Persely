package com.yorer.persely;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JSeparator;

public class Sugo extends JDialog {
	
	private static final long serialVersionUID = 1L;

	public Sugo() {
		getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		setBackground(SystemColor.inactiveCaptionBorder);
		JPanel contentPanel = new JPanel();
		setBounds(100, 100, 600, 679);
		contentPanel.setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setTitle("Súgó");
		setResizable(false);
		setModal(true);
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - getWidth()) / 2;
		final int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
		getContentPane().setLayout(null);
		
		JLabel lblCim = new JLabel("A programr\u00F3l r\u00F6viden:");
		lblCim.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCim.setBounds(10, 11, 202, 17);
		getContentPane().add(lblCim);
		
		JTextArea lblLeiras = new JTextArea();
		lblLeiras.setBackground(SystemColor.inactiveCaptionBorder);
		lblLeiras.setEditable(false);
		lblLeiras.setWrapStyleWord(true);
		lblLeiras.setLineWrap(true);
		lblLeiras.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLeiras.setText("A program az 52 hetes kih\u00EDv\u00E1sra k\u00E9sz\u00FClt a perselyben l\u00E9v\u0151 \u00F6sszeg, \u00E9s a h\u00E1tralev\u0151 \u00F6sszeg nyomonk\u00F6vet\u00E9se c\u00E9lj\u00E1b\u00F3l. Minden \u00FAj h\u00E9ten eml\u00E9keztet a befizetend\u0151 \u00F6sszegre, ki\u00EDrja a jelenlegi egyenleget, \u00E9s hogy mennyi van h\u00E1tra, melyet a g\u00E9peden egy adatb\u00E1zisban t\u00E1rol. A program forr\u00E1sk\u00F3dja, \u00E9s a kih\u00EDv\u00E1s r\u00E9szletei itt \u00E9rhet\u0151k el:");
		lblLeiras.setBounds(10, 39, 574, 46);
		getContentPane().add(lblLeiras);
		
		JButton btnVigylAKszt = new JButton();
		btnVigylAKszt.setText("Készítõ oldala");
		btnVigylAKszt.setBounds(214, 96, 147, 23);
		btnVigylAKszt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				URL url;
				try {
					url = new URL("https://github.com/Yorer/Persely");
					openWebpage(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		});
		JLabel lblAlapadatok = new JLabel("Perselyemben:");
		lblAlapadatok.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAlapadatok.setBounds(10, 178, 104, 14);
		getContentPane().add(lblAlapadatok);
		
		JTextArea txtrKirjaMennyiVan = new JTextArea();
		txtrKirjaMennyiVan.setWrapStyleWord(true);
		txtrKirjaMennyiVan.setText("Ki\u00EDrja mennyi van \u00E9ppen a perselyben. Az \u00E9rt\u00E9ke akkor v\u00E1ltozik, ha a gomb seg\u00EDts\u00E9g\u00E9vel befizeted az \u00F6sszeget, vagy manu\u00E1lisan nem v\u00E1ltoztatsz az \u00E9rt\u00E9k\u00E9n. \u00C9v elej\u00E9n 0-r\u00F3l indul, \u00E9v k\u00F6zben be\u00E1ll\u00EDtja mag\u00E1t.");
		txtrKirjaMennyiVan.setLineWrap(true);
		txtrKirjaMennyiVan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrKirjaMennyiVan.setEditable(false);
		txtrKirjaMennyiVan.setBackground(SystemColor.inactiveCaptionBorder);
		txtrKirjaMennyiVan.setBounds(24, 203, 560, 32);
		getContentPane().add(txtrKirjaMennyiVan);
		
		JTextArea txtrVigyzatHaNveled = new JTextArea();
		txtrVigyzatHaNveled.setWrapStyleWord(true);
		txtrVigyzatHaNveled.setText("Vigy\u00E1zat: ha n\u00F6veled a perselyben l\u00E9v\u0151 \u00E9rt\u00E9ket, az \u00F6sszesen befizetend\u0151 \u00F6sszeg \u00E9rt\u00E9ke cs\u00F6kkenni fog, viszont ha cs\u00F6kkented a perselyben l\u00E9v\u0151 \u00E9rt\u00E9ket, akkor att\u00F3l m\u00E9g nem fog n\u0151ni a befizetend\u0151 \u00F6sszeg \u00E9rt\u00E9ke, viszont annyival kevesebb lesz a perselyedben a v\u00E9g\u00E9n. Javasolt az alapadatok be\u00E1ll\u00EDt\u00E1sait alap\u00E9rtelmezetten hagyni, persze van lehet\u0151s\u00E9g a v\u00E1ltoztat\u00E1s\u00E1ra is ha m\u00E9gsem a tervezett \u00F6sszeg ker\u00FClt a perselybe.");
		txtrVigyzatHaNveled.setLineWrap(true);
		txtrVigyzatHaNveled.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrVigyzatHaNveled.setEditable(false);
		txtrVigyzatHaNveled.setBackground(SystemColor.inactiveCaptionBorder);
		txtrVigyzatHaNveled.setBounds(24, 246, 560, 60);
		getContentPane().add(txtrVigyzatHaNveled);
		getContentPane().add(btnVigylAKszt);
		
		JButton btnKihvsRszletek = new JButton();
		btnKihvsRszletek.setText("Kih\u00EDv\u00E1s r\u00E9szletek");
		btnKihvsRszletek.setBounds(214, 130, 147, 23);
		btnKihvsRszletek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				URL url;
				try {
					url = new URL("http://nassolda.receptneked.hu/2015/12/28/53-hetes-persely-sporolj-gasztroangyal-modra/");
					openWebpage(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		});
		getContentPane().add(btnKihvsRszletek);
		
		JLabel lblNvekedsiRta = new JLabel("N\u00F6veked\u00E9si r\u00E1ta:");
		lblNvekedsiRta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNvekedsiRta.setBounds(10, 317, 139, 14);
		getContentPane().add(lblNvekedsiRta);
		
		JTextArea txtrKirjaMennyivelNvekedjen = new JTextArea();
		txtrKirjaMennyivelNvekedjen.setWrapStyleWord(true);
		txtrKirjaMennyivelNvekedjen.setText("Ki\u00EDrja mennyivel n\u00F6vekedjen h\u00E9tr\u0151l h\u00E9tre a befizetend\u0151 \u00F6sszeg. Maximum egymilli\u00F3 \u00E9rt\u00E9kkel n\u00F6vekedhet hetente.");
		txtrKirjaMennyivelNvekedjen.setLineWrap(true);
		txtrKirjaMennyivelNvekedjen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrKirjaMennyivelNvekedjen.setEditable(false);
		txtrKirjaMennyivelNvekedjen.setBackground(SystemColor.inactiveCaptionBorder);
		txtrKirjaMennyivelNvekedjen.setBounds(24, 342, 560, 17);
		getContentPane().add(txtrKirjaMennyivelNvekedjen);
		
		JLabel lblHtenFizetett = new JLabel("H\u00E9ten fizetend\u0151:");
		lblHtenFizetett.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHtenFizetett.setBounds(10, 370, 139, 14);
		getContentPane().add(lblHtenFizetett);
		
		JTextArea txtrMegmutatjaHogyEzen = new JTextArea();
		txtrMegmutatjaHogyEzen.setWrapStyleWord(true);
		txtrMegmutatjaHogyEzen.setText("Megmutatja hogy ezen a h\u00E9ten mennyivel tartozol, majd a gomb megnyom\u00E1sa ut\u00E1n hozz\u00E1adja a perselyedhez.");
		txtrMegmutatjaHogyEzen.setLineWrap(true);
		txtrMegmutatjaHogyEzen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrMegmutatjaHogyEzen.setEditable(false);
		txtrMegmutatjaHogyEzen.setBackground(SystemColor.inactiveCaptionBorder);
		txtrMegmutatjaHogyEzen.setBounds(24, 395, 560, 17);
		getContentPane().add(txtrMegmutatjaHogyEzen);
		
		JLabel label = new JLabel("H\u00E9ten fizetett:");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(10, 423, 139, 14);
		getContentPane().add(label);
		
		JTextArea txtrMegmutatjaHogyEzen_1 = new JTextArea();
		txtrMegmutatjaHogyEzen_1.setWrapStyleWord(true);
		txtrMegmutatjaHogyEzen_1.setText("Megmutatja hogy ezen a h\u00E9ten mennyit fizett\u00E9l be. Ha \u00E9v k\u00F6zben kezdted el haszn\u00E1lni a programot, akkor m\u00E1r automatikusan arra a h\u00E9tre minden be lesz fizetve, \u00E9s a perselybe azt az \u00F6sszeget kell tenni, amit a program \u00EDr.");
		txtrMegmutatjaHogyEzen_1.setLineWrap(true);
		txtrMegmutatjaHogyEzen_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrMegmutatjaHogyEzen_1.setEditable(false);
		txtrMegmutatjaHogyEzen_1.setBackground(SystemColor.inactiveCaptionBorder);
		txtrMegmutatjaHogyEzen_1.setBounds(24, 448, 560, 32);
		getContentPane().add(txtrMegmutatjaHogyEzen_1);
		
		JLabel lblKvetkezsszeg = new JLabel("K\u00F6vetkez\u0151 \u00F6sszeg:");
		lblKvetkezsszeg.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblKvetkezsszeg.setBounds(10, 491, 139, 14);
		getContentPane().add(lblKvetkezsszeg);
		
		JTextArea txtrrtelemSzerenA = new JTextArea();
		txtrrtelemSzerenA.setWrapStyleWord(true);
		txtrrtelemSzerenA.setText("\u00C9rtelemszer\u0171en a k\u00F6vetkez\u0151 heti \u00F6sszeget \u00EDrja ki, amire k\u00E9sz\u00FClni kell.");
		txtrrtelemSzerenA.setLineWrap(true);
		txtrrtelemSzerenA.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrrtelemSzerenA.setEditable(false);
		txtrrtelemSzerenA.setBackground(SystemColor.inactiveCaptionBorder);
		txtrrtelemSzerenA.setBounds(24, 516, 560, 17);
		getContentPane().add(txtrrtelemSzerenA);
		
		JLabel lblHtralvsszeg = new JLabel("H\u00E1tral\u00E9v\u0151 \u00F6sszeg:");
		lblHtralvsszeg.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHtralvsszeg.setBounds(10, 544, 139, 14);
		getContentPane().add(lblHtralvsszeg);
		
		JTextArea txtrEnnyitKellMg = new JTextArea();
		txtrEnnyitKellMg.setWrapStyleWord(true);
		txtrEnnyitKellMg.setText("Ennyit kell m\u00E9g befizetni, hogy \u00E9v v\u00E9g\u00E9re a kih\u00EDv\u00E1st teljes\u00EDtsd, majd a v\u00E9g\u00E9n ki\u00EDrja mennyit gy\u0171jt\u00F6tt\u00E9l \u00F6ssze.");
		txtrEnnyitKellMg.setLineWrap(true);
		txtrEnnyitKellMg.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrEnnyitKellMg.setEditable(false);
		txtrEnnyitKellMg.setBackground(SystemColor.inactiveCaptionBorder);
		txtrEnnyitKellMg.setBounds(24, 569, 560, 17);
		getContentPane().add(txtrEnnyitKellMg);
		
		JLabel lblHt = new JLabel("H\u00E9t:");
		lblHt.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHt.setBounds(10, 597, 139, 14);
		getContentPane().add(lblHt);
		
		JTextArea txtrMegmutatjappenHanyadik = new JTextArea();
		txtrMegmutatjappenHanyadik.setWrapStyleWord(true);
		txtrMegmutatjappenHanyadik.setText("Megmutatja \u00E9ppen hanyadik h\u00E9t van.");
		txtrMegmutatjappenHanyadik.setLineWrap(true);
		txtrMegmutatjappenHanyadik.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrMegmutatjappenHanyadik.setEditable(false);
		txtrMegmutatjappenHanyadik.setBackground(SystemColor.inactiveCaptionBorder);
		txtrMegmutatjappenHanyadik.setBounds(24, 622, 560, 17);
		getContentPane().add(txtrMegmutatjappenHanyadik);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 164, 574, 3);
		getContentPane().add(separator);
		setVisible(true);
	}
	
	protected void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	protected void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
}
