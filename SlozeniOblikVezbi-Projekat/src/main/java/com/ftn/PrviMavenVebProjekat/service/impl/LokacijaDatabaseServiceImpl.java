package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Lokacija;
import com.ftn.PrviMavenVebProjekat.repository.LokacijeRepository;
import com.ftn.PrviMavenVebProjekat.service.LokacijaService;

@Service
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lokacija update(Lokacija lokacija) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
