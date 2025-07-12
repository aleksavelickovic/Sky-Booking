package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Rezervacija;

public interface RezervacijeService {
	Rezervacija findOne(Long id);

	List<Rezervacija> findAll();

	Rezervacija save(Rezervacija rezervacija);

	Rezervacija update(Rezervacija rezervacija);

	Rezervacija delete(Long id);
}
