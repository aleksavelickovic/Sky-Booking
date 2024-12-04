package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;

public interface KorisniciService {

	Korisnik findOne(Long id);

	List<Korisnik> findAll();

	Korisnik save(Korisnik Korisnik);

	Korisnik update(Korisnik Korisnik);

	void delete(Long id);
}
