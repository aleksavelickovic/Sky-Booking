package com.ftn.PrviMavenVebProjekat.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.ftn.PrviMavenVebProjekat.repository.KarteRepository;
import com.ftn.PrviMavenVebProjekat.service.LetoviService;

@Repository
public class KarteRepositoryImpl implements KarteRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private LetoviService letoviService;

	private class KartaRowCallBackhandler implements RowCallbackHandler {

		private Map<Long, Karta> karte = new HashMap<>();

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			Long letId = rs.getLong(index++);
			String brojSedista = rs.getString(index++);
			int cena = rs.getInt(index++); // Cenu ne koristimo u konstruktoru jer se ona generise na osnovu cene leta
			String imeIPrezimePutnika = rs.getString(index++);
			String brojPasosa = rs.getString(index++);

			Karta Karta = karte.get(id);
			if (Karta == null) {
				Karta = new Karta(id, letoviService.findOne(letId), brojSedista, imeIPrezimePutnika, brojPasosa);
				karte.put(Karta.getId(), Karta);
			}
		}

		public List<Karta> getKarte() {
			return new ArrayList<>(karte.values());
		}
	}

	@Override
	public Karta findOne(Long id) {
		String sql = "SELECT * " + "FROM karte k " + "WHERE k.id = ? " + "ORDER BY k.id; ";

		KartaRowCallBackhandler rowCallbackHandler = new KartaRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getKarte().get(0);
	}

	@Override
	public List<Karta> findAll() {
		String sql = "SELECT * " + "FROM karte k " + "ORDER BY k.id; ";

		KartaRowCallBackhandler rowCallbackHandler = new KartaRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getKarte();
	}

	@Override
	public int save(Karta Karta) {
		PreparedStatementCreator creator = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO karte (letId, brojSedista, cena, imeIPrezimePutnika, brojPasosa) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setLong(index++, Karta.getLet().getId());
				preparedStatement.setString(index++, Karta.getBrojSedista());
				preparedStatement.setInt(index++, Karta.getCena());
				preparedStatement.setString(index++, Karta.getImeIPrezimePutnika());
				preparedStatement.setString(index++, Karta.getBrojPasosa());
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
	public int update(Karta karta) {
		String sql = "UPDATE karte SET letId = ?, brojSedista = ?, cena = ?, imeIPrezimePutnika = ?, brojPasosa = ? WHERE id = ?";
		boolean uspeh = jdbcTemplate.update(sql, karta.getLet().getId(), karta.getBrojSedista(), karta.getCena(),
				karta.getImeIPrezimePutnika(), karta.getBrojPasosa(), karta.getId()) == 1;
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
