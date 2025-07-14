package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Karta;
import com.ftn.PrviMavenVebProjekat.repository.KarteRepository;
import com.ftn.PrviMavenVebProjekat.service.KarteService;

@Service
public class KarteServiceDatabaseImpl implements KarteService {
	@Autowired
	private KarteRepository repository;

	@Override
	public Karta findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Karta> findAll() {
		return repository.findAll();
	}

	@Override
	public Karta save(Karta karta) {
		repository.save(karta);
		return karta;
	}

	@Override
	public Karta update(Karta karta) {
		repository.update(karta);
		return karta;
	}

	@Override
	public Karta delete(Long id) {
		Karta karta = repository.findOne(id);
		if (karta != null) {
			repository.delete(id);
		}
		return karta;
	}
}
