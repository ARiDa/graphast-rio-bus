package org.graphast.importer.jdbc;

import org.graphast.importer.extractor.BusRioExtractor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

public class BusRioJdbcImpl {

	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Transactional(readOnly=true)
	public void execute(){
		
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(DataSourceFactory.getDataSource());
		
		SqlParameterSource namedParameters = null;
		String sql = "SELECT * \n" + 
				"FROM data_bus_rio LIMIT 10";
		
		((JdbcTemplate) this.namedParameterJdbcTemplate.getJdbcOperations()).setFetchSize(1000);
		
		this.namedParameterJdbcTemplate.query(
				sql, namedParameters, new BusRioExtractor());
		
	}
	
	public static void main(String[] args) {
		new BusRioJdbcImpl().execute();
	}
	
}
