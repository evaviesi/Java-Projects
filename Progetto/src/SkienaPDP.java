import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class SkienaPDP {

		
		/***
		 * Calcola tutti gli insiemi di interi che corrispondono ad un PDP della lista di distanze L  
		 * Utilizza il metodo di Skiena
		 * 
		 * @param L una lista di distanze tra n punti
		 * @return un PDP per la lista L
		 */
		public static Set<Set<Integer>> computePDP(DistanceList L){
			int width = L.getMax();
			L.remove(width);
			Set<Integer> X = new HashSet<Integer>();
			X.add(0);
			X.add(width);
			Set<Set<Integer>> res = new HashSet<Set<Integer>>();
			Place(L, X,width,res );
			return res;
		}
		
		/***
		 * Metodo ricorsivo per calcolare tutti gli insiemi di 
		 * interi che corrispondono ad un PDP della lista di distanze L.
		 * 
		 * @param L Lista di distanze tra n punti (varia in dipendenza del contesto ricorsivo)
		 * @param X insieme di interi (varia in dipendenza del contesto ricorsivo)
		 * @param width massimo della lista L originaria (non varia)
		 * @param res insieme di tutti i possibili PDP per L 
		 */
		public static void Place(DistanceList L, Set<Integer> X, int width,Set<Set<Integer>> res){
			if(L.isEmpty()) {
				//passo X a ins perchè nelle chiamate successive l'insieme X viene modificato
				Set<Integer> ins= new HashSet<Integer>(X); 
				res.add(ins);
			}
			int y=L.getMax();
			//mi creo due liste in cui la distanza maggiore può essere generata dai due estremi di X
			List<Integer>var=computeDelta(y,X);
			List<Integer> lis=computeDelta(width-y,X);
			
			
			if(L.contains(var)) { 
				X.add(y);
				L.removeDist(var);
				Place(L,X,width,res);
				X.remove(y);
				L.add(var);
				}
			
			if(L.contains(lis)) { 
				X.add(width-y);
				L.removeDist(lis);
				Place(L,X,width,res);
				X.remove(width-y);
				L.add(lis);
				}
		}

		/***
		 * Calcola l'insieme delle distanze da un punto m a tutti i punti nell'insieme X
		 * 
		 * @param m punto rispetto al quale si vogliono calcolare le distanze
		 * @param X Insieme di punti
		 * @return insieme di distanze da m a tutti i punti in X
		 */
		private static List<Integer> computeDelta(int m, Set<Integer> X) {
			//creo l'insieme delle distanze da m a tutti i punti di X
			 List<Integer> li= new LinkedList<Integer>(); 
			for(Iterator<Integer> it=X.iterator(); it.hasNext();) {
				//aggiungo le distanze positive che posso calcolare a partire da m o da el
				int el= it.next();
				if(m-el>0) {
					li.add(m-el);
				} else {
					
					li.add(el-m); 
				}
				
			 }
			 return li; 
			 
		}	
		
		/**
		 * test dei metodi implementati
		 */
		public static void main(String[] args) {
			Set<Integer> s = new HashSet<Integer>();
			s.add(0); s.add(2); s.add(4); s.add(7); s.add(10);
			DistanceList L = new DistanceList(s);
			System.out.println("L = "+L);
			

			/****test computeDelta ***********/
			Set<Integer> test = new HashSet<Integer>();
			test.add(0); test.add(2); test.add(7); test.add(10);
			List<Integer> res = computeDelta(4,test);
			
			System.out.println("res = "+res+" L contains res ? "+ (L.isEmpty()?false:L.contains(res))+" (dovrebbe essere true)");

			/*****compute pdp Skiena*********/
			Set<Set<Integer>> X = computePDP(L);
			System.out.println("X "+X);

		}
}