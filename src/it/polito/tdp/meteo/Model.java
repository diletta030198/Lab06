package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	public List<Citta> citta ; 
	
	public List<SimpleCity>best;
	

	public Model() {
    MeteoDAO dao= new MeteoDAO();
    this.citta=dao.citta();
  for (Citta c: citta) {
	  c.setRilevamenti(dao.rilevamentoPerCitta(c.getNome()));
  }
	}

	public String getUmiditaMedia(int mese) {
		MeteoDAO dao = new MeteoDAO();
		String res= ""; 
		for (Citta c: dao.citta()) {
			double media =dao.getAvgRilevamentiLocalitaMese(mese, c.getNome());
			res+=c.getNome()+"="+media+"\n";
		}
		return res; 

	
	}
	
	
	
	public String getUmiditaPercitta(int mese) {
		MeteoDAO dao = new MeteoDAO();
		String res= ""; 
		for (Citta c: dao.citta()) {
			
			res+=c.getNome()+"="+c.getRilevamenti()+"\n";
		}
		return res; 

	
	}
	
	

	
	Double costo_best; 
	
	public String trovaSequenza(int mese) {
		
		best=new ArrayList<SimpleCity>(); 
		costo_best=Double.MAX_VALUE; 
		Set<SimpleCity> parziale= new HashSet<SimpleCity>();
		
		cerca(parziale,0,mese);

		return best.toString();   
	}

	private void cerca(Set<SimpleCity> parziale, int L, int mese) {
		
	//Casi terminali
		//1
		double costo= costoTot(parziale);
		if(costo>costo_best) {
			return; 
		}
		//2
		if(L==this.NUMERO_GIORNI_TOTALI) {
			if(costo<costo_best) {
				//la soluzione parziale è il best
				best= new ArrayList<SimpleCity>(parziale);
				costo_best=costo; 
				return;
			}
			else {
				return; 
			}
		}
		
		
		
		
		//al giorno L 
		
		
		
	}

	private double costoTot(Set<SimpleCity> parziale) {
		double costo=0.0; 
		for(SimpleCity s: parziale) {
			costo+=s.getCosto();
		}
		return costo;
	}

	
	private boolean controllaParziale(List<SimpleCity> parziale) {

		return true;
	}

}
