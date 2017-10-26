package com.sisound.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GenresDao {


	private final static Map<String, Long> GENRES = new HashMap<>();
	
	public long getGenreId(String genre) throws SQLException {
		return GENRES.get(genre);
	}

	public Map getAllGenres() throws SQLException {
		if (!GENRES.isEmpty()) {
			return  Collections.unmodifiableMap(this.GENRES);
		}
		
		System.out.println("123");
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT genre_id, genre_title FROM music_genres");
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			GENRES.put(rs.getString("genre_title"), rs.getLong("genre_id"));
			System.out.println(rs.getString("genre_title"));
			System.out.println(GENRES.get(rs.getString("genre_title")));
		}
		
		return  Collections.unmodifiableMap(this.GENRES);
	}
}