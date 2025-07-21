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

import com.ftn.PrviMavenVebProjekat.model.Kontinenti;
import com.ftn.PrviMavenVebProjekat.model.Lokacija;
import com.ftn.PrviMavenVebProjekat.repository.LokacijeRepository;

@Repository
public class LokacijeRepositoryImpl implements LokacijeRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class LokacijaRowCallBackhandler implements RowCallbackHandler {

		private Map<Long, Lokacija> lokacije = new HashMap<>();

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			String Grad = rs.getString(index++);
			String Drzava = rs.getString(index++);
			Kontinenti kontinent = Kontinenti.valueOf(rs.getString(index++));
			String putanjaDoSlike = rs.getString(index++);

			Lokacija lokacija = lokacije.get(id);
			if (lokacija == null) {
				lokacija = new Lokacija(id, Grad, Drzava, kontinent, putanjaDoSlike);
				lokacije.put(lokacija.getId(), lokacija);
			}
		}

		public List<Lokacija> getLokacije() {
			return new ArrayList<>(lokacije.values());
		}
	}

	@Override
	public Lokacija findOne(Long id) {
		String sql = "SELECT * " + "FROM lokacije l " + "WHERE l.id = ? " + "ORDER BY l.id; ";

		LokacijaRowCallBackhandler rowCallbackHandler = new LokacijaRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getLokacije().get(0);
	}

	@Override
	public List<Lokacija> findAll() {
		String sql = "SELECT * " + "FROM lokacije l " + "ORDER BY l.id; ";

		LokacijaRowCallBackhandler rowCallbackHandler = new LokacijaRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getLokacije();
	}

	@Override
	public int save(Lokacija lokacija) {
		PreparedStatementCreator creator = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO lokacije (Grad, Drzava, Kontinent, putanjaDoSlike) VALUES (?, ?, ?, ?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, lokacija.getGrad());
				preparedStatement.setString(index++, lokacija.getDrzava());
				preparedStatement.setString(index++, lokacija.getKontinent().toString());
				preparedStatement.setString(index++, lokacija.getPutanjaDoSlike());
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
	public int update(Lokacija lokacija) {
		String sql = "UPDATE lokacije SET Grad = ?, Drzava = ?, Kontinent = ? WHERE id = ?";
		boolean uspeh = jdbcTemplate.update(sql, lokacija.getGrad(), lokacija.getDrzava(),
				lokacija.getKontinent().toString(), lokacija.getId()) == 1;
		if (uspeh) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM lokacije WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
