package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Aerodrom;

public interface AerodromiService {
	
	Aerodrom findOne(Long id);

	List<Aerodrom> findAll();

	Aerodrom save(Aerodrom aerodrom);

	Aerodrom update(Aerodrom aerodrom);
	
	Aerodrom delete(Long id);

}
