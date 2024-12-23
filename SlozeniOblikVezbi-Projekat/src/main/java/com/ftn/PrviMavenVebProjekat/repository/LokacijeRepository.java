package com.ftn.PrviMavenVebProjekat.repository;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Lokacija;

public interface LokacijeRepository {
	public Lokacija findOne(Long id);

	public List<Lokacija> findAll();

	public int save(Lokacija lokacija);

	public int update(Lokacija lokacija);

	public int delete(Long id);
}
