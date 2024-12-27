package com.ftn.PrviMavenVebProjekat.repository.impl;

import java.sql.Connection;
import java.sql.Date;
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
			Date datumRodjenja = rs.getDate(index++);
			Timestamp datumIVremeRegistracije = rs.getTimestamp(index++);
			Uloga uloga = Uloga.valueOf(rs.getString(index++));
			Boolean blokiran = rs.getBoolean(index++);

			Korisnik korisnik = korisnici.get(id);
			if (korisnik == null) {
				korisnik = new Korisnik(id, korisnickoIme, lozinka, email, ime, prezime, datumRodjenja,
						datumIVremeRegistracije, uloga, blokiran);
				korisnici.put(korisnik.getId(), korisnik);
			}
		}

		public List<Korisnik> getKorisnici() {
			return new ArrayList<>(korisnici.values());
		}
	}

	@Override
	public Korisnik findOne(Long id) {
		String sql = "SELECT * FROM korisnici WHERE id = ? ORDER BY id";

		KorisnikRowCallBackhandler rowCallBackhandler = new KorisnikRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallBackhandler, id);

		return rowCallBackhandler.getKorisnici().get(0);
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
		PreparedStatementCreator creator = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO korisnici (korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, uloga) VALUES (?, ?, ?, ?, ?, ?, 'PUTNIK')";
				PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, Korisnik.getKorisnickoIme());
				preparedStatement.setString(index++, Korisnik.getLozinka());
				preparedStatement.setString(index++, Korisnik.getEmail());
				preparedStatement.setString(index++, Korisnik.getIme());
				preparedStatement.setString(index++, Korisnik.getPrezime());
				preparedStatement.setDate(index++, Korisnik.getDatumRodjenja());

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
	public int update(Korisnik Korisnik) { // TODO edit korisnika ce se verovatno raditi iz ugla korisnika a ne admina
		String sql = "UPDATE korisnici SET ";
		return 0;
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int blockunblock(Korisnik korisnik) {
		String sql = "UPDATE korisnici SET blokiran = ? WHERE id = ?";

		boolean uspeh = jdbcTemplate.update(sql, korisnik.getBlokiran().booleanValue(), korisnik.getId()) == 1;
		if (uspeh) {
			return 1;
		} else {
			return 0;
		}
	}

}
