package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	public  List<Citta> citta ; 
	
	private List<SimpleCity>best;
	
	//faccio una mappa in cui ho come chiave la citta e come valore il contatore di quanti
	//giorni totali è stato in quella citta (deve essere minore di 6)
	Map<Citta,Integer>contatoreTot= new HashMap<Citta,Integer>();  
	

	public Model() {
    MeteoDAO dao= new MeteoDAO();
    this.citta=dao.citta();
  for (Citta c: citta) {
	  c.setRilevamenti(dao.rilevamentoPerCitta(c.getNome()));
	  contatoreTot.put(c, 0); 
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
	
	//ricorsione
	//L è il giorno che sto considerando e per ogni giorno scelgo dove andare nel giorno successivo
	//al giorno 0 scelgo dove andare al giorno 1, 
	//al giorno 14 scelgo dove andare al giorno 15 
	//quando L=15 non devo scegliere più niente e calcolo il percorso ottimo
	
	
	public String trovaSequenza(int mese) {
		
		best=new ArrayList<SimpleCity>(); 
		costo_best=Double.MAX_VALUE; 
		
		
		//Set<SimpleCity> parziale= new HashSet<SimpleCity>();
		ArrayList<SimpleCity> parziale = new ArrayList<SimpleCity>(); 
		
		cercaGiorno(parziale,0,mese);

		return best.toString();   
	}
	
	
	

	
	//dal primo in poi 
	private void cercaGiorno(ArrayList<SimpleCity> parziale, int L, int mese) {
		
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
				//3   una stessa citta è ripetuta più di 6 volte 
				
				
		
				//al giorno L 
		SimpleCity temp= null; 
		SimpleCity temp1= null; 
		
		for(Citta c: citta) {
			
			if(c.getContatoreTotale()<6) {
				//posso rimanere nella citta
			
			if(c.getCounter()<3)
			{
		
		 temp = new SimpleCity(c.getNome(),c.getUmiditaPerMeseGiorno(mese, L+1));
			c.setContatoreTotale(c.getContatoreTotale()+1);
			c.increaseCounter();
			parziale.add(temp);
			this.cercaGiorno(parziale, L+1, mese);
			
			}
			
			else if(c.getCounter()>=3 ){
				
					//posso restare 
					temp= new SimpleCity(c.getNome(),c.getUmiditaPerMeseGiorno(mese, L+1));
					
					c.setContatoreTotale(c.getContatoreTotale()+1);
				
					c.increaseCounter();
					
					parziale.add(temp);
					this.cercaGiorno(parziale, L+1, mese);
					
					
					
					//decido di non restare
					Citta temporanea= new Citta(c.getNome(),c.getRilevamenti());
					
				 for (Citta c1: citta) {
					 if(!c1.equals(temporanea) && c1.getContatoreTotale()<6) {
						 temp1= new SimpleCity(c1.getNome(),c1.getUmiditaPerMeseGiorno(mese,L+1)+100);
						c1.setContatoreTotale(c1.getContatoreTotale()+1);
						
						 c1.increaseCounter();
						 c.setCounter(0);
						 parziale.add(temp1);
						 this.cercaGiorno(parziale, L+1, mese);
						 
					 }
				 }
				 
					
				}
			}
			
			else   {  //(non posso rimanere nella citta perche sono stata troppi giorni
				for (Citta c1: citta) {
					 if(!c1.equals(c)) {
						 temp1= new SimpleCity(c1.getNome(),c1.getUmiditaPerMeseGiorno(mese,L+1)+100);
						 
						 c1.setContatoreTotale(c1.getContatoreTotale()+1);
						 c1.increaseCounter();
						 c.setCounter(0);
						 parziale.add(temp1);
						 this.cercaGiorno(parziale, L+1, mese);
						 
						 
					 }
				 }
				 
			}
			
			
			//backtracking 
			parziale.remove(temp);
			   //c.decreaseCounter();
			
		
			
		}
		
	}
		
		
		
	
		
		
		
		
		
		
		
		
		
	
	
	
	
	

	private double costoTot(ArrayList<SimpleCity> parziale) {
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
