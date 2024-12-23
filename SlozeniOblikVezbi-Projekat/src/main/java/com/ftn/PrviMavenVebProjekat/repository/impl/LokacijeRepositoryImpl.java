package com.ftn.PrviMavenVebProjekat.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
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
			// TODO Auto-generated method stub
			int index = 1;
			Long id = rs.getLong(index++);
			String Grad = rs.getString(index++);
			String Drzava = rs.getString(index++);
			Kontinenti kontinent = Kontinenti.valueOf(rs.getString(index++));

			Lokacija lokacija = lokacije.get(id);
			if (lokacija == null) {
				lokacija = new Lokacija(id, Grad, Drzava, kontinent);
				lokacije.put(lokacija.getId(), lokacija);
			}
		}

		public List<Lokacija> getLokacije() {
			return new ArrayList<>(lokacije.values());
		}
	}

	@Override
	public Lokacija findOne(Long id) {
		String sql = "SELECT l.id, l.Grad, l.Drzava, l.Kontinent "
					+ "FROM lokacije l "
					+ "WHERE l.id = ? "
					+ "ORDER BY l.id; ";
		
		LokacijaRowCallBackhandler rowCallbackHandler = new LokacijaRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getLokacije().get(0);
	}

	@Override
	public List<Lokacija> findAll() {
		String sql = "SELECT l.id, l.Grad, l.Drzava, l.Kontinent "
					+ "FROM lokacije l "
					+ "ORDER BY l.id; ";
		
		LokacijaRowCallBackhandler rowCallbackHandler = new LokacijaRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getLokacije();
	}

	@Override
	public int save(Lokacija lokacija) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Lokacija lokacija) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
