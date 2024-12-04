package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Lokacija;

public interface LokacijaService {

	Lokacija findOne(Long id);

	List<Lokacija> findAll();

	Lokacija save(Lokacija lokacija);

	Lokacija update(Lokacija lokacija);

	void delete(Long id);
}
