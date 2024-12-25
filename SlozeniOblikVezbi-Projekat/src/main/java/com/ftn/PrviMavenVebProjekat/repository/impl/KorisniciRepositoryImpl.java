package com.ftn.PrviMavenVebProjekat.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Uloga;
import com.ftn.PrviMavenVebProjekat.repository.KorisniciRepository;

@Repository
public class KorisniciRepositoryImpl implements KorisniciRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class KorisnikRowCallBackhandler implements RowCallbackHandler {

		private Map<Long, Korisnik> korisnici = new HashMap<>();

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			String korisnickoIme = rs.getString(index++);
			String lozinka = rs.getString(index++);
			String email = rs.getString(index++);
			String ime = rs.getString(index++);
			String prezime = rs.getString(index++);
			String datumRodjenja = rs.getDate(index++).toString();
			LocalDateTime datumIVremeRegistracije = LocalDateTime.parse(rs.getString(index++));
			Uloga uloga = Uloga.valueOf(rs.getString(index++));

			Korisnik korisnik = korisnici.get(id);
			if (korisnik == null) {
				korisnik = new Korisnik(korisnickoIme, lozinka, email, ime, prezime, datumRodjenja,
						datumIVremeRegistracije, uloga);
				korisnici.put(korisnik.getId(), korisnik);
			}
		}

		public List<Korisnik> getKorisnici() {
			return new ArrayList<>(korisnici.values());
		}
	}

	@Override
	public Korisnik findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Korisnik> findAll() {
		String sql = "SELECT * " + "FROM korisnici k " + "ORDER BY k.id; ";

		KorisnikRowCallBackhandler rowCallbackHandler = new KorisnikRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getKorisnici();
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
