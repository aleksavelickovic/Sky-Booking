package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Avion;

public interface AvioniService {
	
	Avion findOne(Long id);

	List<Avion> findAll();

	Avion save(Avion avion);

	Avion update(Avion avion);

	Avion delete(Long id);
}
