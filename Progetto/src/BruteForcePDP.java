import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * classe che realizza un insieme di metodi statici 
 * per risolvere il problema del compendio parziale
 * */
public class BruteForcePDP {
	
	/**
	 * calcola tutte le possibili combinazioni di k elementi tra n interi
	 * ciascuna combinazione contiene elementi da 1 a n incluso
	 * <p>
	 * Utilizza un array di integer come struttura di appoggio per generare 
	 * tutte le possibili combinazioni
	 * 
	 * @param n numero di interi 
	 * @param k numero di elementi di una combinazione
	 * @return insieme di liste di interi
	 * */
	public static Set<Set<Integer>> getAllSet(int n, int k){
		
		//creo la struttura dati da ritornare
		Set<Set<Integer>> allSet = new HashSet<Set<Integer>>();
				
		//inizializzo un array di interi crescenti da 1 a k inclusi
		Integer[] currList = new Integer[k];
		for (int i = 0; i < k; i++) {
			currList[i] = i+1;
		}
		System.out.println("Starting configuration "+Arrays.toString(currList));
		
		//creo un insieme dall'array
		Set<Integer> set = new HashSet<Integer>(Arrays.asList(currList));
		//aggiungo l'insieme agli insiemi da restituire
		allSet.add(set);				

		//calcolo il numero totale di combinazioni (coefficiente binomiale C(n,k))
		long total = FunzioniRicorsive.fatt(n) / (FunzioniRicorsive.fatt(n-k)*FunzioniRicorsive.fatt(k));
		System.out.println("total = "+total);
		//ciclo sul numero di combinazioni
		while(total>1){
			int i = k-1;
//			System.out.println("curr List = "+Arrays.toString(currList));
//			System.out.println("i = "+i+" n = " + n + " k = "+k);
			//parto dall'ultimo elemento e diminuisco il contatore sino a quando
			//l'elemento considerato non ha raggiunto il massimo valore
			while (currList[i] == n - (k - 1 - i)) {
			      i--;
			}
			
			//aggiorno l'elemento 
			currList[i] = currList[i] + 1;
//			System.out.println("update1 "+Arrays.toString(currList)+" i = "+i);
		    
			//aggiorno tutti gli elementi da quello corrente (node1) in poi ponendoli 
			//pari a node1 node1+1 node1+2 etc.
			for (int j = i + 1; j < k; j++) {
			  currList[j] = currList[j-1] + 1;
			}
			System.out.println("Next configuration "+Arrays.toString(currList));
		    
		    //creo una lista dall'array
			set = new HashSet<Integer>(Arrays.asList(currList));
			//aggiungo la lista all'insieme da restituire
			allSet.add(set);				

			//decremento il contatore
			total--;		   
			System.out.println("total "+total);
		}
		return allSet;
	}
	
	
	
	
	/***
	 * Calcola tutti gli insiemi di interi che corrispondono ad un PDP della lista di distanze L  
	 * facendo una ricerca esaustiva di tutti i possibili insiemi.
	 * 
	 * @param L una lista di distanze tra n punti
	 * @param n numero di punti nel pdp (potrebbe essere calcolato risolvendo l'equazione n(n-1)/2=L.size() con il vincolo n>0)
	 * @return un PDP per la lista L
	 */
	public static Set<Set<Integer>> computePDP(DistanceList L, int n){
		Set<Set<Integer>> PDP = new HashSet<Set<Integer>>();
		int max=L.getMax(); 
		//max-1 è n elementi e n-2 è k (calcolo tutte le possibili combinazioni di k elementi in n)
		Set<Set<Integer>> all=getAllSet(max-1,n-2); 
		//uso un iteratore per scandire tutti gli elementi dell'insieme 
		for(Iterator<Set<Integer>> it=all.iterator();it.hasNext();) {
			Set<Integer> el= it.next();
			el.add(0);                                          
			el.add(max);
			DistanceList delta=new DistanceList(el);
			//uso equals della classe Objects per comparare il valore delle due liste di tipo DistanceList
			if(delta.equals(L)) { 
				PDP.add(el);
			}
		}
		//ritorno l'insieme degli insiemi omeometrici di L
		return PDP;
	}
	     
	
	
	
	/**
	 * test dei metodi implementati
	 */
	public static void main(String[] args) {
		Set<Set<Integer>> all = getAllSet(5, 3);
		System.out.println("all set "+all);
		
		
		Set<Integer> s = new HashSet<Integer>();
		s.add(0); s.add(2); s.add(4); s.add(7); s.add(10);
		DistanceList L = new DistanceList(s);
		System.out.println("L = "+L);

		System.out.println("***********COMPUTE X *****************");
		Set<Set<Integer>> X = computePDP(L, 5);
		System.out.println("**************************************");		
		System.out.println("X = "+X);
	}
	
}