package game.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

import game.controller.Main;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Game implements Serializable {

	private static final long serialVersionUID = 5712053620818160194L;
	private transient Socket sock;
	private transient ObjectOutputStream oos;
	private transient ObjectInputStream ois;
	private Gamer g1;
	private Gamer g2;
	private Main main;
	private int gamerId;

	public Game(int nr, Main m) throws RemoteException {
		super();
		gamerId = nr;
		main = m;
	}

	public Socket getSock() {
		return sock;
	}

	public void setSock(Socket sock) {
		this.sock = sock;
		try {
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
			Main.getExecutor().submit(new Thread(() -> remoteReader()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void remoteReader() {
		Object obj = null;
		try {
			obj = ois.readObject();
			String s = (String) obj;
			Method m = Game.class.getDeclaredMethod(s);
			m.invoke(this);
			while ((obj = ois.readObject()) != null) {
				String[] commands = (String[]) obj;
				for (String saa : commands) {
					System.out.println(saa);
				}
				if (commands[0].equalsIgnoreCase("LOSUJ")) {
					int nr = Integer.parseInt(commands[1]);
					if (nr == gamerId) {
						showInfo("Rzuc kostka");
						main.getMainWindowController().getDrawBut().setVisible(true);
					} else {
						showInfo("Przeciwnik rzuca");
					}
				} else if (commands[0].equalsIgnoreCase("FOE")) {
					Platform.runLater(() -> {
						main.getMainWindowController().comunicate(commands[1]);
					});
				} else if (commands[0].equalsIgnoreCase("WATER")) {
					int gamerNr = Integer.parseInt(commands[1]);
					int counterNr = Integer.parseInt(commands[2]);
					Gamer g = gamerNr == 1 ? g1 : g2;
					if (gamerNr == 1)
						g.getPawns().get(counterNr).fallIntoWater(300, 400);
					else
						g.getPawns().get(counterNr).fallIntoWater(650, 400);
				} else if (commands[0].equalsIgnoreCase("MOVE")) {
					int gamerNr = Integer.parseInt(commands[1]);
					int counterNr = Integer.parseInt(commands[2]);
					int moveX = Integer.parseInt(commands[3]);
					int moveY = Integer.parseInt(commands[4]);
					Gamer g = gamerNr == 1 ? g1 : g2;
					if (counterNr < g.getPawns().size())
						g.getPawns().get(counterNr).moveOnBridge(moveX, moveY);
				} else if (commands[0].equalsIgnoreCase("END")) {
					String s1 = commands[1];
					String s2 = commands[2];
					String s3 = commands[3];
					String result = String.format("%s\n%s\n%-15s %s\n%-13s %s", "KONIEC", s1, "WYNIK", s2, "PRZECIWNIK",
							s3);
					showScore(result);
					break;
				} else {
					showInfo(commands[0]);
				}
			}
		} catch (Exception e) {
			showInfo("POLACZENIE\nZOSTALO\nPRZERWANE");
			showScore("\n\n\nKONIEC GRY");
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendObj(Object o) {
		try {
			oos.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startNewGame() {
		g1 = new Gamer(1, "Player1", Color.LAVENDER);
		g2 = new Gamer(2, "Player2", Color.BROWN);
		Pane pane = (Pane) Main.getMainStage().getScene().getRoot();
		Platform.runLater(() -> {
			int x = 45;
			int y = 765;
			for (Pawn p : g1.getPawns()) {
				p.moveOnBridge(x, y);
				pane.getChildren().add(p);
				x += 80;
			}
			x = 415;
			for (Pawn p : g2.getPawns()) {
				p.moveOnBridge(x, y);
				pane.getChildren().add(p);
				x += 80;
			}
		});
		showInfo("ZACZYNAMY");
		sendObj("connected");
	}

	public void showInfo(String s) {
		Platform.setImplicitExit(false);
		Platform.runLater(() -> {
			try {
				Label t = Main.getInfoTxt();
				t.setText(s.toUpperCase());
				t.toFront();
				t.setVisible(true);
				Main.getInfoTxtSeq().play();
			} catch (NullPointerException npe) {
			}
		});
	}

	public void showScore(String s) {
		Platform.setImplicitExit(false);
		Platform.runLater(() -> {
			try {
				Label t = Main.getScoreInfoTxt();
				t.setText(s.toUpperCase());
				t.toFront();
				t.setVisible(true);
				Main.getScoreTxtSeq().play();
			} catch (NullPointerException npe) {
			}
		});
	}

}