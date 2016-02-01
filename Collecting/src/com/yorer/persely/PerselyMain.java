package com.yorer.persely;

public class PerselyMain {

	public int ciklusosSzamolas(PerselyAdatok adatok) {
		int hetek = 52;
		int hetenteNovekvoErtek;
		int vegOsszeg = adatok.getAlapOsszeg();
		
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
	
	public int hatraVan(){
		return 0; //FIXME innen folytatni
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
}
