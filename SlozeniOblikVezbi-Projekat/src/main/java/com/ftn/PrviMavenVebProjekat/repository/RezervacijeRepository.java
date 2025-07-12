
package com.ftn.PrviMavenVebProjekat.repository;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Rezervacija;

public interface RezervacijeRepository {
	public Rezervacija findOne(Long id);

	public List<Rezervacija> findAll();

	public int save(Rezervacija rezervacija);

	public int update(Rezervacija rezervacija);

	public int delete(Long id);
}
