package com.ftn.PrviMavenVebProjekat.model;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lokacije {
	private Map<Long, Lokacija> lokacije = new HashMap<>();
	private long nextId = 1L;

	public Lokacije() {

		try {
			Path path = Paths.get(getClass().getClassLoader().getResource("lokacije.txt").toURI());
			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

			for (String line : lines) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				
				String[] tokens = line.split(";");
				Long id = Long.parseLong(tokens[0]);
				String grad = tokens[1];
				String drzava = tokens[2];
				String kontinent = tokens[3];
				
				lokacije.put(id, new Lokacija(id, grad, drzava, kontinent));
				
				if(nextId<id)
					nextId=id;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Lokacija findOne(Long id) {
		return lokacije.get(id);
	}

	public List<Lokacija> findAll() {
		return new ArrayList<Lokacija>(lokacije.values());
	}

	public Lokacija save(Lokacija Lokacija) {
		if (Lokacija.getId() == null) {
			Lokacija.setId(++nextId);
		}
		lokacije.put(Lokacija.getId(), Lokacija);
		return Lokacija;
	}

	public List<Lokacija> save(List<Lokacija> Lokacije) {
		List<Lokacija> ret = new ArrayList<>();

		for (Lokacija k : Lokacije) {

			Lokacija saved = save(k);

			if (saved != null) {
				ret.add(saved);
			}
		}
		return ret;
	}


	public Lokacija delete(Long id) {
		if (!lokacije.containsKey(id)) {
//			lokacije.remove(id-1);
			throw new IllegalArgumentException("Pokusali ste da obrisete nepostojecu lokaciju!!!");
		}
		Lokacija Lokacija = lokacije.get(id);
		if (Lokacija != null) {
			lokacije.remove(id);
		}
		return Lokacija;
	}

	public void delete(List<Long> ids) {
		for (Long id : ids) {

			delete(id);
		}
	}

	public List<Lokacija> FindByGrad(String naziv) {
		List<Lokacija> ret = new ArrayList<>();

		for (Lokacija l : lokacije.values()) {
			if (naziv.startsWith(l.getGrad())) {
				ret.add(l);
			}
		}

		return ret;
	}
}
