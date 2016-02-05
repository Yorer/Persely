package com.yorer.persely;

import java.util.Date;

public class PerselyAdatok {
	
	private int id;
	private int alapOsszeg;
	private int novRata;
	private String arfolyam;
	private Date jelenlegiDatum;
	private Date evKezdete;
	private Date evVege;
	
	protected int getId() {
		return id;
	}
	protected void setId(int id) {
		this.id = id;
	}
	protected int getAlapOsszeg() {
		return alapOsszeg;
	}
	protected void setAlapOsszeg(int alapOsszeg) {
		this.alapOsszeg = alapOsszeg;
	}
	protected int getNovRata() {
		return novRata;
	}
	protected void setNovRata(int novRata) {
		this.novRata = novRata;
	}
	protected String getArfolyam() {
		return arfolyam;
	}
	protected void setArfolyam(String arfolyam) {
		this.arfolyam = arfolyam;
	}
	protected Date getJelenlegiDatum() {
		return jelenlegiDatum;
	}
	protected void setJelenlegiDatum(Date jelenlegiDatum) {
		this.jelenlegiDatum = jelenlegiDatum;
	}
	protected Date getEvKezdete() {
		return evKezdete;
	}
	protected void setEvKezdete(Date evKezdete) {
		this.evKezdete = evKezdete;
	}
	protected Date getEvVege() {
		return evVege;
	}
	protected void setEvVege(Date evVege) {
		this.evVege = evVege;
	}
}
