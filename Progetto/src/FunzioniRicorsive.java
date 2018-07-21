import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;


public class FunzioniRicorsive {
	/**
	 * Calcla il fattoriale di n in maniera ricoriva
	 * 
	 * @param n il numero di cui vogliamo calcolare il fattoriale
	 * @return il fattoriale di n
	 * */	
	public static long fatt(int n) {
		if(n==0){
			return 1;
		}
		else {
			return n*fatt(n-1);
		}
	}
	
	/**
	 * Calcola la somma di due interi in maniera ricorsiva
	 * 
	 * @param x primo intero
	 * @param y secondo intero
	 * @return x + y
	 * */
	public static int sommaRicorsiva(int x, int y){
		if (y==0){
			return x;
		} else {
			return 1 + sommaRicorsiva(x, y-1);
		}
	}

	/**
	 * Calcola la moltiplicazione di due interi in maniera ricorsiva
	 * 
	 * @param x primo intero
	 * @param y secondo intero
	 * @return x * y
	 * */
	public static int moltRicorsiva(int x, int y){
		if (y==0) {
			return 0;
			
		} else {
			return x + moltRicorsiva(x,y-1);
		}
	}

	/**
	 * Calcola l'elevamento a potenza in maniera ricorsiva
	 * 
	 * @param b base
	 * @param e esponente
	 * @return b elevato all e
	 * */
	public static int potRicorsiva(int b, int e){
		if(e==0) {
			return 1;
		} else {
			return b*potRicorsiva(b,e-1);
		}
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("inserisci il numero di cui vuoi calcolare il fattoriale");
		int n = sc.nextInt();
		
		System.out.println("Il fattoriale di "+n+" e' "+fatt(n));
		
		System.out.println("inserisci i numeri di cui vuoi calcolare la somma");
		int x = sc.nextInt();
		int y = sc.nextInt();
		
		System.out.println("Somma ricorsiva = "+sommaRicorsiva(x, y)+" somma = "+ (x+y));
		System.out.println("Il metodo somma ricorsiva e' "+((sommaRicorsiva(x, y)==(x+y))?"corretto":"sbagliato"));
		
		System.out.println("inserisci i numeri di cui vuoi calcolare la moltiplicazione");
		int a = sc.nextInt();
		int b = sc.nextInt();
		
		System.out.println("moltiplicazione ricorsiva = "+moltRicorsiva(a, b)+" moltiplicazione = "+ (a*b));
		System.out.println("Il metodo moltiplicazione ricorsiva e' "+((moltRicorsiva(a, b)==(a*b))?"corretto":"sbagliato"));
		
		System.out.println("inserisci i numeri di cui vuoi calcolare la potenza");
		int c = sc.nextInt();
		int e = sc.nextInt();
		
		System.out.println("moltiplicazione ricorsiva = "+potRicorsiva(c, e)+" potenza = "+ Math.pow(c, e));
		System.out.println("Il metodo potenza ricorsiva e' "+((potRicorsiva(c, e)==Math.pow(c, e))?"corretto":"sbagliato"));
		
	}
	
	
}