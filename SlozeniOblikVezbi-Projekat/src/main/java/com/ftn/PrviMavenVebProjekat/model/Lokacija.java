package com.ftn.PrviMavenVebProjekat.model;

public class Lokacija {

	private Long id;
	private String Grad;
	private String Drzava;
	private Kontinenti Kontinent;
	private String putanjaDoSlike;

	public Lokacija() {
	} // Default construcor mora postojati zbog @ModelAttribute

	public Lokacija(Long id, String grad, String drzava, Kontinenti kontinent, String putanjaDoSlike) {
		super();
		this.id = id;
		Grad = grad;
		Drzava = drzava;
		Kontinent = kontinent;
		this.putanjaDoSlike = putanjaDoSlike;
	}

	public Lokacija(String grad, String drzava, Kontinenti kontinent, String putanjaDoSlike) {
		super();
		Grad = grad;
		Drzava = drzava;
		Kontinent = kontinent;
		this.putanjaDoSlike = putanjaDoSlike;
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

	public String getPutanjaDoSlike() {
		return putanjaDoSlike;
	}

	public void setPutanjaDoSlike(String putanjaDoSlike) {
		this.putanjaDoSlike = putanjaDoSlike;
	}

	@Override
	public String toString() {
		return id + ";" + Grad + ";" + Drzava + ";" + Kontinent;
	}

}
