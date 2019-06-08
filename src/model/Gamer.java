package model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

/**
 * Klasa gracza, definiuje konstruktor gracza
 * @author Ernest Paprocki
 *
 */
public class Gamer {

	/**
	 * identyfikator gracza
	 */
	private int id;
	/**
	 * nazwa gracza
	 */
	private String name;
	/**
	 * liczba punktow gracza
	 */
	private int points;
	/**
	 * lista pionkow gracza
	 */
	private List<Pawn> pawns;
	/**
	 * kolor pionkow gracza
	 */
	private Color color;
	/**
	 * liczba pionkow gracza
	 */
	private int numberPawns;
	/**
	 * aktualny pionek gracza
	 */
	private Pawn currentPawn;

	/**
	 * Konstruktor gracza
	 * @param id identyfikator gracza
	 * @param name nazwa gracza
	 * @param color kolor pionkow gracza
	 */
	public Gamer(int id, String name, Color color) {
		this.name = name;
		this.id = id;
		points = 0;
		numberPawns = 4;
		this.color = color;
		pawns = new ArrayList<Pawn>();
		for (int i = 0; i < numberPawns; i++) {
			Pawn p = new Pawn(color, 1);
			pawns.add(p);
		}
		currentPawn = pawns.get(0);
	}

	/**
	 * @return zwraca liste pionkow gracza
	 */
	public List<Pawn> getPawns() {
		return pawns;
	}

	/**
	 * @return zwraca aktualny pionek gracza
	 */
	public Pawn getCurrentPawn() {
		return currentPawn;
	}

}