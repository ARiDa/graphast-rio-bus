package org.graphast.importer.jdbc;

import org.graphast.importer.extractor.BusRioExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class BusRioJdbcImpl {

	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public Object execute(){
		
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(DataSourceFactory.getDataSource());
		
		SqlParameterSource namedParameters = null;
		String sql = "SELECT * FROM paradas_rio WHERE linha=603 ORDER BY sequencia DESC";
		
		return this.namedParameterJdbcTemplate.query(sql, namedParameters, new BusRioExtractor());
		
	}
	
}
