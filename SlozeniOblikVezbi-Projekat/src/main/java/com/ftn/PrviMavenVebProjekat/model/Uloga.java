package com.ftn.PrviMavenVebProjekat.model;

public enum Uloga {
	PUTNIK("PUTNIK"), ADMIN("ADMIN");

	private final String displayName;

	Uloga(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}
}
