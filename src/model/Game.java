package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.Socket;
import java.rmi.RemoteException;

import controller.Main;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Klasa opisujaca zachowania na planszy
 * Tu definiowane sa poszczegolne zachowania pionkow w zaleznosci od decyzji graczy
 * Tu wyswietlane sa opisy wskazujace na kolejne ruchy graczy oraz nastepstwa decyzji przeciwnika
 * Tu obsluzone jest przedwczesne zakonczenie gry przez jednego z graczy
 * @author Ernest Paprocki
 *
 */
public class Game implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5712053620818160194L;
	/**
	 * nieserializawane gniazdo serwera
	 */
	private transient Socket sock;
	/**
	 * nieserializowany obiekt wyjsciowy
	 */
	private transient ObjectOutputStream oos;
	/**
	 * nieserializowany obiekt wejsciowy
	 */
	private transient ObjectInputStream ois;
	/**
	 * gracz pierwszy
	 */
	private Gamer g1;
	/**
	 * gracz drugi
	 */
	private Gamer g2;
	/**
	 * klasa glowna
	 */
	private Main main;
	/**
	 * identyfikator gracza
	 */
	private int gamerId;

	/**
	 * konstruktor gracza
	 * @param nr numer gracza
	 * @param m klasa glowna
	 * @throws RemoteException obsluguje wyjatki metod zdalnych
	 */
	public Game(int nr, Main m) throws RemoteException {
		super();
		gamerId = nr;
		main = m;
	}

	/**
	 * @return zwraca gniazdo
	 */
	public Socket getSock() {
		return sock;
	}

	/**
	 * Metoda do ustawienia gniazda
	 * Definiowane sa obiekty wejscia i wyjscia, nastepuje obsluga watkow
	 * @param sock gniazdo typu Socket
	 */
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

	/**
	 * Metoda do zdalnego sterowania
	 * Wskazuje odebrane zachowanie pionkow zaleznie od decyzji graczy
	 * Obiekt wejsciowy reaguje na przekazywane ciagi tekstowe i na tej podstawie nastepuje realizacja decyzji graczy
	 * 1) Gdy jeden gracz jest na etapie losowania, przeciwnik oczekuje na decyzje
	 * 2) Potem inicjatywe przejmuje przeciwnik - decyduje czy gracz pierwszy nie oszukiwal, a ten czeka
	 * a) Gdy przekazany String 'WATER', nastepuje obsluga utraty pionka, a gdy to mozliwe na poczatku mostu ustawiany jest nastepny
	 * b) Gdy przekazany String 'MOVE', nastepuje przesuniecie pionka po moscie
	 * c) Gdy przekazany String 'END', nastepuje koniec gry, wyswietlane jest podsumowanie punktacji
	 * d) Gdy przekazany String 'ADD', nastepuje wyswietlenie komunikatu na planszach graczy
	 * *obsluzone jest rowniez przerwanieie gry przed jej koncem przez jednego z graczy
	 * *po zakonczeniu gry zamykane jest gniazdo zerwera
	 */
	public void remoteReader() {
		Object obj = null;
		try {
			obj = ois.readObject();
			String s = (String) obj;
			Method m = Game.class.getDeclaredMethod(s);
			m.invoke(this);
			while ((obj = ois.readObject()) != null) {
				String[] commands = (String[]) obj;
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
				} else if (commands[0].equalsIgnoreCase("ADD")) {
					showAddInfo(commands[1]);
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

	/**
	 * Metoda do przekazania parametrow wyjsciowych
	 * Przechwytuje przerwanie polaczenia przez jednego z graczy
	 * @param o przekazywany obiekt
	 */
	public void sendObj(Object o) {
		try {
			oos.writeObject(o);
		} catch (Exception e) {
			showInfo("POLACZENIE\nZOSTALO\nPRZERWANE");
			showScore("\n\n\nKONIEC GRY");
		}
	}

	/**
	 * Metoda do przygotowania pozycji starwych graczy
	 * Definiowani sa dwaj gracze, ich identyfikatory, nazwy, kolory pionkow
	 * Nastepuje ustawienie pionkow na pozycjach startowych w dolnej czesci okna
	 * Gracze otrzymuja informacje o przygotowaniu planszy i poczatku rozgrywki
	 */
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

	/**
	 * Metoda do wyswietlania informacji graczom
	 * W etykiecie nastepuje ustawienie informacji, przekonwertowane do wielkich liter
	 * Etykieta jest przekazywana na pierwszy plan i ustawiana na widoczna
	 * Nastepuje wyswietlenie informacji ze zdefiniowana animacja
	 * @param s przekazywany tekst
	 */
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

	/**
	 * Metoda do wyswietlania dodatkowej informacji graczom
	 * W etykiecie nastepuje ustawienie informacji, przekonwertowane do wielkich liter
	 * Etykieta jest przekazywana na pierwszy plan i ustawiana na widoczna
	 * Nastepuje wyswietlenie informacji ze zdefiniowana animacja
	 * @param s przekazywany tekst
	 */
	public void showAddInfo(String s) {
		Platform.setImplicitExit(false);
		Platform.runLater(() -> {
			try {
				Label t = Main.getAdditionalInfoTxt();
				t.setText(s.toUpperCase());
				t.toFront();
				t.setVisible(true);
				Main.getAdditionalInfoTxtSeq().play();
			} catch (NullPointerException npe) {
			}
		});
	}

	/**
	 * Metoda do wyswietlania punktacji graczy
	 * W etykiecie nastepuje ustawienie informacji, przekonwertowane do wielkich liter
	 * Etykieta jest przekazywana na pierwszy plan i ustawiana na widoczna
	 * Nastepuje wyswietlenie informacji ze zdefiniowana animacja
	 * @param s przekazywany tekst
	 */
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