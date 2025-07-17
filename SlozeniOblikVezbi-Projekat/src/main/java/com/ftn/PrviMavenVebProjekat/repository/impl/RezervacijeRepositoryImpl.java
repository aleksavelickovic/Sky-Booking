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
			Long id = rs.getLong("rezervacijaId");
			Long idKorisnika = rs.getLong("idKorisnika");
			Timestamp datumIVremeKreiranja = rs.getTimestamp("datumIVremeKreiranja");
			int ukupnaCena = rs.getInt("ukupnaCena");

			System.out.println("id: " + id);
			System.out.println("idkorisnika: " + idKorisnika);
			System.out.println("datumivreme: " + datumIVremeKreiranja);
			System.out.println("UKUPNACENA: " + ukupnaCena);

			Rezervacija rezervacija = rezervacije.get(id);
			if (rezervacija == null) {
				rezervacija = new Rezervacija(id, korisniciService.findOne(idKorisnika), datumIVremeKreiranja,
						ukupnaCena);
				rezervacije.put(rezervacija.getId(), rezervacija);
			}

			Long kartaId = rs.getLong("kartaId");
			if (kartaId != 0) {
				Long letId = rs.getLong("letId");
				Let let = letoviService.findOne(letId);
				String brojSedista = rs.getString("brojSedista");
				int cena = rs.getInt("cena");
				String imeIPrezimePutnika = rs.getString("imeIPrezimePutnika");
				String brojPasosa = rs.getString("brojPasosa");

				Karta karta = new Karta(kartaId, let, brojSedista, imeIPrezimePutnika, brojPasosa);

				rezervacija.getKarte().add(karta);
			}
		}

		public List<Rezervacija> getRezervacije() {
			return new ArrayList<>(rezervacije.values());
		}
	}

	@Override
	public Rezervacija findOne(Long id) {
		String sql = "SELECT " + "  r.id AS rezervacijaId, " + "  r.idKorisnika, " + "  r.datumIVremeKreiranja, "
				+ "  r.ukupnaCena, " + "  rk.kartaId, " + "  k.id AS kartaId2, " + "  k.letId, " + "  k.brojSedista, "
				+ "  k.cena, " + "  k.imeIPrezimePutnika, " + "  k.brojPasosa " + "FROM rezervacije r "
				+ "LEFT JOIN rezervacija_karta rk ON rk.rezervacijaId = r.id "
				+ "LEFT JOIN karte k ON rk.kartaId = k.id " + "WHERE r.id = ? " + "ORDER BY k.id;";

//		sql = "SELECT *\n"
//				+ "FROM rezervacije r\n"
//				+ "LEFT JOIN rezervacija_karta rk ON rk.rezervacijaId = r.id\n"
//				+ "LEFT JOIN karte k ON rk.kartaId = k.id\n"
//				+ "ORDER BY k.id;";

		RezervacijaRowCallBackhandler rowCallbackHandler = new RezervacijaRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getRezervacije().get(0);
	}

	@Override
	public List<Rezervacija> findAll() {
		String sql = "SELECT " + "  r.id AS rezervacijaId, " + "  r.idKorisnika, " + "  r.datumIVremeKreiranja, "
				+ "  r.ukupnaCena, " + "  rk.kartaId, " + "  k.id AS kartaId2, " + "  k.letId, " + "  k.brojSedista, "
				+ "  k.cena, " + "  k.imeIPrezimePutnika, " + "  k.brojPasosa " + "FROM rezervacije r "
				+ "LEFT JOIN rezervacija_karta rk ON rk.rezervacijaId = r.id "
				+ "LEFT JOIN karte k ON rk.kartaId = k.id " +

				"ORDER BY k.id;";

		RezervacijaRowCallBackhandler rowCallbackHandler = new RezervacijaRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getRezervacije();
	}

	@Override
	public int save(Rezervacija rezervacija) {
		PreparedStatementCreator creator = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO rezervacije (idKorisnika, ukupnaCena) VALUES (?, ?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setLong(index++, rezervacija.getKorisnik().getId());
				preparedStatement.setInt(index++, rezervacija.getUkupnaCena());
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
		String sql = "UPDATE rezervacije SET idKorisnika = ?, ukupnaCena = ? WHERE id = ?";
		boolean uspeh = jdbcTemplate.update(sql, rezervacija.getKorisnik().getId(), rezervacija.getUkupnaCena(),
				rezervacija.getId()) == 1;
		if (uspeh) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM rezervacija_karta WHERE rezervacijaId = ?";
		jdbcTemplate.update(sql, id);
		sql = "DELETE FROM rezervacije WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	public void addKartaToRezervacija(Long rezervacijaId, Long kartaId) {
		String sql = "INSERT INTO rezervacija_karta (rezervacijaId, kartaId) VALUES (?, ?)";
		jdbcTemplate.update(sql, rezervacijaId, kartaId); // Use JdbcTemplate for SQL execution
	}

}
