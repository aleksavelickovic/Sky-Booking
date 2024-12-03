package com.ftn.PrviMavenVebProjekat.model;

public class Lokacija {

	private Long id;
	private String Grad;
	private String Drzava;
	private String Kontinent;

	public Lokacija(Long id, String grad, String drzava, String kontinent) {
		super();
		this.id = id;
		Grad = grad;
		Drzava = drzava;
		Kontinent = kontinent;
	}

	public Lokacija(String grad, String drzava, String kontinent) {
		super();
		Grad = grad;
		Drzava = drzava;
		Kontinent = kontinent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGrad() {
		return Grad;
	}

	public void setGrad(String grad) {
		Grad = grad;
	}

	public String getDrzava() {
		return Drzava;
	}

	public void setDrzava(String drzava) {
		Drzava = drzava;
	}

	public String getKontinent() {
		return Kontinent;
	}

	public void setKontinent(String kontinent) {
		Kontinent = kontinent;
	}

}
