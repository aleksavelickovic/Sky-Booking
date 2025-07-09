package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Let;
import com.ftn.PrviMavenVebProjekat.repository.LetoviRepository;
import com.ftn.PrviMavenVebProjekat.service.LetoviService;

@Service
public class LetoviDatabaseServiceImpl implements LetoviService {

	@Autowired
	private LetoviRepository repository;

	@Override
	public Let findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Let> findAll() {
		return repository.findAll();
	}

	@Override
	public Let save(Let let) {
		repository.save(let);
		return let;
	}

	@Override
	public Let update(Let let) {
		repository.update(let);
		return let;
	}

	@Override
	public Let delete(Long id) {
		Let let = repository.findOne(id);
		if (let != null) {
			repository.delete(id);
		}
		return let;
	}
}
