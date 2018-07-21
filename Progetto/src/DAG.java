import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


 /**
  * La classe DAG rappresenta un grafo diretto aciclico mediante liste di adiacenza.
  * In particolare si e' voluto dare un'implementazione che utilizzasse classi
  * standard di java.util.
  * Di conseguenza:
  * 1. la lista dei nodi e' rappresentata da una HashMap per poter accedere ai nodi in 
  * tempo costante
  * 2. la lista dei nodi adiacenti e' rappresentata da un HashSet di nodi orientati, in
  * modo tale da poter verificare/accedere al nodo adiacente in tempo costante.
  * Anziche' rappresentare il nodo adiacente e il peso dell'arco si e' preferito
  * rappresentare l'arco completo.
  *
  */

public class DAG extends Grafo {
    //time lo uso per incrementare i tempi 
	private int time = 0;  
	//tempo di fine visita, 0 all'inizio
	private Map<Object,Integer> endValues = new HashMap<Object,Integer>();  
	//tempo di inzio visita
	private Map<Object,Integer> startValues = new HashMap<Object,Integer>();  
	
	
	public boolean add(ArcoOrdinato a) {
		return add(a.getFrom(),a.getTo(),a.getValue());
	}
	
	   /**aggiunge un arco tra i nodi x e y se tale arco non e' gia' presente e restituisce true, 
	   * altrimenti non modifica il grafo e restituisce false. 	
	   */
		public boolean add(Object x, Object y, Object value) {
	    boolean flag = false, flag1 = false;
	    if (!nodi.containsKey(x))
	      add(x);
	    if (!nodi.containsKey(y))
	      add(y);
	    ArcoOrdinato a = new ArcoOrdinato(x,y,value);
	    flag = ( (Set<Arco>) nodi.get(x) ).add(a);
	    flag1 =( (Set<Arco>) nodi.get(y) ).add(a);
	    flag = flag && flag1;
	    if (flag)
	      nArchi++;
	    return flag;
	  }

	  /**
	   * Restituisce l'insieme di archi incidenti entranti nel nodo "nodo",
	   * se nodo e' presente nel grafo, null altrimenti 
	   * 
	   * @param nodo nodo di cui si vuole conoscere l'insieme di archi incidenti
	   * @return l'insieme di archi incidenti sul nodo nodo, ritorna gli archi entranti in un nodo
	   * se nodo e' presente nel grafo null altrimenti
	   */
	  public Set<ArcoOrdinato> getInEdgeSet(Object nodo) {  
	    if (nodi.containsKey(nodo)){
	    	//System.out.println("building inset for "+nodo);
	    	Set<ArcoOrdinato> res = new HashSet<ArcoOrdinato>(); 
	    	for (Arco o : getEdgeSet(nodo)) {
				ArcoOrdinato a = (ArcoOrdinato) o;
				if (a.getTo().equals(nodo)){
					res.add(a);
				}
			}
	    	return res;
	    	
	    } 
	    else
	      return null;
	  }

	  /**
	   * Restituisce l'insieme di archi incidenti uscenti dal nodo "nodo",
	   * se nodo e' presente nel grafo, null altrimenti 
	   * 
	   * @param nodo nodo di cui si vuole conoscere l'insieme di archi incidenti
	   * @return l'insieme di archi incidenti sul nodo nodo, ritorna tutti gli archi uscenti di un nodo
	   * se nodo e' presente nel grafo null altrimenti
	   */
	  public Set<ArcoOrdinato> getOutEdgeSet(Object nodo) { 
		    if (nodi.containsKey(nodo)){
		    	Set<ArcoOrdinato> res = new HashSet<ArcoOrdinato>(); 
		    	for (Arco  o : getEdgeSet(nodo)) {
					ArcoOrdinato a = (ArcoOrdinato) o;
					if (a.getFrom().equals(nodo)){
						res.add(a);
					}
		    	}
		    	return res;
		     } 
		    //se il nodo e' presente nel grafo
		    else
		      return null;
	  }
	  
	  public String toString() {
		    StringBuffer out = new StringBuffer();
		    Object nodo;
		    ArcoOrdinato a;
		    Iterator arcoI;
		    Iterator nodoI = nodi.keySet().iterator();
		    while (nodoI.hasNext()) {
		      arcoI = ((Set) nodi.get( nodo = nodoI.next() )).iterator();
		      out.append("Nodo " + nodo.toString() + ": ");
		      while (arcoI.hasNext()) {
		        a = (ArcoOrdinato) arcoI.next();
		        //out.append( ((a.x == nodo ) ? a.y.toString() : a.x.toString()) + "("+a.value.toString()+"), ");
		        out.append(a.toString()+", ");
		      }
		      out.append("\n");
		    }
		    return out.toString();
		  }
	  
	  
	  
