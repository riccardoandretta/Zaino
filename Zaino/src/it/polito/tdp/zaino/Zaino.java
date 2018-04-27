package it.polito.tdp.zaino;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Zaino {

	private int capienza; // peso max sopportato dallo zaino
	private List<Pezzo> pezzi; // pezzi da provare ad inserire

	/**
	 * Inizializza un nuovo problema dello zaino
	 * 
	 * @param capienza
	 *            Massimo peso che lo zaino può sopportare
	 */
	public Zaino(int capienza) {
		this.capienza = capienza;
		this.pezzi = new ArrayList<>();
	}

	/**
	 * Aggiunge un nuovo pezzo al problema dello zaino da risolvere. Il nuovo pezzo
	 * deve essere diverso da quelli esistenti.
	 * 
	 * @param p
	 *            il {@link Pezzo} da aggiungere
	 */

	public void add(Pezzo p) {
		if (!pezzi.contains(p))
			pezzi.add(p);
		else
			throw new IllegalArgumentException("Pezzo duplicato " + p);
	}

	public Set<Pezzo> risolvi() { // Metodo che prepara la struttura dati e chiama l'algoritmo ricorsivo a livello
									// 0

		Set<Pezzo> parziale = new HashSet<>();
		Set<Pezzo> best = new HashSet<>(); // parametro che non serve alla ricorsione in quanto tale, ma serve a salvare
											// il risultato
		scegli(parziale, 0, best);

		return best;
	}

	private void scegli(Set<Pezzo> parziale, int livello, Set<Pezzo> best) {

		if (costo(parziale) > costo(best)) {
			// Trovata soluzione miglire --> devo salvarla in 'best'

			// best = parziale; salvo i riferimenti, ma non ho copiato nulla!!! Deve essere
			// una fotografia statica del migliore in quel momento (e parziale varia in
			// continuazione); inoltre sto agendo su best localmente al metodo; la variabile
			// che ho dichiarato prima (in risolvi) non l'ho toccata minimamente
			
			best.clear();
			best.addAll(parziale);

			System.out.println(parziale);

		}

		for (Pezzo p : pezzi)
			if (!parziale.contains(p)) {
				// 'p' non c'è ancora, provo a metterlo
				// se non supera la capacità
				if (peso(parziale) + p.getPeso() <= capienza) {
					// prova a mettere 'p' nello zaino
					parziale.add(p);
					// e delegare la ricerca al livello successivo
					scegli(parziale, livello + 1, best);
					// poi rimetti le cose a posto (togli 'p')
					parziale.remove(p);
				}
			}

	}

	private int peso(Set<Pezzo> parziale) {
		int r = 0;
		for (Pezzo p : parziale) {
			r += p.getPeso();
		}
		return r;
	}

	private int costo(Set<Pezzo> parziale) {
		int r = 0;
		for (Pezzo p : parziale) {
			r += p.getCosto();
		}
		return r;
	}

	public static void main(String[] args) {

		Zaino z = new Zaino(15);
		z.add(new Pezzo(12, 4, "Verde"));
		z.add(new Pezzo(2, 2, "Azzurro"));
		z.add(new Pezzo(1, 1, "Salmone"));
		z.add(new Pezzo(4, 10, "Giallo"));
		z.add(new Pezzo(1, 2, "Grigio"));

		Set<Pezzo> soluzione = z.risolvi();

		System.out.println(soluzione);

	}

}
