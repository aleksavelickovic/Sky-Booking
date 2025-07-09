package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Avion;
import com.ftn.PrviMavenVebProjekat.repository.AvioniRepository;
import com.ftn.PrviMavenVebProjekat.service.AvioniService;

@Service
public class AvioniDatabaseServceImpl implements AvioniService {
	@Autowired
	private AvioniRepository repository;

	@Override
	public Avion findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Avion> findAll() {
		return repository.findAll();
	}

	@Override
	public Avion save(Avion avion) {
		repository.save(avion);
		return avion;
	}

	@Override
	public Avion update(Avion avion) {
		repository.update(avion);
		return avion;
	}

	@Override
	public Avion delete(Long id) {
		Avion avion = repository.findOne(id);
		if(avion != null) {
			repository.delete(id);
		}
		return avion;
	}
}
