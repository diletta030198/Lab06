package it.polito.tdp.meteo;

import it.polito.tdp.meteo.bean.Citta;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(12));
		
		//System.out.println(m.citta.get(1).stampaRilevamenti());
		
		Citta c= new Citta("Milano"); 
		System.out.println("contatore= "+m.contatoreTot); 
		System.out.println(m.trovaSequenza(5));
		System.out.println("contatore= "+m.contatoreTot); 
		System.out.println("contatoreTot= "+c.getContatoreTotale()); 
		
       //System.out.println(m.trovaSequenza(4));
	}

}
