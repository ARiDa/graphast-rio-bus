package org.graphast.importer.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.graphast.model.EdgeImpl;
import org.graphast.model.Graph;
import org.graphast.model.GraphImpl;
import org.graphast.model.NodeImpl;
import org.graphast.util.DistanceUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class BusRioExtractor implements ResultSetExtractor<Object> {

	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {

		Graph rioNetwork = new GraphImpl("/home/gustavolgcr/rioBusGraph");

		Map<List<Double>, Integer> currentSequence = new HashMap<List<Double>, Integer>();
		Map<List<Double>, Integer> nextSequence = new HashMap<List<Double>, Integer>();

		if(rs.next()){

			List<Double> coordinates = new ArrayList<Double>();

			coordinates.add(rs.getDouble("latitude"));
			coordinates.add(rs.getDouble("longitude"));

			currentSequence.put(coordinates, rs.getInt("sequencia"));

		}

		if(rs.next()){

			if(!currentSequence.containsValue(rs.getInt("sequencia"))){

				List<Double> coordinates = new ArrayList<Double>();

				coordinates.add(rs.getDouble("latitude"));
				coordinates.add(rs.getDouble("longitude"));

				nextSequence.put(coordinates, rs.getInt("sequencia"));

			} else {

				rs.previous();

			}

		}

		while(rs.next()) {

			if(currentSequence.containsValue(rs.getInt("sequencia"))){

				List<Double> coordinates = new ArrayList<Double>();

				coordinates.add(rs.getDouble("latitude"));
				coordinates.add(rs.getDouble("longitude"));

				currentSequence.put(coordinates, rs.getInt("sequencia"));

			}

			if(nextSequence.containsValue(rs.getInt("sequencia")) || nextSequence.isEmpty()){

				List<Double> coordinates = new ArrayList<Double>();

				coordinates.add(rs.getDouble("latitude"));
				coordinates.add(rs.getDouble("longitude"));

				nextSequence.put(coordinates, rs.getInt("sequencia"));

			}

			if(!currentSequence.containsValue(rs.getInt("sequencia")) && 
					!nextSequence.containsValue(rs.getInt("sequencia"))){

				double distance = Double.MAX_VALUE;
				NodeImpl nodeFrom;
				NodeImpl nodeTo=null;
				NodeImpl verificationNode;

				for(Map.Entry<List<Double>, Integer> currentSequenceEntry : currentSequence.entrySet()){

					distance = Double.MAX_VALUE;

					nodeFrom = new NodeImpl(currentSequenceEntry.getKey().get(0), currentSequenceEntry.getKey().get(1));

					if(rioNetwork.getNodeId(nodeFrom.getLatitude(), nodeFrom.getLongitude())==null) {
						rioNetwork.addNode(nodeFrom);
						System.out.println("Node FROM added:");
						System.out.println("\t" + nodeFrom.toString());
					} else {

						nodeFrom = (NodeImpl)rioNetwork.getNode(rioNetwork.getNodeId(nodeFrom.getLatitude(), nodeFrom.getLongitude()));

					}

					for(Map.Entry<List<Double>, Integer> nextSequenceEntry : nextSequence.entrySet()) {

						verificationNode = new NodeImpl(nextSequenceEntry.getKey().get(0), nextSequenceEntry.getKey().get(1));

						if(DistanceUtils.distanceLatLong(nodeFrom, verificationNode) < distance) {
							nodeTo = verificationNode;
							distance = DistanceUtils.distanceLatLong(nodeFrom, verificationNode);
						}

					}

					if(rioNetwork.getNodeId(nodeTo.getLatitude(), nodeTo.getLongitude())==null) {
						rioNetwork.addNode(nodeTo);
						System.out.println("Node TO added:");
						System.out.println("\t" + nodeTo.toString());
					} else{
						nodeTo = (NodeImpl)rioNetwork.getNode(rioNetwork.getNodeId(nodeTo.getLatitude(), nodeTo.getLongitude()));

					}



					EdgeImpl edge = new EdgeImpl(nodeFrom.getId(), nodeTo.getId(), (int)distance);
					rioNetwork.addEdge(edge);

				}

				currentSequence.clear();
				currentSequence.putAll(nextSequence);
				nextSequence.clear();

				List<Double> coordinates = new ArrayList<Double>();

				coordinates.add(rs.getDouble("latitude"));
				coordinates.add(rs.getDouble("longitude"));

				nextSequence.put(coordinates, rs.getInt("sequencia"));

			}

		}

		double distance = Double.MAX_VALUE;
		NodeImpl nodeFrom;
		NodeImpl nodeTo=null;
		NodeImpl verificationNode;

		for(Map.Entry<List<Double>, Integer> currentSequenceEntry : currentSequence.entrySet()){

			distance = Double.MAX_VALUE;

			nodeFrom = new NodeImpl(currentSequenceEntry.getKey().get(0), currentSequenceEntry.getKey().get(1));

			if(rioNetwork.getNodeId(nodeFrom.getLatitude(), nodeFrom.getLongitude())==null) {

				rioNetwork.addNode(nodeFrom);
				System.out.println("Node FROM added:");
				System.out.println("\t" + nodeFrom.toString());

			} else {

				nodeFrom = (NodeImpl)rioNetwork.getNode(rioNetwork.getNodeId(nodeFrom.getLatitude(), nodeFrom.getLongitude()));

			}

			for(Map.Entry<List<Double>, Integer> nextSequenceEntry : nextSequence.entrySet()) {

				verificationNode = new NodeImpl(nextSequenceEntry.getKey().get(0), nextSequenceEntry.getKey().get(1));

				if(DistanceUtils.distanceLatLong(nodeFrom, verificationNode) < distance) {
					nodeTo = verificationNode;
					distance = DistanceUtils.distanceLatLong(nodeFrom, verificationNode);
				}

			}

			if(rioNetwork.getNodeId(nodeTo.getLatitude(), nodeTo.getLongitude())==null) {

				rioNetwork.addNode(nodeTo);
				System.out.println("Node TO added:");
				System.out.println("\t" + nodeTo.toString());

			} else{
				nodeTo = (NodeImpl)rioNetwork.getNode(rioNetwork.getNodeId(nodeTo.getLatitude(), nodeTo.getLongitude()));

			}

			EdgeImpl edge = new EdgeImpl(nodeFrom.getId(), nodeTo.getId(), (int)distance);
			rioNetwork.addEdge(edge);

		}

		currentSequence.clear();
		currentSequence.putAll(nextSequence);
		nextSequence.clear();

		for(Map.Entry<List<Double>, Integer> currentSequenceEntry : currentSequence.entrySet()){
			nodeFrom = new NodeImpl(currentSequenceEntry.getKey().get(0), currentSequenceEntry.getKey().get(1));

			if(rioNetwork.getNodeId(nodeFrom.getLatitude(), nodeFrom.getLongitude())==null) {

				rioNetwork.addNode(nodeFrom);
				System.out.println("Node FROM added:");
				System.out.println("\t" + nodeFrom.toString());

			} else {

				nodeFrom = (NodeImpl)rioNetwork.getNode(rioNetwork.getNodeId(nodeFrom.getLatitude(), nodeFrom.getLongitude()));

			}

		}

		return rioNetwork;

	}

}
