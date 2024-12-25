package com.ftn.PrviMavenVebProjekat.repository;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;

public interface KorisniciRepository {

	public Korisnik findOne(Long id);

	public List<Korisnik> findAll();

	public int save(Korisnik Korisnik);

	public int update(Korisnik Korisnik);

	public int delete(Long id);
}
