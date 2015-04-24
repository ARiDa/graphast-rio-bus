package org.graphast.importer.jdbc;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceFactory {

	private static DriverManagerDataSource dataSource;
	
	private static String url = "jdbc:postgresql://arida1.mooo.com:8080/data_traffic";
	private static String username = "postgres";
	private static String password = "aridapostgres12";
	
	public static DriverManagerDataSource getDataSource(){
		
		if(dataSource == null) {
			dataSource = new DriverManagerDataSource(url, username, password);
			
		}
		
		return dataSource;
		
	}
	
}
