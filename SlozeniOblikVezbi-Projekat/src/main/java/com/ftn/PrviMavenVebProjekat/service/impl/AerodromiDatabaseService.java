package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.PrviMavenVebProjekat.model.Aerodrom;
import com.ftn.PrviMavenVebProjekat.repository.AerodromiRepository;
import com.ftn.PrviMavenVebProjekat.service.AerodromiService;

public class AerodromiDatabaseService implements AerodromiService {

	@Autowired
	private AerodromiRepository repository;

	@Override
	public Aerodrom findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Aerodrom> findAll() {
		return repository.findAll();
	}

	@Override
	public Aerodrom save(Aerodrom aerodrom) {
		repository.save(aerodrom);
		return aerodrom;
	}

	@Override
	public Aerodrom update(Aerodrom aerodrom) {
		repository.update(aerodrom);
		return aerodrom;
	}

	@Override
	public Aerodrom delete(Long id) {
		Aerodrom aerodrom = repository.findOne(id);
		if (aerodrom != null) {
			repository.delete(id);
		}
		return aerodrom;
	}
}
