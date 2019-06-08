package model;

import javafx.scene.layout.Pane;

/**
 * Klasa definiujaca parametry dla polozenia pionkow na planszy
 * @author Ernest Paprocki
 *
 */
public class Board{
	/**
	 * tablica z parametrami dla pionkow pierwszego gracza - pionki po lewej stronie mostu
	 */
	private static int[][] leftPlanksPos;
	/**
	 * tablica z parametrami dla pionkow drugiego gracza - pionki po prawej stronie mostu
	 */
	private static int[][] rightPlanksPos;
	/**
	 * tablica z parametrami kamieni - pionki po przejsciu przez most
	 */
	private static int[][] stonesPos;
	/**
	 * plansza
	 */
	private static Board board;

	/**
	 * Konstruktor bezparametrowy planszy
	 */
	private Board() {}

	static {
		board = new Board();
		leftPlanksPos = new int[][] {{466, 613}, {448, 555}, {427, 500}, {401, 453}, {386, 392}, {376, 339}, {376, 290}, {364, 243}, {353, 204}, {407, 152}};
		rightPlanksPos = new int[][] {{582, 577}, {570, 516}, {547, 473}, {527, 422}, {509, 370}, {500, 316}, {482, 273}, {475, 232}, {467, 189}, {407, 152}};
		stonesPos = new int[][] {{33, 446}, {80, 409}, {39, 353}, {96, 307}, {27, 247}, {91, 214}, {33, 167}, {97, 133}};
	}

	/**
	 * @return zwraca parametry po lewej stronie mostu
	 */
	public static int[][] getLeftPlanksPos() {
		return leftPlanksPos;
	}

	/**
	 * @return zwraca parametry po prawej stronie mostu
	 */
	public static int[][] getRightPlanksPos() {
		return rightPlanksPos;
	}

	/**
	 * @return zwraca parametry dla kamieni
	 */
	public static int[][] getStonesPos() {
		return stonesPos;
	}

	/**
	 * @return zwraca plansze
	 */
	public static Board getBoard() {
		return board;
	}
}