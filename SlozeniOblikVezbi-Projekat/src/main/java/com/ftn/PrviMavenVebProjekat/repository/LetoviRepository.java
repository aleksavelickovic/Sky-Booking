package com.ftn.PrviMavenVebProjekat.repository;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Let;

public interface LetoviRepository {
	public Let findOne(Long id);

	public List<Let> findAll();

	public int save(Let Let);

	public int update(Let Let);

	public int delete(Long id);
}
