import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * rappresenta una lista di distanze tra punti su una retta
 * (punti di restrizione di una sequenza genomica)
 * 
 * */
public class DistanceList {
	
	//lista di interi che rappresentano le distanze tra i punti  
	private List<Integer> delta;
	
	/**
	 * Costruisce una lista di distanze vuota
	 * */
	public DistanceList() {
		delta = new ArrayList<Integer>();
		
	}
	
	
	/**
	 * Costruisce la lista di tutte le distanze tra coppie di punti a partire da un insieme di punti su una retta 
	 * (punti di restizione) 
	 * 
	 * @param s insieme di punti su una retta
	 * */
	public DistanceList(Set<Integer> s) {
		//costruisco una lista vuota
		delta = new ArrayList<Integer>();
		//costruisco una lista di appoggio che mi contiene tutti i punti dell'insieme s per accedervi in base alla posizione
		List<Integer> res= new LinkedList<Integer>();
		res.addAll(s); 
		//se ordino l'insieme la differenza tra due punti adiacenti sarà sempre positiva
		Collections.sort(res);                      
		for(int i=0; i<res.size(); i++) {
			for(int j=i+1;j<res.size(); j++) {
				//aggiungo a delta la differenza delta.add(res.get(j)-res.get(i))
				int diff=(res.get(j)-res.get(i));   
				delta.add(diff);
				
				}
		}
		//ordino l'insieme delta in cui ho aggiunto le differenze
		Collections.sort(delta);                  
	}
	
	/**
	 * test dei metodi implementati
	 */
	public static void main(String[] args) {
		Set<Integer> s = new HashSet<Integer>();
		s.add(10); s.add(0); s.add(7); s.add(4); s.add(2);
		System.out.println("Set s = "+s);
		DistanceList deltas = new DistanceList(s);
		System.out.println(deltas);
		System.out.println("Massimo = "+ deltas.getMax());
		
	}
	
	@Override
	public String toString() {
		return ""+delta;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DistanceList) {
			DistanceList otherList = (DistanceList) obj;
			return delta.equals(otherList.delta);
		} 
		return false;
	}

	/**
	 * ritorna la distanza massima contenuta nella lista
	 * 
	 * 
	 * @return la massima distanza contenuta nella lista
	 */
	public int getMax() {
		int max=0;
		for(int i=0; i<delta.size(); i++ ) {
			if(delta.get(i)>max) {
				max=delta.get(i);
			}
		}
		return max;
	}

	/***
	 * rimuove l'intero m dalla lista di distanze
	 * @param m
	 * @return vero se l'elemento e' stato rimosso
	 */
	public boolean remove(Integer m) {
		return delta.remove(m);
	}

	/***
	 * Controlla se la lista di distanze e' vuota
	 * 
	 * @return vero se la lista e' vuota
	 */
	public boolean isEmpty() {
		return delta.isEmpty();
	}

	/***
	 * Controlla se tutti gli elementi in l sono contenuti nella lista di distanze
	 * 
	 * @param l lista di elementi
	 * @return vero se tutti gli elementi in l sono contenuti nella lista di distanze
	 */
	public boolean contains(List<Integer> l) {
		return delta.containsAll(l);
	}

	/***
	 * Rimuove la prima occorrenza di tutti gli elementi in l dalla lista di distanze 
	 * 
	 * @param l lista di elemtni da rimuovere
	 */
	public void removeDist(List<Integer> l) {
		for (Iterator<Integer> iterator = l.iterator(); iterator.hasNext();) {
			Integer elem = iterator.next();
			delta.remove(elem);
		}
	}

	/***
	 * Aggiunge tutti gli elementi contenuti in l alla lista di distanze
	 * 
	 * @param l lista di elementi da aggiungere
	 */
	public void add(List<Integer> l) {
		delta.addAll(l);
		Collections.sort(delta);
	}

	
}