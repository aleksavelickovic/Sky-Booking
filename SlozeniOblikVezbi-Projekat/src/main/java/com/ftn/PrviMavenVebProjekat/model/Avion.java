package com.ftn.PrviMavenVebProjekat.model;

public class Avion {

	private Long id;
	private String naziv;
	private int brojKolona;
	private int brojRedova;

	public Avion(Long id, String naziv, int brojKolona, int brojRedova) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.brojKolona = brojKolona;
		this.brojRedova = brojRedova;
	}

	public Avion(String naziv, int brojKolona, int brojRedova) {
		super();
		this.naziv = naziv;
		this.brojKolona = brojKolona;
		this.brojRedova = brojRedova;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getBrojKolona() {
		return brojKolona;
	}

	public void setBrojKolona(int brojKolona) {
		this.brojKolona = brojKolona;
	}

	public int getBrojRedova() {
		return brojRedova;
	}

	public void setBrojRedova(int brojRedova) {
		this.brojRedova = brojRedova;
	}

	@Override
	public String toString() {
		return "Avion [id=" + id + ", naziv=" + naziv + ", brojKolona=" + brojKolona + ", brojRedova=" + brojRedova
				+ "]";
	}

}
