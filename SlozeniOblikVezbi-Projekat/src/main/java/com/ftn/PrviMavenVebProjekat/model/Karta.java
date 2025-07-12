package com.ftn.PrviMavenVebProjekat.model;

public class Karta {

	private Long id;
	private Let let;
	private String brojSedista;
	private int cena;
	private String imeIPrezimePutnika;
	private String brojPasosa;

	public Karta(Long id, Let let, String brojSedista, String imeIPrezimePutnika, String brojPasosa) {
		super();
		this.id = id;
		this.let = let;
		this.brojSedista = brojSedista;
		this.cena = let.getCena();
		this.imeIPrezimePutnika = imeIPrezimePutnika;
		this.brojPasosa = brojPasosa;
	}

	public Karta(Let let, String brojSedista, String imeIPrezimePutnika, String brojPasosa) {
		super();
		this.let = let;
		this.brojSedista = brojSedista;
		this.cena = let.getCena();
		this.imeIPrezimePutnika = imeIPrezimePutnika;
		this.brojPasosa = brojPasosa;
	}

	public Karta() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Let getLet() {
		return let;
	}

	public void setLet(Let let) {
		this.let = let;
	}

	public String getBrojSedista() {
		return brojSedista;
	}

	public void setBrojSedista(String brojSedista) {
		this.brojSedista = brojSedista;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public String getImeIPrezimePutnika() {
		return imeIPrezimePutnika;
	}

	public void setImeIPrezimePutnika(String imeIPrezimePutnika) {
		this.imeIPrezimePutnika = imeIPrezimePutnika;
	}

	public String getBrojPasosa() {
		return brojPasosa;
	}

	public void setBrojPasosa(String brojPasosa) {
		this.brojPasosa = brojPasosa;
	}

	@Override
	public String toString() {
		return "Karta [id=" + id + ", let=" + let + ", brojSedista=" + brojSedista + ", cena=" + cena
				+ ", imeIPrezimePutnika=" + imeIPrezimePutnika + ", brojPasosa=" + brojPasosa + "]";
	}

}
