package com.ftn.PrviMavenVebProjekat.repository;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Avion;

public interface AvioniRepository {
	public Avion findOne(Long id);

	public List<Avion> findAll();

	public int save(Avion Avion);

	public int update(Avion Avion);

	public int delete(Long id);
}
