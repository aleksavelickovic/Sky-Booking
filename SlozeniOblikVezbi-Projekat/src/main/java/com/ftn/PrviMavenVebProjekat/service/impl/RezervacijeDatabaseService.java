package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Rezervacija;
import com.ftn.PrviMavenVebProjekat.repository.RezervacijeRepository;
import com.ftn.PrviMavenVebProjekat.service.RezervacijeService;

@Service
public class RezervacijeDatabaseService implements RezervacijeService {
	@Autowired
	private RezervacijeRepository repository;

	@Override
	public Rezervacija findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Rezervacija> findAll() {
		return repository.findAll();
	}

	@Override
	public Rezervacija save(Rezervacija rezervacija) {
		repository.save(rezervacija);
		return rezervacija;
	}

	@Override
	public Rezervacija update(Rezervacija rezervacija) {
		repository.update(rezervacija);
		return rezervacija;
	}

	@Override
	public Rezervacija delete(Long id) {
		Rezervacija rezervacija = repository.findOne(id);
		if (rezervacija != null) {
			repository.delete(id);
		}
		return rezervacija;
	}
}
