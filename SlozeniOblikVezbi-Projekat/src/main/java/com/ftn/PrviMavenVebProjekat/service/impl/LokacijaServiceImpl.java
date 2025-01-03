package com.ftn.PrviMavenVebProjekat.service.impl;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Kontinenti;
import com.ftn.PrviMavenVebProjekat.model.Lokacija;
import com.ftn.PrviMavenVebProjekat.service.LokacijaService;

@Service
@Qualifier("PrviServisZaLokacije")
public class LokacijaServiceImpl implements LokacijaService {

	@Value("${paths.baseResourcePath}")
	private String basePath;

	private Map<Long, Lokacija> readFromFile() {

		Map<Long, Lokacija> lokacije = new HashMap<>();
		Long nextId = 1L;

		try {
			Path path = Paths.get(basePath + "lokacije.csv");
			List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

			for (String line : lines) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;

				String[] tokens = line.split(",");
				Long id = Long.parseLong(tokens[0]);
				String grad = tokens[1];
				String drzava = tokens[2];
				Kontinenti kontinent = Kontinenti.valueOf(tokens[3]);

				lokacije.put(id, new Lokacija(id, grad, drzava, kontinent));

				if (nextId < id)
					nextId = id;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lokacije;
	}

	private Map<Long, Lokacija> saveToFile(Map<Long, Lokacija> lokacije) {

		Map<Long, Lokacija> lokacijeReturn = new HashMap<>();

		try {
			Path path = Paths.get(basePath + "lokacije.txt");
			List<String> lines = new ArrayList<>();

			for (Lokacija Lokacija : lokacije.values()) {
				String line = Lokacija.toString();
				lines.add(line);
				lokacijeReturn.put(Lokacija.getId(), Lokacija);
			}

			Files.write(path, lines, Charset.forName("UTF-8"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lokacijeReturn;
	}

	@Override
	public Lokacija findOne(Long id) {
		// TODO Auto-generated method stub
		Map<Long, Lokacija> lokacije = readFromFile();
		return lokacije.get(id);
	}

	@Override
	public List<Lokacija> findAll() {
		// TODO Auto-generated method stub
		Map<Long, Lokacija> lokacije = readFromFile();
		return new ArrayList<>(lokacije.values());
	}

	@Override
	public Lokacija save(Lokacija lokacija) {
		// TODO Auto-generated method stub
		Map<Long, Lokacija> lokacije = readFromFile();
		Long nextId = nextId(lokacije);

		if (lokacija.getId() == null) {
			lokacija.setId(nextId + 1);
		}

		lokacije.put(lokacija.getId(), lokacija);
		saveToFile(lokacije);
		return null;
	}

	@Override
	public Lokacija update(Lokacija lokacija) {
		// TODO Auto-generated method stub
		Map<Long, Lokacija> lokacije = readFromFile();
		lokacije.replace(lokacija.getId(), lokacija);
		saveToFile(lokacije);
		return lokacija;
	}

	@Override
	public Lokacija delete(Long id) {
		// TODO Auto-generated method stub
		Map<Long, Lokacija> knjige = readFromFile();
		knjige.remove(id);
		saveToFile(knjige);
		return null;
	}

	private Long nextId(Map<Long, Lokacija> map) {
		Long nextId = 0L;

		for (Long id : map.keySet()) {
			if (id > nextId) {
				nextId = id;
			}
		}

		return nextId;
	}

}
