package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Lokacija;
import com.ftn.PrviMavenVebProjekat.repository.LokacijeRepository;
import com.ftn.PrviMavenVebProjekat.service.LokacijaService;

@Service
@Primary
@Qualifier("LokacijeDatabaseServis")
public class LokacijaDatabaseServiceImpl implements LokacijaService {

	@Autowired
	private LokacijeRepository repository;

	@Override
	public Lokacija findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Lokacija> findAll() {
		return repository.findAll();
	}

	@Override
	public Lokacija save(Lokacija lokacija) {
		repository.save(lokacija);
		return lokacija;
	}

	@Override
	public Lokacija update(Lokacija lokacija) {
		repository.update(lokacija);
		return lokacija;
	}

	@Override
	public Lokacija delete(Long id) {
		Lokacija lokacija = repository.findOne(id);
		if(lokacija != null) {
			repository.delete(id);
		}
		return lokacija;
	}

}
