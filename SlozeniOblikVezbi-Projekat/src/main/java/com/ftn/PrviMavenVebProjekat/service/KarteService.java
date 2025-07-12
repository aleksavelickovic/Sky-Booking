package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Karta;

public interface KarteService {
	Karta findOne(Long id);

	List<Karta> findAll();

	Karta save(Karta karta);

	Karta update(Karta karta);

	Karta delete(Long id);
}
