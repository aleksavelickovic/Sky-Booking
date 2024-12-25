package com.ftn.PrviMavenVebProjekat.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Korisnik {

	private Long id;
	private String korisnickoIme;
	private String lozinka;
	private String email;
	private String ime;
	private String prezime;
	private Date datumRodjenja;
	private Timestamp datumIVremeRegistracije;
	private Uloga uloga;

	public Korisnik() {
	}

	public Korisnik(long id, String korisnickoIme, String lozinka, String email, String ime, String prezime,
			Date datumRodjenja, Timestamp datumIVremeRegistracije, Uloga uloga) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.datumIVremeRegistracije = datumIVremeRegistracije;
		this.uloga = uloga;
	}

	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime,
			Date datumRodjenja, Timestamp datumIVremeRegistracije, Uloga uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.datumIVremeRegistracije = datumIVremeRegistracije;
		this.uloga = uloga;
	}

	@Override
	public String toString() {
		return id + ";" + korisnickoIme + ";" + lozinka + ";" + email + ";" + ime + ";" + prezime + ";" + datumRodjenja
				+ ";" + datumIVremeRegistracije + ";" + uloga;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Date getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public Timestamp getDatumIVremeRegistracije() {
		return datumIVremeRegistracije;
	}

	public void setDatumIVremeRegistracije(Timestamp datumIVremeRegistracije) {
		this.datumIVremeRegistracije = datumIVremeRegistracije;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

}
