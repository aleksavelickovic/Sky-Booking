package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.repository.KorisniciRepository;
import com.ftn.PrviMavenVebProjekat.service.KorisniciService;

@Service
@Primary
@Qualifier("KorisniciDatabaseServis")
public class KorisniciDatabaseServiceImpl implements KorisniciService {

	@Autowired
	private KorisniciRepository repository;

	@Override
	public Korisnik findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Korisnik> findAll() {
		return repository.findAll();
	}

	@Override
	public Korisnik save(Korisnik Korisnik) {
		repository.save(Korisnik);
		return Korisnik;
	}

	@Override
	public Korisnik update(Korisnik Korisnik) {
		repository.update(Korisnik);
		return Korisnik;
	}

	@Override
	public Korisnik delete(Long id) {
		Korisnik korisnik = repository.findOne(id);
		if (korisnik != null) {
			repository.delete(id);
		}
		return korisnik;
	}

	@Override
	public Korisnik blockunblock(Korisnik korisnik) {
		repository.blockunblock(korisnik);
		return korisnik;
	}

}
