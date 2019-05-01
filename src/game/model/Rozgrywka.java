package game.model;

import java.util.ArrayList;
import java.util.List;

public class Rozgrywka {

	private List<Gracz> listaGraczy;
	private List<Pionek> pionkiNaPodium;
	private int liczbaGraczy;

	private Gracz aktualnyGracz;		
	
	public Rozgrywka() {
		listaGraczy = new ArrayList<Gracz>();
		liczbaGraczy = 0;
	}
		
	public void dodajGracza(Gracz gracz) {
		listaGraczy.add(gracz);
		liczbaGraczy++;
		gracz.setGra(this);
	}
	
	public List<Gracz> getListaGraczy() {
		return listaGraczy;
	}
	

		
	
	
	
	
}
