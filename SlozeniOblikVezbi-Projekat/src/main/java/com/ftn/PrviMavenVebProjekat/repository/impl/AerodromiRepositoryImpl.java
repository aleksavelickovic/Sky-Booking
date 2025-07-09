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

import com.ftn.PrviMavenVebProjekat.model.Aerodrom;
import com.ftn.PrviMavenVebProjekat.repository.AerodromiRepository;
//import com.ftn.PrviMavenVebProjekat.repository.impl.AerodromiRepositoryImpl.AerodromRowCallBackhandler;
import com.ftn.PrviMavenVebProjekat.service.LokacijaService;

public class AerodromiRepositoryImpl implements AerodromiRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private LokacijaService lokacijaService;

	private class AerodromRowCallBackhandler implements RowCallbackHandler {

		private Map<Long, Aerodrom> aerodromi = new HashMap<>();

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			String oznaka = rs.getString(index++);
			Long lokacijaId = rs.getLong(index++);

			Aerodrom Aerodrom = aerodromi.get(id);
			if (Aerodrom == null) {
				Aerodrom = new Aerodrom(id, oznaka, lokacijaService.findOne(lokacijaId));
				aerodromi.put(Aerodrom.getId(), Aerodrom);
			}
		}

		public List<Aerodrom> getAerodromi() {
			return new ArrayList<>(aerodromi.values());
		}
	}

	@Override
	public Aerodrom findOne(Long id) {
		String sql = "SELECT * " + "FROM aerodromi a " + "WHERE a.id = ? " + "ORDER BY a.id; ";

		AerodromRowCallBackhandler rowCallbackHandler = new AerodromRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getAerodromi().get(0);
	}

	@Override
	public List<Aerodrom> findAll() {
		String sql = "SELECT * " + "FROM aerodromi a " + "ORDER BY a.id; ";

		AerodromRowCallBackhandler rowCallbackHandler = new AerodromRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getAerodromi();
	}

	@Override
	public int save(Aerodrom Aerodrom) {
		PreparedStatementCreator creator = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO aerodromi (oznaka, lokacijaId) VALUES (?, ?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, Aerodrom.getOznaka());
				preparedStatement.setLong(index++, Aerodrom.getLokacija().getId());
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
	public int update(Aerodrom Aerodrom) {
		String sql = "UPDATE aerodromi SET oznaka = ?, lokacijaId = ? WHERE id = ?";
		boolean uspeh = jdbcTemplate.update(sql, Aerodrom.getOznaka(), Aerodrom.getLokacija().getId(),
				Aerodrom.getId()) == 1;
		if (uspeh) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM aerodromi WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
