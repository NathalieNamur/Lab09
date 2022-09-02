package it.polito.tdp.borders.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.borders.model.Country;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();
		
		
		Map<Integer,Country> idMap = new HashMap<>();
		dao.loadAllCountries(idMap);
		
		System.out.println("Numero nazioni: "+idMap.size());
		
		
		System.out.println("Numero confini (1920): "+dao.getCountryPairs(1920, idMap).size());
	}
	
	
}
