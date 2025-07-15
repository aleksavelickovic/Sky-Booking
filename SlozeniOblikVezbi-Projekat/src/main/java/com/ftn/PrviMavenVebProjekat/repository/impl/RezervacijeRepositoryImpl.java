package com.ftn.PrviMavenVebProjekat.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.ftn.PrviMavenVebProjekat.model.Karta;
import com.ftn.PrviMavenVebProjekat.model.Let;
import com.ftn.PrviMavenVebProjekat.model.Rezervacija;
import com.ftn.PrviMavenVebProjekat.repository.RezervacijeRepository;
import com.ftn.PrviMavenVebProjekat.service.KorisniciService;
import com.ftn.PrviMavenVebProjekat.service.LetoviService;

@Repository
public class RezervacijeRepositoryImpl implements RezervacijeRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private LetoviService letoviService;
	@Autowired
	private KorisniciService korisniciService;

	private class RezervacijaRowCallBackhandler implements RowCallbackHandler {

		private Map<Long, Rezervacija> rezervacije = new HashMap<>();

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			Long idKorisnika = rs.getLong(index++);
			Timestamp datumIVremeKreiranja = rs.getTimestamp(index++);
			int ukupnaCena = rs.getInt(index++);

			Rezervacija Rezervacija = rezervacije.get(id);
			if (Rezervacija == null) {
				Rezervacija = new Rezervacija(id, korisniciService.findOne(idKorisnika), datumIVremeKreiranja, ukupnaCena);
				rezervacije.put(Rezervacija.getId(), Rezervacija);
			}

			Long kartaId = rs.getLong(index++);
			Long kartaId2 = rs.getLong(index++);
			Long letId = rs.getLong(index++);
			Let let = letoviService.findOne(letId);
			String brojSedista = rs.getString(index++);
			int cena = rs.getInt(index++);
			String imeIPrezimePutnika = rs.getString(index++);
			String brojPasosa = rs.getString(index++);

			Karta karta = new Karta(kartaId, let, brojSedista, imeIPrezimePutnika, brojPasosa);

			Rezervacija.getKarte().add(karta);
		}

		public List<Rezervacija> getRezervacije() {
			return new ArrayList<>(rezervacije.values());
		}
	}

	@Override
	public Rezervacija findOne(Long id) {
		String sql 
				= "SELECT *" 
				+ "FROM rezervacije r" 
				+ "LEFT JOIN rezervacija_karta rk ON rk.rezervacijaId = r.id"
				+ "LEFT JOIN karte k ON rk.kartaId = k.id" + "ORDER BY k.id;";

		RezervacijaRowCallBackhandler rowCallbackHandler = new RezervacijaRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getRezervacije().get(0);
	}

	@Override
	public List<Rezervacija> findAll() {
		String sql 
				= "SELECT *" 
				+ "FROM rezervacije r" 
				+ "LEFT JOIN rezervacija_karta rk ON rk.rezervacijaId = r.id"
				+ "LEFT JOIN karte k ON rk.kartaId = k.id" + "ORDER BY k.id;";

		RezervacijaRowCallBackhandler rowCallbackHandler = new RezervacijaRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getRezervacije();
	}

	@Override
	public int save(Rezervacija rezervacija) {
		PreparedStatementCreator creator = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO karte (ukupnaCena) VALUES (?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setLong(index++, rezervacija.getUkupnaCena());
				return preparedStatement;
			}
		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(creator, keyHolder) == 1;

		if (uspeh) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int update(Rezervacija rezervacija) {
		String sql = "UPDATE karte SET ukupnaCena = ? WHERE id = ?";
		boolean uspeh = jdbcTemplate.update(sql, rezervacija.getUkupnaCena(), rezervacija.getId()) == 1;
		if (uspeh) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM karte WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
}
