package it.polito.tdp.meteo.bean;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import java.util.List;

public class Citta {

	private String nome;
	private List<Rilevamento> rilevamenti;
	private int counter ;
	private int contatoreTotale; 
	
	
	
	public Citta(String nome) {
		this.nome = nome;
		counter=0; 
		contatoreTotale=0; 
	}
	
	public Citta(String nome, List<Rilevamento> rilevamenti) {
		this.nome = nome;
		this.rilevamenti = rilevamenti;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Rilevamento> getRilevamenti() {
		return rilevamenti;
	}

	public void setRilevamenti(List<Rilevamento> rilevamenti) {
		this.rilevamenti = rilevamenti;
	}

	public int getCounter() {
		return counter;
	}
	
	

	public int getContatoreTotale() {
		return contatoreTotale;
	}

	public void setContatoreTotale(int contatoreTotale) {
		this.contatoreTotale = contatoreTotale;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public void increaseCounter() {
		this.counter += 1;
	}
	public void decreaseCounter() {
		this.counter -=1; 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Citta other = (Citta) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	public String stampaRilevamenti() {
		String res="";
		for(Rilevamento r: this.getRilevamenti()) {
			res+=r.toString()+" *** ";
			
		}
		return res;
	}
	
	/**
	 * Avendo un'ArrayList nell'algoritmo di ricorsione posso trovare il singolo valore di umidità
	 * utilizzando l'indice che corrisponde al giorno del mese
	 * @param mese
	 * @return un'arrayList con le umidità per ciascun giorno del mese passato come parametro
	 */
	public double getUmiditaPerMeseGiorno(int mese, int giorno){
		double umidita=0.0;  
		for (Rilevamento r: rilevamenti) {

			if(r.getData().getMonth().getValue()==mese && r.getData().getDayOfMonth()==giorno) {  
				umidita=r.getUmidita(); 
				break;
			}
		}
		return umidita; 
	}
	
	
}
