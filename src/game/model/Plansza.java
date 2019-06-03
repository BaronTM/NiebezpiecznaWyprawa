package game.model;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;

public class Plansza extends Pane{
	private static int[][] leweWspDesek;
	private static int[][] praweWspDesek;
	private static int[][] kamienieWsp;
	
	static {
		leweWspDesek = new int[][] {{466, 613}, {448, 555}, {427, 500}, {401, 453}, {386, 392}, {376, 339}, {376, 290}, {364, 243}, {353, 204}};
		praweWspDesek = new int[][] {{582, 577}, {570, 516}, {547, 473}, {527, 422}, {509, 370}, {500, 316}, {482, 273}, {475, 232}, {467, 189}};
		kamienieWsp = new int[][] {{33, 438}, {80, 409}, {39, 353}, {96, 299}, {27, 247}, {91, 214}, {33, 167}, {97, 133}, {407, 152}};
		
	}
	
	public static int[][] getLeweWspDesek() {
		return leweWspDesek;
	}
	
	public static int[][] getPraweWspDesek() {
		return praweWspDesek;
	}
	
	public static int[][] getKamienieWsp() {
		return kamienieWsp;
	}
	
}
