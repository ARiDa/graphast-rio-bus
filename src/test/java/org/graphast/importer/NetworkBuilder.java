package org.graphast.importer;

import java.sql.SQLException;
import java.util.List;

import org.graphast.importer.jdbc.BusRioJdbcImpl;
import org.graphast.model.Edge;
import org.graphast.model.Graph;
import org.graphast.model.GraphImpl;
import org.graphast.model.Node;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.DataAccessException;

public class NetworkBuilder {

	private static Graph rioBusGraph;
	private static List<List<Object>> result;
	
	@BeforeClass
	public static void setup() throws SQLException, DataAccessException{
		
		rioBusGraph = new GraphImpl("/home/gustavolgcr/rioBusGraph");
		result = (List<List<Object>>) new BusRioJdbcImpl().execute();
		
		for(List<Object> row : result) {
			for(int i=0; i<row.size() ; i++) {
				System.out.print(row.get(i) + " ");
			}
			System.out.println("");
		}
		
	}
	
	@Test
	public void generateRioBusNetwork() {
		

	}

}
