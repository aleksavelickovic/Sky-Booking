package com.ftn.PrviMavenVebProjekat.model;

public class Lokacija {

	private Long id;
	private String Grad;
	private String Drzava;
	private Kontinenti Kontinent;
	
	public Lokacija() {} //Default construcor mora postojati zbog @ModelAttribute

	public Lokacija(Long id, String grad, String drzava, Kontinenti kontinent) {
		super();
		this.id = id;
		Grad = grad;
		Drzava = drzava;
		Kontinent = kontinent;
	}

	public Lokacija(String grad, String drzava, Kontinenti kontinent) {
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

	public Kontinenti getKontinent() {
		return Kontinent;
	}

	public void setKontinent(Kontinenti kontinent) {
		Kontinent = kontinent;
	}

	@Override
	public String toString() {
		return id + ";" + Grad + ";" + Drzava + ";" + Kontinent;
	}

}
