package game.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class Gamer {

	private int id;
	private String name;
	private int points;
	private List<Pawn> pawns;
	private Color color;
	private int numberPawns;
	private Pawn currentPawn;

	public Gamer(int id, String name, Color color) {
		this.name = name;
		this.id = id;
		points = 0;
		numberPawns = 4;
		this.color = color;;
		pawns = new ArrayList<Pawn>();
		for (int i = 0; i < numberPawns; i++) {
			Pawn p = new Pawn(color, 1);
			pawns.add(p);
		}
		currentPawn = pawns.get(0);
	}

	public List<Pawn> getPawns() {
		return pawns;
	}

	public Pawn getCurrentPawn() {
		return currentPawn;
	}

}