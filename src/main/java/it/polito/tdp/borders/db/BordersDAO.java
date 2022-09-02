package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	//METODO PER POPOLARE L'ID MAP CON I DATI DEL DB:
	public void loadAllCountries(Map<Integer,Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme "
				   + "FROM country "
				   + "ORDER BY StateAbb";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				
				if(!idMap.containsKey(rs.getInt("ccode"))) {
					
					Country c = new Country(rs.getString("StateAbb"),
											rs.getInt("ccode"),  
											rs.getString("StateNme"));
				
					idMap.put(c.getCCode(), c);
				}
			}
			
			conn.close();
			st.close();

			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Errore nel metodo loadAllCountries().");
			throw new RuntimeException("Error Connection Database");
		}
	}

	
	
	//METODO CHE RESTITUISCE LA LISTA DEI CONFINI (CORRISPONDENRTI AL VINCOLO):
	public List<Border> getCountryPairs(int anno, Map<Integer,Country> idMap) {

		String sql = "SELECT state1no, state2no "
				   + "FROM contiguity "
				   + "WHERE year <= ? AND conttype = 1";
		
		List<Border> result = new ArrayList<>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
		
				Country c1 = idMap.get(rs.getInt("state1no"));
				Country c2 = idMap.get(rs.getInt("state2no"));
				
				Border b = new Border(c1, c2);
				
				result.add(b);
				
			}
			
			conn.close();
			st.close();

			return result;
		
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Errore nel metodo getCountryPairs().");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
}
