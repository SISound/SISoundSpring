package com.sisound.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CountryDao {

	private final static Map<String, Long> COUNTRIES = new HashMap<>();
	
	public long getCountryId(String genre) throws SQLException {
		return COUNTRIES.get(genre);
	}

	private void getAllCountries() throws SQLException {
		if (!COUNTRIES.isEmpty()) {
			return;
		}
		
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT (country_id, country_name) FROM countries");
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			COUNTRIES.put(rs.getString("country_name"), rs.getLong("country_id"));
		}	
	}
}
