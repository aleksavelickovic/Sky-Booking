package com.ftn.PrviMavenVebProjekat.model;

public class Aerodrom {

	private Long id;
	private String oznaka;
	private Lokacija lokacija;

	public Aerodrom(Long id, String oznaka, Lokacija lokacija) {
		super();
		this.id = id;
		this.oznaka = oznaka;
		this.lokacija = lokacija;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public Lokacija getLokacija() {
		return lokacija;
	}

	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}

	@Override
	public String toString() {
		return "Aerodrom [oznaka=" + oznaka + ", lokacija=" + lokacija + "]";
	}

}
