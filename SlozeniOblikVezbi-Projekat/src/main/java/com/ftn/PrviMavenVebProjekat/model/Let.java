package com.ftn.PrviMavenVebProjekat.model;

import java.time.LocalDateTime;

public class Let {

	private Long id;
	private String oznaka;
	private Aerodrom polaziste;
	private Aerodrom odrediste;
	private Avion avion;
	private LocalDateTime terminPolaska;
	private int trajanjeLeta;
	private int cena;
	private Boolean naAkciji;
	private int brojMesta;
	private String razlogOtkaza;

	public Let(Long id, String oznaka, Aerodrom polaziste, Aerodrom odrediste, Avion avion, LocalDateTime terminPolaska,
			int trajanjeLeta, int cena, Boolean naAkciji, int brojMesta, String razlogOtkaza) {
		super();
		this.id = id;
		this.oznaka = oznaka;
		this.polaziste = polaziste;
		this.odrediste = odrediste;
		this.avion = avion;
		this.terminPolaska = terminPolaska;
		this.trajanjeLeta = trajanjeLeta;
		this.cena = cena;
		this.naAkciji = naAkciji;
		this.brojMesta = brojMesta;
		this.razlogOtkaza = razlogOtkaza;
	}

	public Let(String oznaka, Aerodrom polaziste, Aerodrom odrediste, Avion avion, LocalDateTime terminPolaska,
			int trajanjeLeta, int cena, Boolean naAkciji) {
		super();
		this.oznaka = oznaka;
		this.polaziste = polaziste;
		this.odrediste = odrediste;
		this.avion = avion;
		this.terminPolaska = terminPolaska;
		this.trajanjeLeta = trajanjeLeta;
		this.cena = cena;
		this.naAkciji = naAkciji;

		this.brojMesta = this.avion.getBrojKolona() * this.avion.getBrojRedova();

		this.razlogOtkaza = "";
	}

	public Let() { // Prazan constructor zbog @ModelAttribute
		super();
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

	public Aerodrom getPolaziste() {
		return polaziste;
	}

	public void setPolaziste(Aerodrom polaziste) {
		this.polaziste = polaziste;
	}

	public Aerodrom getOdrediste() {
		return odrediste;
	}

	public void setOdrediste(Aerodrom odrediste) {
		this.odrediste = odrediste;
	}

	public Avion getAvion() {
		return avion;
	}

	public void setAvion(Avion avion) {
		this.avion = avion;
	}

	public LocalDateTime getTerminPolaska() {
		return terminPolaska;
	}

	public void setTerminPolaska(LocalDateTime terminPolaska) {
		this.terminPolaska = terminPolaska;
	}

	public int getTrajanjeLeta() {
		return trajanjeLeta;
	}

	public void setTrajanjeLeta(int trajanjeLeta) {
		this.trajanjeLeta = trajanjeLeta;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public Boolean getNaAkciji() {
		return naAkciji;
	}

	public void setNaAkciji(Boolean naAkciji) {
		this.naAkciji = naAkciji;
	}

	public int getBrojMesta() {
		return brojMesta;
	}

	public void setBrojMesta(int brojMesta) {
		this.brojMesta = brojMesta;
	}

	public String getRazlogOtkaza() {
		return razlogOtkaza;
	}

	public void setRazlogOtkaza(String razlogOtkaza) {
		this.razlogOtkaza = razlogOtkaza;
	}

	@Override
	public String toString() {
		return "Let [id=" + id + ", oznaka=" + oznaka + ", polaziste=" + polaziste + ", odrediste=" + odrediste
				+ ", avion=" + avion + ", terminPolaska=" + terminPolaska + ", trajanjeLeta=" + trajanjeLeta + ", cena="
				+ cena + ", naAkciji=" + naAkciji + ", brojMesta=" + brojMesta + "]";
	}

}
