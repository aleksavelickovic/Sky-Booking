package com.ftn.PrviMavenVebProjekat.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Rezervacija {

	private Long id;
	private Korisnik korisnik;
	private ArrayList<Karta> karte = new ArrayList<>();
	private Timestamp datumIVremeKreiranja;
	private int ukupnaCena;

	public Rezervacija(Long id, Korisnik korisnik, Timestamp datumIVremeKreiranja, int ukupnaCena) {
		super();
		this.id = id;
		this.korisnik = korisnik;
		this.datumIVremeKreiranja = datumIVremeKreiranja;
		for (Karta karta : this.karte) {
			ukupnaCena = ukupnaCena + karta.getCena();
		}
	}

	public Rezervacija(Korisnik korisnik, Timestamp datumIVremeKreiranja, int ukupnaCena) {
		super();
		this.korisnik = korisnik;
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
	
	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	@Override
	public String toString() {
		return "Rezervacija [id=" + id + ", karte=" + karte + ", datumIVremeKreiranja=" + datumIVremeKreiranja
				+ ", ukupnaCena=" + ukupnaCena + "]";
	}

}
