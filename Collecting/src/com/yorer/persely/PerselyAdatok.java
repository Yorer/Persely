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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAlapOsszeg() {
		return alapOsszeg;
	}
	public void setAlapOsszeg(int alapOsszeg) {
		this.alapOsszeg = alapOsszeg;
	}
	public int getNovRata() {
		return novRata;
	}
	public void setNovRata(int novRata) {
		this.novRata = novRata;
	}
	public String getArfolyam() {
		return arfolyam;
	}
	public void setArfolyam(String arfolyam) {
		this.arfolyam = arfolyam;
	}
	public Date getJelenlegiDatum() {
		return jelenlegiDatum;
	}
	public void setJelenlegiDatum(Date jelenlegiDatum) {
		this.jelenlegiDatum = jelenlegiDatum;
	}
	public Date getEvKezdete() {
		return evKezdete;
	}
	public void setEvKezdete(Date evKezdete) {
		this.evKezdete = evKezdete;
	}
	public Date getEvVege() {
		return evVege;
	}
	public void setEvVege(Date evVege) {
		this.evVege = evVege;
	}
}
