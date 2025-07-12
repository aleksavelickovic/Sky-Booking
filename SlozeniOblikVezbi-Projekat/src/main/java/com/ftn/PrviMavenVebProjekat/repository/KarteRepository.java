
package com.ftn.PrviMavenVebProjekat.repository;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Karta;

public interface KarteRepository {
	public Karta findOne(Long id);

	public List<Karta> findAll();

	public int save(Karta karta);

	public int update(Karta karta);

	public int delete(Long id);
}
