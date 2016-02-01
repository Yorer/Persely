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
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class PreferencesDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;

	public PreferencesDialog(PerselyAdatok adatok) {
		JSpinner spNovRata;
		JSpinner spAlapOsszeg;
		JComboBox<String> jComboBox;
		
		setTitle("Beállítások");
		setResizable(false);
		setLocationRelativeTo(null);
		final JPanel contentPanel = new JPanel();
		
		
		setBounds(100, 100, 275, 164);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("Alapösszeg (perselyben levõ)");
		label.setBounds(6, 14, 177, 14);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("Növekedési ráta");
		label_1.setBounds(6, 39, 177, 14);
		contentPanel.add(label_1);

		SpinnerNumberModel spinnerModelNovRata = new SpinnerNumberModel(1, 1, null, 1);
		spNovRata = new JSpinner(spinnerModelNovRata);
		spNovRata.setToolTipText("Növekedési ráta");
		spNovRata.setBounds(193, 36, 70, 20);
		spNovRata.setValue(adatok.getNovRata());
		JTextField tf3 = ((JSpinner.DefaultEditor) spNovRata.getEditor()).getTextField();
		tf3.addFocusListener(focus());
		contentPanel.add(spNovRata);

		SpinnerNumberModel spinnerModelAlapOsszeg = new SpinnerNumberModel(0, 0, null, 1);
		spAlapOsszeg = new JSpinner(spinnerModelAlapOsszeg);
		spAlapOsszeg.setToolTipText("Alapösszeg");
		spAlapOsszeg.setBounds(193, 11, 70, 20);
		spAlapOsszeg.setValue(adatok.getAlapOsszeg());
		JTextField tf4 = ((JSpinner.DefaultEditor) spAlapOsszeg.getEditor()).getTextField();
		tf4.addFocusListener(focus());
		contentPanel.add(spAlapOsszeg);
		
		JLabel label_4 = new JLabel("Árfolyam");
		label_4.setBounds(6, 67, 177, 14);
		contentPanel.add(label_4);
		
		Values v = new Values();
		String[] items = {v.getFONT(), v.getEURO(), v.getFORINT()};
		jComboBox = new JComboBox(items);
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
						
						adatok.setAlapOsszeg(Integer.parseInt(spAlapOsszeg.getValue().toString()));
						adatok.setNovRata(Integer.parseInt(spNovRata.getValue().toString()));
						adatok.setArfolyam((String) jComboBox.getSelectedItem());
						
						boolean opened = false;
						
						boolean fileExists = new File("resources/persely.db").exists();
						if(!fileExists){
							new File("resources").mkdirs();
							
							opened = dbc.open();
							dbc.createTable();
							dbc.addToDB(adatok);
						}
						if(!opened){
							dbc.open();
						}
						
						dbc.updateDB(adatok);
						dbc.getDatabaseValues(adatok);
						
						dbc.close();
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
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
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - getWidth()) / 2;
		final int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
		setVisible(true);
	}
	
	public FocusListener focus(){
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
