package com.ftn.PrviMavenVebProjekat.model;

public enum Kontinenti {

	Evropa("Evropa"), Amerika("Amerika"), Azija("Azija"), Australija("Australija"), Afrika("Afrika"),
	Antartika("Antartika"), Okeanija("Okeanija");

	private final String displayName;

	Kontinenti(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}
}
