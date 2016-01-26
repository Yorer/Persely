package com.yorer.persely;

public class PerselyMain {

	public String ciklusosSzamolas(int elsoNap, int differencia, int hetek, int megjelenites, String tipus,
			boolean elsoHet, boolean utolsoHet) {
		int vegOsszeg = elsoNap;
		int haviBontas = 0;

		String szoveg = "";

		if (hetek != 0) {
			int hetenteNovekvoErtek;
			for (int hanyadikHet = 1; hanyadikHet <= hetek; hanyadikHet++) {
				hetenteNovekvoErtek = Math.multiplyExact(differencia, hanyadikHet);
				vegOsszeg += hetenteNovekvoErtek;
				haviBontas += hetenteNovekvoErtek;
				szoveg = szoveg.concat(feltetelek(tipus, hanyadikHet, megjelenites, hetek, hetenteNovekvoErtek,
						vegOsszeg, haviBontas, elsoHet, utolsoHet));
				if (hanyadikHet % 4 == 0) {
					haviBontas = 0;
				}
			}
		}
		szoveg = szoveg.trim();
		return szoveg;
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

	public String feltetelek(String tipus, int hanyadikHet, int megjelenites, int hetek, int hetenteNovekvoErtek,
			int vegOsszeg, int haviBontas, boolean elsoHet, boolean utolsoHet) {
		String szoveg = "";
		String szovegReszlet = szoveg(tipus, hanyadikHet, hetek, hetenteNovekvoErtek, vegOsszeg, haviBontas);

		if (elsoHet && hanyadikHet == 1 && !szoveg.equals(szovegReszlet)) {
			szoveg = szoveg.concat(szovegReszlet);
		}

		if (hanyadikHet % megjelenites == 0 && !szoveg.equals(szovegReszlet)) {
			szoveg = szoveg.concat(szovegReszlet);
		}

		if (utolsoHet && hanyadikHet == hetek
				&& !szoveg.equals(szovegReszlet)) {
			szoveg = szoveg.concat(szovegReszlet);
		}
		return szoveg;
	}

	public String szoveg(String tipus, int hanyadikHet, int hetek, int hetenteNovekvoErtek, int vegOsszeg,
			int haviBontas) {
		String szoveg = "";

		if (tipus == "Font" || tipus == "Euró") {
			szoveg = hanyadikHet + ". héten: " + arfolyam(tipus) + hetenteNovekvoErtek + " \r\nPerselyben: "
					+ arfolyam(tipus) + vegOsszeg + ", \r\nNyuszikám fizet: " + arfolyam(tipus)
					+ hetenteNovekvoErtek / 2 + ", \r\nEbben a hónapban " + arfolyam(tipus) + haviBontas
					+ "-(o)t fizetsz be.\r\n\r\n";
		} else {
			szoveg = hanyadikHet + ". héten: " + hetenteNovekvoErtek + " " + arfolyam(tipus) + " \r\nPerselyben: "
					+ vegOsszeg + " " + arfolyam(tipus) + ", \r\nNyuszikám fizet: " + hetenteNovekvoErtek / 2 + " "
					+ arfolyam(tipus) + ", \r\nEbben a hónapban " + haviBontas + " " + arfolyam(tipus)
					+ "-(o)t fizetsz be.\r\n\r\n";
		}
		return szoveg;
	}
}
