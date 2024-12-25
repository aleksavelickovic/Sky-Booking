package com.ftn.PrviMavenVebProjekat.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.repository.KorisniciRepository;

@Repository
public class KorisniciRepositoryImpl implements KorisniciRepository{

	@Override
	public Korisnik findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Korisnik> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(Korisnik Korisnik) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Korisnik Korisnik) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
