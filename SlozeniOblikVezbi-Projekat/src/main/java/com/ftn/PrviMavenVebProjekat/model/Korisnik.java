package com.ftn.PrviMavenVebProjekat.model;

import java.sql.Date;
import java.sql.Timestamp;

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
	private Boolean blokiran;
	private int loyaltyBodovi;
	private Boolean zahtevaLoyalty;
	private int paraPotroseno;

	public Korisnik() {
	}

	public Korisnik(long id, String korisnickoIme, String lozinka, String email, String ime, String prezime,
			Date datumRodjenja, Timestamp datumIVremeRegistracije, Uloga uloga, Boolean blokiran, int loyaltyBodovi,
			boolean zahtevaLoyalty, int paraPotroseno) {
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
		this.blokiran = blokiran;
		this.loyaltyBodovi = loyaltyBodovi;
		this.zahtevaLoyalty = zahtevaLoyalty;
		this.paraPotroseno = paraPotroseno;
	}

	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime, Date datumRodjenja,
			Timestamp datumIVremeRegistracije, Uloga uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.datumIVremeRegistracije = datumIVremeRegistracije;
		this.uloga = uloga;
		this.blokiran = false;
		loyaltyBodovi = -1;
		zahtevaLoyalty = false;
		paraPotroseno = 0;
	}

	@Override
	public String toString() {
		return id + ";" + korisnickoIme + ";" + lozinka + ";" + email + ";" + ime + ";" + prezime + ";" + datumRodjenja
				+ ";" + datumIVremeRegistracije + ";" + uloga + ";" + blokiran + ";" + loyaltyBodovi + ";"
				+ zahtevaLoyalty + ";" + paraPotroseno;
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

	public Boolean getBlokiran() {
		return blokiran;
	}

	public void setBlokiran(Boolean blokiran) {
		this.blokiran = blokiran;
	}

	public int getLoyaltyBodovi() {
		return loyaltyBodovi;
	}

	public void setLoyaltyBodovi(int loyaltyBodovi) {
		this.loyaltyBodovi = loyaltyBodovi;
	}

	public Boolean getZahtevaLoyalty() {
		return zahtevaLoyalty;
	}

	public void setZahtevaLoyalty(Boolean zahtevaLoyalty) {
		this.zahtevaLoyalty = zahtevaLoyalty;
	}

	public int getParaPotroseno() {
		return paraPotroseno;
	}

	public void setParaPotroseno(int paraPotroseno) {
		this.paraPotroseno = paraPotroseno;
	}

}
