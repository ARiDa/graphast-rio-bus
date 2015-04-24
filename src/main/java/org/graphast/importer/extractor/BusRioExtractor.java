package org.graphast.importer.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class BusRioExtractor implements ResultSetExtractor<Object> {

	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		
		while(rs.next()) {
			System.out.println(rs.getString("linha"));
		}
		
		return null;
	}

	
	
}
