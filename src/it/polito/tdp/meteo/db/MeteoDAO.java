package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.time.Month;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {
	
	


	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";
		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data").toLocalDate(), rs.getInt("Umidita"));
			
				
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		List<Rilevamento> rilevamentoCittaMese= new LinkedList<Rilevamento>();
		final String sql= "SELECT  localita, data, umidita FROM situazione WHERE localita=? AND MONTH(DATA)=?";
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, localita);
			st.setInt(2, mese);

			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data").toLocalDate(), rs.getInt("Umidita"));
				rilevamentoCittaMese.add(r);
			}

			conn.close();
			
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	

		return rilevamentoCittaMese;
	}

	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {
		final String sql=	"SELECT   AVG(umidita) FROM situazione WHERE localita=? AND MONTH(DATA)=?";
		Double media=null; 
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, localita);
			st.setInt(2, mese);

			ResultSet rs = st.executeQuery();
			if(rs.next()) {
		 media = rs.getDouble("AVG(umidita)");
			}
			
			
			conn.close();
			
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

		return media;
	}
	
	
	public List<Citta> citta(){
		List<Citta> citta = new LinkedList<Citta>();
		final String sql = "SELECT localita FROM situazione GROUP BY localita"; 
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			

			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {

			Citta c = new Citta(rs.getString("localita"));
			citta.add(c);
				
			}

			
			
			
			conn.close();
			
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return citta; 
		}
	
	public List <Rilevamento> rilevamentoPerCitta(String citta){
		List<Rilevamento> rilevamenti = new LinkedList<Rilevamento>();
		
		final String sql=	"SELECT Localita, Data, Umidita FROM situazione WHERE localita=? ";
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, citta);
			

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data").toLocalDate(), rs.getInt("Umidita"));
				rilevamenti.add(r);
				
			
					
				}

			
			
			conn.close();
			
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
return rilevamenti;
		
	}
	
	
	
	
}
