package org.graphast.importer.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.graphast.util.DistanceUtils;
import org.graphast.model.EdgeImpl;
import org.graphast.model.Graph;
import org.graphast.model.GraphImpl;
import org.graphast.model.NodeImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class BusRioExtractor implements ResultSetExtractor<Object> {

	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		
		
		//TODO Verificar o tipo do currentNode e currentSequence;
		
		Graph rioNetwork = new GraphImpl("/home/gustavolgcr/rioBusGraph");
		
		NodeImpl currentNode;
		NodeImpl nextNode;
		
		List<NodeImpl> currentNodeList = new ArrayList<NodeImpl>();
		List<NodeImpl> nextNodeList = new ArrayList<NodeImpl>();
		
		List<Integer> currentSequencia = new ArrayList<Integer>();
		List<Integer> nextSequencia = new ArrayList<Integer>();

		if(rs.next()){
			
			currentNode = new NodeImpl(rs.getInt("latitude"), rs.getInt("longitude"));
			currentNodeList.add(currentNode);
			
			currentSequencia.add(rs.getInt("sequencia"));
			
		}
		
		
		while(rs.next()) {

			if(rs.getInt("sequencia")==currentSequencia.get(0)){
				
				currentSequencia.add(rs.getInt("sequencia"));
				
				currentNode = new NodeImpl(rs.getInt("latitude"), rs.getInt("longitude"));
				currentNodeList.add(currentNode);
				
			} else {
				
				
				nextSequencia.add(rs.getInt("sequencia"));
				
				nextNode = new NodeImpl(rs.getInt("latitude"), rs.getInt("longitude"));
				nextNodeList.add(nextNode);
				
			}
			
			if(rs.getInt("sequencia")!=currentSequencia.get(0) && rs.getInt("sequencia")!=nextSequencia.get(0)){
				
				double distance = Double.MAX_VALUE;
				NodeImpl nodeFrom;
				NodeImpl nodeTo=null;
				
				for(NodeImpl current : currentNodeList){
					
					nodeFrom = current;
					rioNetwork.addNode(nodeFrom);
					
					for(NodeImpl next : nextNodeList){
						
						if(DistanceUtils.distanceLatLong(current, next) < distance){
							distance = DistanceUtils.distanceLatLong(current, next);
							nodeTo = next;
						}
						
					}
					
					EdgeImpl edge = new EdgeImpl(nodeFrom.getId(), nodeTo.getId(), (int)distance);
					rioNetwork.addEdge(edge);
					
				}
				
				rs.previous();
			
				currentSequencia = nextSequencia;
				nextSequencia = new ArrayList<Integer>();
				
			}
			
		}
		
		for(int current : currentSequencia){
			for(int next : nextSequencia){
				
				//verificar o mais proximo
				//criar no e aresta
				
			}
		}
		
		return rioNetwork;

	}

}
