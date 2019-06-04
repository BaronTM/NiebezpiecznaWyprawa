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
	private Pionek aktualnyPionek;
	
	public Gracz(int id, String imie, Color kolor) {
		this.imie = imie;
		this.id = id;
		pkt = 0;
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

	public Pionek getAktualnyPionek() {
		return aktualnyPionek;
	}
	
	public Pionek getAktualnyPionekDoWody() throws Exception {
		liczbaPionkow--;
		if (liczbaPionkow <0 ) return null;
		Pionek p = aktualnyPionek;
		pionki.remove(p);
		aktualnyPionek = pionki.get(0);
		return p;
	}
	
	
	
	
}
