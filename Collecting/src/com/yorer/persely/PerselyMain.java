package com.yorer.persely;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

public class PerselyMain {

	public int ciklusosSzamolas(PerselyAdatok adatok) {
		int hetek = evVegeHet(adatok);
		int hetenteNovekvoErtek;
		int vegOsszeg = 0;
		
		for (int hanyadikHet = 1; hanyadikHet <= hetek; hanyadikHet++) {
			hetenteNovekvoErtek = Math.multiplyExact(adatok.getNovRata(), hanyadikHet);
			vegOsszeg += hetenteNovekvoErtek;
		}
		return vegOsszeg;
	}

	public int hetenFizetendo(PerselyAdatok adatok, int hanyadikHet){
		int counter = 0;
		for(int i = 0; i < hanyadikHet; i++){
			counter += adatok.getNovRata();
		}
		return counter;
	}
	
	public int koviHet(PerselyAdatok adatok, int hanyadikHet){
		return hetenFizetendo(adatok, hanyadikHet) + adatok.getNovRata();
	}
	
	public int hatraVan(PerselyAdatok adatok){
		return ciklusosSzamolas(adatok) - (adatok.getAlapOsszeg()); 
	}
	
	public String arfolyam(String tipus) {
		String getTipus;
		switch (tipus) {
		case "Font":
			getTipus = "₤";
			break;
		case "Forint":
			getTipus = "Ft";
			break;
		case "Euró":
			getTipus = "€";
			break;
		default:
			getTipus = null;
			break;
		}
		return getTipus;
	}
	
	public void setStatusz(PerselyAdatok adatok, PerselyWindow window){
		if(jelenlegiHet() != fizetettHet(adatok)){
			window.getLblBefizetve().setForeground(Color.RED);
			window.getLblBefizetve().setText("Tartozás!");
			window.getBtnMehet().setText("Befizettem");
			window.getBtnMehet().setEnabled(true);
		} else {
			window.getLblBefizetve().setText("Befizetve!");
			window.getLblBefizetve().setForeground(Color.GREEN);
			window.getBtnMehet().setText("Fizetve");
			window.getBtnMehet().setEnabled(false);
		}
	}
	
	protected int evKezdeteHet(PerselyAdatok adatok){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(adatok.getEvKezdete());
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	protected int evVegeHet(PerselyAdatok adatok){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(adatok.getEvVege());
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	protected int fizetettHet(PerselyAdatok adatok){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(adatok.getJelenlegiDatum());
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	protected int jelenlegiHet(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
}
