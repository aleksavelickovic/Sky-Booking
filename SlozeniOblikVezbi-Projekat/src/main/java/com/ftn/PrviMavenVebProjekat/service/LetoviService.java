package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Let;

public interface LetoviService {
	
	Let findOne(Long id);

	List<Let> findAll();

	Let save(Let let);

	Let update(Let let);
	
	Let delete(Long id);
}
