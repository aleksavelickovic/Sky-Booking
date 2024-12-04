package com.ftn.PrviMavenVebProjekat.service.impl;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Uloga;
import com.ftn.PrviMavenVebProjekat.service.KorisniciService;

@Service
@Qualifier("KorisniciServis")
public class KorisniciServiceImpl implements KorisniciService {

	@Value("${lokacije.pathToFile}")
	private String pathToFile;

	private Map<Long, Korisnik> readFromFile() {

		Map<Long, Korisnik> korisnici = new HashMap<>();
		Long nextId = 1L;

		try {
			Path path = Paths.get(pathToFile);
//			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

			for (String line : lines) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;

				String[] tokens = line.split(";");
				Long id = Long.parseLong(tokens[0]);
				String korIme = tokens[1];
				String lozinka = tokens[2];
				String email = tokens[3];
				String ime = tokens[4];
				String prezime = tokens[5];
				String datumRodjenja = tokens[6];
				LocalDateTime datumRegistracije = LocalDateTime.parse(tokens[7]);
				Uloga uloga = Uloga.valueOf(tokens[8]);

				korisnici.put(id,
						new Korisnik(0, korIme, lozinka, email, ime, prezime, datumRodjenja, datumRegistracije, uloga));

				if (nextId < id)
					nextId = id;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return korisnici;
	}

	private Map<Long, Korisnik> saveToFile(Map<Long, Korisnik> korisnici) {

		Map<Long, Korisnik> korisniciReturn = new HashMap<>();

		try {
			Path path = Paths.get(pathToFile);
//			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = new ArrayList<>();

			for (Korisnik Korisnik : korisnici.values()) {
				String line = Korisnik.toString();
				lines.add(line);
				korisniciReturn.put(Korisnik.getId(), Korisnik);
			}

			Files.write(path, lines, Charset.forName("UTF-8"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return korisniciReturn;
	}

	@Override
	public Korisnik findOne(Long id) {
		// TODO Auto-generated method stub
		Map<Long, Korisnik> korisnici = readFromFile();
		return korisnici.get(id);
	}

	@Override
	public List<Korisnik> findAll() {
		// TODO Auto-generated method stub
		Map<Long, Korisnik> korisnici = readFromFile();
		return new ArrayList<>(korisnici.values());
	}

	@Override
	public Korisnik save(Korisnik Korisnik) {
		// TODO Auto-generated method stub
		Map<Long, Korisnik> korisnici = readFromFile();
		Long nextId = nextId(korisnici);

		if (Korisnik.getId() == null) {
			Korisnik.setId(nextId + 1);
		}

		korisnici.put(Korisnik.getId(), Korisnik);
		saveToFile(korisnici);
		return null;
	}

	@Override
	public Korisnik update(Korisnik Korisnik) {
		// TODO Auto-generated method stub
		Map<Long, Korisnik> korisnici = readFromFile();
		korisnici.replace(Korisnik.getId(), Korisnik);
		saveToFile(korisnici);
		return Korisnik;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Map<Long, Korisnik> knjige = readFromFile();
		knjige.remove(id);
		saveToFile(knjige);
		return;
	}

	private Long nextId(Map<Long, Korisnik> map) {
		Long nextId = 0L;

		for (Long id : map.keySet()) {
			if (id > nextId) {
				nextId = id;
			}
		}

		return nextId;
	}

}
