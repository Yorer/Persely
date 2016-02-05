package com.yorer.persely;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class PreferencesDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JButton cancelButton = new JButton("Cancel");

	protected PreferencesDialog(PerselyAdatok adatok, PerselyWindow window) {
		JSpinner spNovRata;
		JSpinner spAlapOsszeg;
		JComboBox<String> jComboBox;
		
		setTitle("Be�ll�t�sok");
		setResizable(false);
		setLocationRelativeTo(null);
		final JPanel contentPanel = new JPanel();
		
		
		setBounds(100, 100, 275, 164);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("Alap�sszeg (perselyben lev�)");
		label.setBounds(6, 14, 177, 14);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("N�veked�si r�ta");
		label_1.setBounds(6, 39, 177, 14);
		contentPanel.add(label_1);

		SpinnerNumberModel spinnerModelNovRata = new SpinnerNumberModel(1, 1, 1000000, 1);
		spNovRata = new JSpinner(spinnerModelNovRata);
		spNovRata.setToolTipText("N�veked�si r�ta");
		spNovRata.setBounds(193, 36, 70, 20);
		if(window.elsoInditas){
			spNovRata.setValue(1);
		} else {
			spNovRata.setValue(adatok.getNovRata());			
		}
		JTextField tf3 = ((JSpinner.DefaultEditor) spNovRata.getEditor()).getTextField();
		tf3.addFocusListener(focus());
		contentPanel.add(spNovRata);

		SpinnerNumberModel spinnerModelAlapOsszeg = new SpinnerNumberModel(0, 0, 1000000, 1);
		spAlapOsszeg = new JSpinner(spinnerModelAlapOsszeg);
		spAlapOsszeg.setToolTipText("Alap�sszeg");
		spAlapOsszeg.setBounds(193, 11, 70, 20);
		if(window.elsoInditas){
			spAlapOsszeg.setEnabled(false);
		} else {
			spAlapOsszeg.setValue(adatok.getAlapOsszeg());			
		}
		JTextField tf4 = ((JSpinner.DefaultEditor) spAlapOsszeg.getEditor()).getTextField();
		tf4.addFocusListener(focus());
		contentPanel.add(spAlapOsszeg);
		
		JLabel label_4 = new JLabel("�rfolyam");
		label_4.setBounds(6, 67, 177, 14);
		contentPanel.add(label_4);
		
		Values v = new Values();
		String[] items = {v.getFONT(), v.getEURO(), v.getFORINT()};
		jComboBox = new JComboBox<>(items);
		jComboBox.setBounds(193, 64, 70, 20);
		if(adatok.getArfolyam() != null && !adatok.getArfolyam().isEmpty()){
			jComboBox.setSelectedItem(adatok.getArfolyam());			
		}
		contentPanel.add(jComboBox);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 92, 257, 2);
		contentPanel.add(separator);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						DbConnector dbc = new DbConnector();
						if(window.elsoInditas){
							PerselyMain main = new PerselyMain();
							spAlapOsszeg.setValue(main.eddigOsszegyujtott((int)spNovRata.getValue()));
							JOptionPane.showMessageDialog(null, "A n�veked�si r�ta megad�sa ut�n a program automatikusan be�ll�totta a perselyben l�v� �rt�ket.\n" +
							"A(z) " + main.jelenlegiHet() + ". h�ten a perselyben annyinak kell lennie, amit a program �r. Ha nem annyi van benne p�told!\n" +
							"Term�szetesen ezt az �rt�ket a k�s�bbiek folyam�n lehet v�ltoztatni (b�r nem aj�nlott),\n�gy kevesebb/t�bb p�nz gy�lik �ssze.", "Inf�", 1);
						}
						adatok.setAlapOsszeg((int)spAlapOsszeg.getValue());
						adatok.setNovRata((int)spNovRata.getValue());							
						adatok.setArfolyam((String) jComboBox.getSelectedItem());
						
						boolean opened = false;
						window.createTableIfNotExists(opened, dbc, adatok, false);
						dbc.updateDB(adatok);
						try {
							dbc.adatbazisErtekek(adatok);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						dbc.close();
						if(window.elsoInditas){
							JOptionPane.showMessageDialog(null, "Mivel m�r a(z) " + new PerselyMain().jelenlegiHet() + ". h�ten vagyunk, �gy a fizetend� �sszeg is a h�thez igazodik.\n" + 
									"Alap�rtelmezetten fogja jelezni mennyit kellett a h�ten beletenni, �s mennyi lesz a k�vetkez�.\n" +
									"A k�vetkez� h�tf�i napon lehet befizetni majd a k�vetkez� �sszeget.", "Figyelem!", 2);
						}
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				if(window.elsoInditas){
					cancelButton.setEnabled(false);
				}
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		if(window.elsoInditas){
			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		} else {
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		setModal(true);
		
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - getWidth()) / 2;
		final int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
		setVisible(true);
	}
	
	protected JButton getCancelButton() {
		return cancelButton;
	}

	protected FocusListener focus(){
		FocusListener fcsListener = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				dumpInfo(e);
			}

			@Override
			public void focusLost(FocusEvent e) {
				dumpInfo(e);
			}

			private void dumpInfo(FocusEvent e) {
				final Component c = e.getComponent();
				if (c instanceof JTextField) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							((JTextField) c).setText(((JTextField) c).getText());
							((JTextField) c).selectAll();
						}
					});
				}
			}
		};
		return fcsListener;
	}
}
