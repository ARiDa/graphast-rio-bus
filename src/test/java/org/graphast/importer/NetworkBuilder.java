package org.graphast.importer;

import java.sql.SQLException;

import org.graphast.importer.jdbc.BusRioJdbcImpl;
import org.graphast.model.Graph;
import org.graphast.model.GraphImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.DataAccessException;

public class NetworkBuilder {

	private static Graph rioBusGraph;
	
	@BeforeClass
	public static void setup() throws SQLException, DataAccessException{
		
		rioBusGraph = new GraphImpl("/home/gustavolgcr/rioBusGraph");
		rioBusGraph = (GraphImpl) new BusRioJdbcImpl().execute();
		//result = (List<List<Object>>) new BusRioJdbcImpl().execute();
		
		System.out.println("Numero de nos: " + rioBusGraph.getNumberOfNodes());
		System.out.println("Numero de arestas: " + rioBusGraph.getNumberOfEdges());
		
		
		
	}
	
	@Test
	public void generateRioBusNetwork() {
		

	}

}
