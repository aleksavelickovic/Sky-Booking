package com.ftn.PrviMavenVebProjekat.repository;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Aerodrom;

public interface AerodromiRepository {
	public Aerodrom findOne(Long id);

	public List<Aerodrom> findAll();

	public int save(Aerodrom Aerodrom);

	public int update(Aerodrom Aerodrom);

	public int delete(Long id);
}
