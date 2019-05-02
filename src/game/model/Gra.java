package game.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.scene.paint.Color;

public class Gra {
	
	private Gracz gracz;
	private Gracz graczMock;
	private Gracz aktuGracz;
	private Socket sock;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private static int[][] leweWspDesek;
	private static int[][] praweWspDesek;
	private static int[][] kamienieWsp;
	
	public Gra() {
		gracz = new Gracz(1, "Pierwszy", Color.AQUA);
		graczMock = new Gracz(2, "Drugi", Color.LAVENDER);
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
	
	public Socket getSock() {
		return sock;
	}
	
	public void setSock(Socket sock) {
		this.sock = sock;
		try {
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	static {
		leweWspDesek = new int[][] {{380, 510}, {362, 468}, {330, 420}, {298, 376}, {276, 342}, {254, 304}, {228, 259}, {204, 226}, {183, 191}};
		praweWspDesek = new int[][] {{464, 431}, {441, 398}, {404, 370}, {375, 329}, {354, 293}, {334, 258}, {304, 220}, {280, 185}, {258, 153}};
		kamienieWsp = new int[][] {{178, 144}, {100, 168}, {69, 224}, {55, 279}, {74, 340}, {48, 402}, {102, 439}, {49, 500}};
	}
}
