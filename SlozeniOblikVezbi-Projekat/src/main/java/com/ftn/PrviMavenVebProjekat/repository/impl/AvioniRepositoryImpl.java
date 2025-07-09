package com.ftn.PrviMavenVebProjekat.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.PrviMavenVebProjekat.model.Avion;
import com.ftn.PrviMavenVebProjekat.repository.AvioniRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class AvioniRepositoryImpl implements AvioniRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class AvionRowCallBackhandler implements RowCallbackHandler {

		private Map<Long, Avion> avioni = new HashMap<>();

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			String naziv = rs.getString(index++);
			int brojKolona = rs.getInt(index++);
			int brojRedova = rs.getInt(index++);

			Avion Avion = avioni.get(id);
			if (Avion == null) {
				Avion = new Avion(id, naziv, brojKolona, brojRedova);
				avioni.put(Avion.getId(), Avion);
			}
		}

		public List<Avion> getAvioni() {
			return new ArrayList<>(avioni.values());
		}
	}

	@Override
	public Avion findOne(Long id) {
		String sql = "SELECT * " + "FROM avioni av " + "WHERE av.id = ? " + "ORDER BY av.id; ";

		AvionRowCallBackhandler rowCallbackHandler = new AvionRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getAvioni().get(0);
	}

	@Override
	public List<Avion> findAll() {
		String sql = "SELECT * " + "FROM avioni av " + "ORDER BY av.id; ";

		AvionRowCallBackhandler rowCallbackHandler = new AvionRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getAvioni();
	}

	@Override
	public int save(Avion Avion) {
		PreparedStatementCreator creator = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO avioni (naziv, brojKolona, brojRedova) VALUES (?, ?, ?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, Avion.getNaziv());
				preparedStatement.setInt(index++, Avion.getBrojKolona());
				preparedStatement.setInt(index++, Avion.getBrojRedova());
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
	public int update(Avion Avion) {
		String sql = "UPDATE avioni SET naziv = ?, brojKolona = ?, brojRedova = ? WHERE id = ?";
		boolean uspeh = jdbcTemplate.update(sql, Avion.getNaziv(), Avion.getBrojKolona(), Avion.getBrojRedova(),
				Avion.getId()) == 1;
		if (uspeh) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM avioni WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
}
