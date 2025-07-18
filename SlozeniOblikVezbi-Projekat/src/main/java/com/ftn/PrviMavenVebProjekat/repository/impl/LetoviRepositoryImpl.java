package com.ftn.PrviMavenVebProjekat.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.ftn.PrviMavenVebProjekat.model.Let;
import com.ftn.PrviMavenVebProjekat.repository.LetoviRepository;
import com.ftn.PrviMavenVebProjekat.service.AerodromiService;
import com.ftn.PrviMavenVebProjekat.service.AvioniService;
//import com.ftn.PrviMavenVebProjekat.repository.impl.LetoviRepositoryImpl.LetRowCallBackhandler;
import com.ftn.PrviMavenVebProjekat.service.LokacijaService;

@Repository
public class LetoviRepositoryImpl implements LetoviRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	@Qualifier("LokacijeDatabaseServis")
	private LokacijaService lokacijaService;
	@Autowired
	private AerodromiService aerodromService;
	@Autowired
	private AvioniService avionService;

	private class LetRowCallBackhandler implements RowCallbackHandler {

		private Map<Long, Let> letovi = new HashMap<>();

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			String oznaka = rs.getString(index++);
			Long polazisteId = rs.getLong(index++);
			Long odredisteId = rs.getLong(index++);
			Long avionId = rs.getLong(index++);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime terminPolaska = LocalDateTime.parse(rs.getString(index++), formatter);
			int trajanjeLeta = rs.getInt(index++);
			int cena = rs.getInt(index++);
			Boolean naAkciji = rs.getBoolean(index++);
			int brojMesta = rs.getInt(index++);
			String razlogOtkaza = rs.getString(index++);
			LocalDate datumVazenjaAkcije = LocalDate.parse(rs.getString(index++));
			int staraCena = rs.getInt(index++);

			Let Let = letovi.get(id);
			if (Let == null) {
				Let = new Let(id, oznaka, aerodromService.findOne(polazisteId), aerodromService.findOne(odredisteId),
						avionService.findOne(avionId), terminPolaska, trajanjeLeta, cena, naAkciji, brojMesta,
						razlogOtkaza, datumVazenjaAkcije, staraCena);
				if (Let.getDatumVazenjaAkcije().isBefore(LocalDate.now())) {
					Let.setCena(staraCena);
					Let.setNaAkciji(false);
				}
				letovi.put(Let.getId(), Let);
			}
//			System.out.println("Processed row with ID: " + id);
//			System.out.println("Current map size: " + letovi.size());
		}

		public List<Let> getLetovi() {
			return new ArrayList<>(letovi.values());
		}
	}

	@Override
	public Let findOne(Long id) {
		String sql = "SELECT * " + "FROM letovi lt " + "WHERE lt.id = ? " + "ORDER BY lt.id; ";

		LetRowCallBackhandler rowCallbackHandler = new LetRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getLetovi().get(0);
	}

	@Override
	public List<Let> findAll() {
		String sql = "SELECT * " + "FROM letovi lt " + "ORDER BY lt.id;";

		LetRowCallBackhandler rowCallbackHandler = new LetRowCallBackhandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getLetovi();
	}

	@Override
	public int save(Let Let) {
		PreparedStatementCreator creator = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO letovi (oznaka, polazisteId, odredisteId, avionId, terminPolaska, trajanjeLeta, cena, naAkciji, brojMesta, razlogOtkaza, "
						+ "datumVazenjaAkcije, staraCena)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, '', ?, ?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, Let.getOznaka());
				preparedStatement.setLong(index++, Let.getPolaziste().getId());
				preparedStatement.setLong(index++, Let.getOdrediste().getId());
				preparedStatement.setLong(index++, Let.getAvion().getId());
				preparedStatement.setTimestamp(index++, java.sql.Timestamp.valueOf(Let.getTerminPolaska()));
				preparedStatement.setInt(index++, Let.getTrajanjeLeta());
				preparedStatement.setInt(index++, Let.getCena());
				preparedStatement.setBoolean(index++, Let.getNaAkciji());
				preparedStatement.setInt(index++, Let.getBrojMesta());
				preparedStatement.setDate(index++, java.sql.Date.valueOf(Let.getDatumVazenjaAkcije()));
				preparedStatement.setInt(index++, Let.getStaraCena());
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
	public int update(Let Let) {
		String sql = "UPDATE letovi SET oznaka = ?, polazisteId = ?, odredisteId = ?, avionId = ?, terminPolaska = ?, trajanjeLeta = ?, cena = ?, "
				+ "naAkciji = ?, brojMesta = ?, razlogOtkaza = ?, datumVazenjaAkcije = ?, staraCena = ?"
				+ " WHERE id = ?";
		boolean uspeh = jdbcTemplate.update(sql, Let.getOznaka(), Let.getPolaziste().getId(),
				Let.getOdrediste().getId(), Let.getAvion().getId(), java.sql.Timestamp.valueOf(Let.getTerminPolaska()),
				Let.getTrajanjeLeta(), Let.getCena(), Let.getNaAkciji(), Let.getBrojMesta(), Let.getRazlogOtkaza(),
				Let.getDatumVazenjaAkcije(), Let.getStaraCena(), Let.getId()) == 1;
		if (uspeh) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM letovi WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
}
