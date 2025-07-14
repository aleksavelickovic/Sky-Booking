package com.ftn.PrviMavenVebProjekat.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Rezervacija {

	private Long id;
	private ArrayList<Karta> karte = new ArrayList<>();
	private Timestamp datumIVremeKreiranja;
	private int ukupnaCena;

	public Rezervacija(Long id, Timestamp datumIVremeKreiranja, int ukupnaCena) {
		super();
		this.id = id;
		this.datumIVremeKreiranja = datumIVremeKreiranja;
		for (Karta karta : this.karte) {
			ukupnaCena = ukupnaCena + karta.getCena();
		}
	}

	public Rezervacija(Timestamp datumIVremeKreiranja, int ukupnaCena) {
		super();
		this.datumIVremeKreiranja = datumIVremeKreiranja;
		for (Karta karta : this.karte) {
			ukupnaCena = ukupnaCena + karta.getCena();
		}
	}

	public Rezervacija() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<Karta> getKarte() {
		return karte;
	}

	public void setKarte(ArrayList<Karta> karte) {
		this.karte = karte;
	}

	public Timestamp getDatumIVremeKreiranja() {
		return datumIVremeKreiranja;
	}

	public void setDatumIVremeKreiranja(Timestamp datumIVremeKreiranja) {
		this.datumIVremeKreiranja = datumIVremeKreiranja;
	}

	public int getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(int ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	@Override
	public String toString() {
		return "Rezervacija [id=" + id + ", karte=" + karte + ", datumIVremeKreiranja=" + datumIVremeKreiranja
				+ ", ukupnaCena=" + ukupnaCena + "]";
	}

}
