package game.model;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;

public class Plansza extends Pane{
	private static int[][] leweWspDesek;
	private static int[][] praweWspDesek;
	private static int[][] kamienieWsp;
	
	static {
		leweWspDesek = new int[][] {{380, 510}, {362, 468}, {330, 420}, {298, 376}, {276, 342}, {254, 304}, {228, 259}, {204, 226}, {183, 191}};
		praweWspDesek = new int[][] {{464, 431}, {441, 398}, {404, 370}, {375, 329}, {354, 293}, {334, 258}, {304, 220}, {280, 185}, {258, 153}};
		kamienieWsp = new int[][] {{178, 144}, {100, 168}, {69, 224}, {55, 279}, {74, 340}, {48, 402}, {102, 439}, {49, 500}};
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
