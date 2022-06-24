package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
		
	private YelpDao dao ;
	private List<String> cities ;
	private Map<String, Business> idMap ;
	private List<Business> businesses ;
	private Graph<Business, DefaultWeightedEdge> grafo ;
	
	public Model() {
		this.dao = new YelpDao() ;
		this.cities = new ArrayList<>(dao.getAllCities()) ;
		this.idMap = new HashMap<String, Business>() ;
		dao.getAllBusiness(idMap) ;
		this.businesses = new ArrayList<>() ;
		
	}
	
	public String creaGrafo(String citta, int anno) {
		this.grafo = 
				new SimpleDirectedWeightedGraph<Business, DefaultWeightedEdge>(DefaultWeightedEdge.class) ;
		this.businesses = new ArrayList<>(dao.getAllBusinessByCityYear(citta, anno, idMap)) ;
		
		Graphs.addAllVertices(this.grafo, this.businesses) ;
		
		List<Adiacenza> archi = new ArrayList<>(dao.getAllAdiacenze(citta, anno, idMap)) ;
		
		for(Adiacenza a : archi) {
			double peso = a.getMedia1() - a.getMedia2() ;
			
			if(peso > 0 ) { 
//					&& !this.grafo.containsEdge(this.grafo.getEdge( a.getBusiness2(), a.getBusiness1()))) {
				Graphs.addEdgeWithVertices(this.grafo, a.getBusiness2(), a.getBusiness1()) ;
			} else if(peso < 0) {
//					&& !this.grafo.containsEdge(this.grafo.getEdge( a.getBusiness1(), a.getBusiness2()))) {
				Graphs.addEdgeWithVertices(this.grafo, a.getBusiness1(), a.getBusiness2()) ;
			}
		}
		
		return "Grafo creato\n" + "#VERTICI: " + this.grafo.vertexSet().size() + "\n"
				+ "#ARCHI: " + this.grafo.edgeSet().size() + "\n";
	}
	
	public List<String> getCities() {
		Collections.sort(cities, new ComparatorePerNome());
		return cities ;
	}
	
	public String getLocaleMigliore() {
		double max = 0.0 ;
		Business migliore = null ;
		for(Business b : this.grafo.vertexSet()) {
			
			if(calcolaPeso(b) > max) {
				max = calcolaPeso(b) ;
				migliore = b ;
			}
		}
		
		return "LOCALE MIGLIORE: " + migliore.getBusinessName() + "\n";
		
	}

	private double calcolaPeso(Business b) {
		double sumUscenti = 0.0;
		double sumEntranti = 0.0;

		for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(b)) {
			sumUscenti += this.grafo.getEdgeWeight(e) ;
		}
		for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(b)) {
			sumEntranti += this.grafo.getEdgeWeight(e) ;

		}
		
		return sumEntranti - sumUscenti;
	}
	
	public boolean grafoCreato() {
		if(this.grafo==null) {
			return false ;
		} else 
			return true ;
	}
}