	  /**
	   * Restituisce un ordine topologico di un DAG, 
	   * eseguendo una DFS. 
	   *  
	   * @return una ordine topologico del grafo partendo da startingNode
	   */
	  public List<Object> getTopologicalOrder(){ 
		  //creo un insieme a cui passo l'insieme di tutti i nodi del grafo
		  Set<Object> vert= new HashSet<Object>(getNodeSet());
			for(Iterator<Object> iterator=vert.iterator(); iterator.hasNext();){
			  	Object v=iterator.next();
			  	//inizializzo i tempi di inizio e fine visita a 0 per tutti i nodi
			  	startValues.put(v, 0);
				endValues.put(v, 0);
				}
			for(Iterator<Object> it=vert.iterator(); it.hasNext();){
				Object f=it.next();
				//se il tempo di inizio visita di un nodo è 0, eseguo la dfs sul nodo
				if(startValues.get(f)==0){
					dfs(f);
					}
				}
			//creo una lista che rappresenta l'ordine topologico dei vertici
			List<Object> res= new LinkedList<Object>();
			for(Iterator<Object> iterator=vert.iterator(); iterator.hasNext();){
				iterator.next();
				//aggiungo alla lista i nodi in ordine di tempo di fine visita dal maggiore al minore
				res.add(pullMax(endValues));
				}
			return res;
		  }
		 
	  
	  /**
	   * Data una map di nodi ad interi restituisce (ed elimina dalla map) il nodo che ha valore piu' alto.
	   *  
	   * @return nodo (object) con valore piu' alto. Il node viene eliminato dalla map. 
	   * Ritorna l'oggetto che ha l'intero più alto e lo leva dalla  mappa,
	   *  la mappa avrà in meno l'elemento massimo che ci viene passato, chiamo pullMax finchè endValues non è vuota/finita.
	   */
	  private Object pullMax(Map<Object,Integer> m) { 
		  Object best = null;
		  //inizializzo il valore massimo a 0
		  Integer bestValue = 0;
		  //inizializzo un iteratore che accede all'insieme delle chiavi (valore del nodo)
		  for (Iterator<Object> iterator = m.keySet().iterator(); iterator.hasNext();) {
			Object curr = iterator.next();
			//accedo al valore del nodo con get
			Integer currValue = m.get(curr);
			if((best == null) || (currValue > bestValue)){
				best = curr;
				bestValue = currValue;
			}
		  }
		  //il valore più alto viene rimosso ogni volta per proseguire l'ordinamento
		  m.remove(best);
		  return best;
	  }

	/**
	   * Metodo ricorsivo che esegue la dfs dal nodo "node"
	   * 
	   * @param node nodo da cui partire per la visita DFS
	   * @param nodes marcatura dei nodi da visitare
	   * @param res ordine topologico che stiamo calcolando
	   */
	private void dfs(Object node) {  
		time++;
		//inizio la visita dal nodo node con il tempo di inizio aggiornato a time=1
		startValues.put(node, time);
		//creo un insieme a cui passo tutti gli archi uscenti da node che rappresentano i suoi vicini
		Set<ArcoOrdinato> vicini=new HashSet<ArcoOrdinato>(getOutEdgeSet(node));
		for(Iterator<ArcoOrdinato> iterator=vicini.iterator(); iterator.hasNext();){
			ArcoOrdinato a=iterator.next();
			//resituisco u il nodo di arrivo dell'arco
			Object u=a.getTo();
			//controllo se il nodo u è stato visitato e faccio  la dfs se non l'ho ancora marcato
			if(startValues.get(u)==0){
				dfs(u);
			}
			else{
				//se il tempo di fine è ancora uguale a 0 lancio un'eccezione perchè ho trovato un ciclo
				if(endValues.get(u)==0){
					IllegalArgumentException e= new IllegalArgumentException("Errore: G non è un DAG!");
					throw e;
					//System.out.println("errore");
					}
				}
		}
		time++;
		//aggiorno endValues a time++ a seconda del tempo corrente
		endValues.put(node, time); 
	}

		

	public static void main(String[] args) {
		    DAG g = new DAG();
		    g.add(new ArcoOrdinato("e","d",new Integer(1)));
		    g.add(new ArcoOrdinato("d","f",new Integer(1)));
		    g.add(new ArcoOrdinato("d","b",new Integer(3)));
		    g.add(new ArcoOrdinato("d","c",new Integer(4)));
		    g.add(new ArcoOrdinato("b","c",new Integer(2)));

		    System.out.println("Il grafo G e':\n" + g);
		    System.out.println("L'insieme di archi e': " + g.getEdgeSet());

		    System.out.println(g.getTopologicalOrder());
		    System.out.println();

		    //g2 contiene un ciclo, tra d-b-c, nell'ordinamento topologico mi darà errore perchè non è possibile ottenerlo
		    DAG g2 = new DAG();
		    g2.add(new ArcoOrdinato("e","d",new Integer(1)));
		    g2.add(new ArcoOrdinato("d","f",new Integer(1)));
		    g2.add(new ArcoOrdinato("d","b",new Integer(3)));
		    g2.add(new ArcoOrdinato("c","d",new Integer(4)));
		    g2.add(new ArcoOrdinato("b","c",new Integer(2)));

		    System.out.println("Il grafo G2 e':\n" + g2);
		    System.out.println("L'insieme di archi e': " + g2.getEdgeSet());

		    System.out.println(g2.getTopologicalOrder());
		    System.out.println();
		    
		  }
}
	
	
