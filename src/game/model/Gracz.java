package game.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class Gracz {
	
	private int id;
	private String imie;
	private int pkt;
	private List<Pionek> pionki;
	private Color kolor;
	private int liczbaPionkow;
	private Gra gra;
	private Pionek aktualnyPionek;
	
	public Gracz(int id, String imie, Color kolor) {
		this.imie = imie;
		this.id = id;
		liczbaPionkow = 4;
		this.kolor = kolor;;
		pionki = new ArrayList<Pionek>();
		for (int i = 0; i < liczbaPionkow; i++) {
			Pionek p = new Pionek(kolor, 1);
			pionki.add(p);
		}
		aktualnyPionek = pionki.get(0);
	}
	
	public List<Pionek> getPionki() {
		return pionki;
	}
	
	public void setGra(Gra gra) {
		this.gra = gra;
	}
	
	
	
}
