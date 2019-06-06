package game.model;

import javafx.scene.layout.Pane;

public class Board extends Pane{
	private static int[][] leftPlanksPos;
	private static int[][] rightPlanksPos;
	private static int[][] stonesPos;

	static {
		leftPlanksPos = new int[][] {{466, 613}, {448, 555}, {427, 500}, {401, 453}, {386, 392}, {376, 339}, {376, 290}, {364, 243}, {353, 204}, {407, 152}};
		rightPlanksPos = new int[][] {{582, 577}, {570, 516}, {547, 473}, {527, 422}, {509, 370}, {500, 316}, {482, 273}, {475, 232}, {467, 189}, {407, 152}};
		stonesPos = new int[][] {{33, 446}, {80, 409}, {39, 353}, {96, 307}, {27, 247}, {91, 214}, {33, 167}, {97, 133}};

	}

	public static int[][] getLeftPlanksPos() {
		return leftPlanksPos;
	}

	public static int[][] getRightPlanksPos() {
		return rightPlanksPos;
	}

	public static int[][] getStonesPos() {
		return stonesPos;
	}

}