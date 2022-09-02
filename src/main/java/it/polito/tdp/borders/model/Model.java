package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private BordersDAO dao;
	private Map<Integer,Country> idMap;
	private Graph<Country, DefaultEdge> grafo;
	
	
	
	public Model() {
		
		dao = new BordersDAO();
		
		idMap = new HashMap<>();
		dao.loadAllCountries(idMap);
		
	}
	
	
	
	//METODO DI CREAZIONE E POPOLAMENTO DEL GRAFO:
	public void creaGrafo(int anno) {
		
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
		
		List<Border> borders = dao.getCountryPairs(anno, idMap);
		
		for(Border b : borders) {
			
			grafo.addVertex(b.getC1());
			grafo.addVertex(b.getC2());
			
			grafo.addEdge(b.getC1(), b.getC2());
		}
		
	
		//System.out.println("Numero vertici grafo: "+grafo.vertexSet().size());
		//System.out.println("Numero archi grafo: "+grafo.edgeSet().size());
	}
	
	
	//METODO CHE RESTITUISCE LA MAPPA CORRISPONDENTE A
	//STATI (VERTICE GRAFO) - NUMERO DI STATI CONFINANTI (GRADO VERTICE):
	public Map<Country,Integer> getVerticiConGrado(){
		
		Map<Country,Integer> verticiConGrado = new HashMap<>();
		
		for(Country c : grafo.vertexSet())
			 verticiConGrado.put(c, grafo.degreeOf(c));
		
		return verticiConGrado; 
	}
	
	
	//METODO CHE RESTITUISCE IL NUMERO DI COMPONENTI CONNESSE DEL GRAFO:
	public int getNumComponentiConnesse() {
		
		ConnectivityInspector<Country,DefaultEdge> ci = 
				new ConnectivityInspector<Country,DefaultEdge>(grafo);
		
		return ci.connectedSets().size();
	}

	
	//----------------------------------------------------------------------
	
	
	//METODO CHE RESTITUISCE IL SET DEI VERTICI DEL GRAFO:
	public Set<Country> getVertici(){
		return grafo.vertexSet();
	}

	
	//METODO CHE RESTITUISCE LA COMPONENTE CONNESSA DEL GRAFO
	//(CORRISPONDENTE AL VERTICE DATO):
	public Set<Country> getComponenteConnessa(Country vertice) {
		
		//Per ottenere la componente connessa del grafo	
		//corrispondente al vertice dato, è necessario:
		
		//visitare il grafo partendo dal vertice in questione
		//e salvare i vertici man mano visitati in un set.
		
		
		//Set di vertici visitati: 
		Set<Country> visitati = new HashSet<>();
		
		
		//Visita del grafo:
		
		//-iteratore:
		DepthFirstIterator<Country,DefaultEdge> it = 
				new DepthFirstIterator<Country,DefaultEdge>(this.grafo, vertice);
		
		//-finchè l'iteratore trova vertici successivi,
		//questi sono da considerare:
		while (it.hasNext()) {
			visitati.add(it.next());
		}
		
		return visitati; 
	}
	
	
}
