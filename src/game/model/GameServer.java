package game.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import game.controller.Main;
import javafx.application.Platform;

public class GameServer implements Runnable {

	private Socket g1Socket;
	private Socket g2Socket;
	private ObjectOutputStream oosg1;
	private ObjectOutputStream oosg2;
	private ObjectInputStream oisg1;
	private ObjectInputStream oisg2;
	private ImaginaryPlayer player1;
	private ImaginaryPlayer player2;
	private ImaginaryPlayer currentPlayer;
	private ImaginaryPlayer foePlayer;
	private int salvages;
	private Main main;

	{
		salvages = 0;
	}

	public GameServer(Main m) {
		main = m;
	}

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(4242);
			Main.setServerSocket(serverSocket);
			Platform.runLater(() -> {
				try {
					TimeUnit.MILLISECONDS.sleep(300);
					Main.getGame().setSock(new Socket("127.0.0.1", 4242));
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			});
			g1Socket = serverSocket.accept();
			oosg1 = new ObjectOutputStream(g1Socket.getOutputStream());
			oisg1 = new ObjectInputStream(g1Socket.getInputStream());

			g2Socket = serverSocket.accept();
			oosg2 = new ObjectOutputStream(g2Socket.getOutputStream());
			oisg2 = new ObjectInputStream(g2Socket.getInputStream());
			serverSocket.close();
			Platform.runLater(() -> {
				Main.runGame();
				try {
					oosg1.writeObject("startNewGame");
					oosg2.writeObject("startNewGame");
				} catch (SecurityException | IOException e) {
					e.printStackTrace();
				}
			});
			checkIfConnected(oisg1, "Gracz 1 polaczony");
			checkIfConnected(oisg2, "Gracz 2 polaczony");
			letsPlay();
		} catch (SocketException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkIfConnected(ObjectInputStream o, String s) {
		Object obj;
		try {
			obj = o.readObject();
			String str = (String) obj;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	private void letsPlay() {
		player1 = new ImaginaryPlayer(1, oisg1, oosg1);
		player2 = new ImaginaryPlayer(2, oisg2, oosg2);
		currentPlayer = player1;
		foePlayer = player2;

		try {
			TimeUnit.SECONDS.sleep(7);

			currentPlayer.getObjectOutputStream().writeObject(player1.moveCounterCommand());
			foePlayer.getObjectOutputStream().writeObject(player1.moveCounterCommand());
			currentPlayer.getObjectOutputStream().writeObject(player2.moveCounterCommand());
			foePlayer.getObjectOutputStream().writeObject(player2.moveCounterCommand());

			while (true) {
				String[] commands = new String[] { "LOSUJ", "" + currentPlayer.getId() };
				player1.getObjectOutputStream().writeObject(commands);
				player2.getObjectOutputStream().writeObject(commands);

				String[] res = (String[]) currentPlayer.getObjectInputStream().readObject();
				foePlayer.getObjectOutputStream().writeObject(new String[] { "FOE", res[2] });
				String[] tf = (String[]) foePlayer.getObjectInputStream().readObject();
				if (tf[1].equalsIgnoreCase("true")) {
					currentPlayer.getObjectOutputStream().writeObject(new String[] {"ADD", "PRZECIWNIK UWIERZYL" });
					int step = Integer.parseInt(res[2]);
					currentPlayer.addToMove(step);
					String[] move = currentPlayer.moveCounterCommand();
					currentPlayer.getObjectOutputStream().writeObject(move);
					foePlayer.getObjectOutputStream().writeObject(move);
				} else {
					if (res[1].equalsIgnoreCase(res[2])) {
						currentPlayer.getObjectOutputStream()
								.writeObject(new String[] {"ADD", "PRZECIWNIK SPRAWDZIL\nI NIE ZGADL" });
						foePlayer.getObjectOutputStream().writeObject(new String[] {"ADD", "NIE ZGADLES" });
						String[] water = foePlayer.counterToWater();
						currentPlayer.getObjectOutputStream().writeObject(water);
						foePlayer.getObjectOutputStream().writeObject(water);
						int step = Integer.parseInt(res[2]);
						currentPlayer.addToMove(step);
						String[] move = currentPlayer.moveCounterCommand();
						currentPlayer.getObjectOutputStream().writeObject(move);
						foePlayer.getObjectOutputStream().writeObject(move);
						String[] moveNewCounter = foePlayer.moveCounterCommand();
						currentPlayer.getObjectOutputStream().writeObject(moveNewCounter);
						foePlayer.getObjectOutputStream().writeObject(moveNewCounter);
					} else {
						currentPlayer.getObjectOutputStream()
								.writeObject(new String[] { "ADD", "PRZECIWNIK SPRAWDZIL\nI ZGADL" });
						foePlayer.getObjectOutputStream().writeObject(new String[] {"ADD", "ZGADLES" });
						String[] water = currentPlayer.counterToWater();
						currentPlayer.getObjectOutputStream().writeObject(water);
						foePlayer.getObjectOutputStream().writeObject(water);
						String[] move = currentPlayer.moveCounterCommand();
						currentPlayer.getObjectOutputStream().writeObject(move);
						foePlayer.getObjectOutputStream().writeObject(move);
					}
				}
				if (currentPlayer.getCurrentCounterPositionStep() >= 9) {
					TimeUnit.SECONDS.sleep(2);
					int moveX = Board.getStonesPos()[salvages][0];
					int moveY = Board.getStonesPos()[salvages][1];
					salvages++;
					currentPlayer.setCurrentCounterPosition(new int[] { moveX, moveY });
					String[] move = currentPlayer.moveCounterCommand();
					currentPlayer.getObjectOutputStream().writeObject(move);
					foePlayer.getObjectOutputStream().writeObject(move);
					currentPlayer.newSalvage();
					String[] moveNewCounter = currentPlayer.moveCounterCommand();
					currentPlayer.getObjectOutputStream().writeObject(moveNewCounter);
					foePlayer.getObjectOutputStream().writeObject(moveNewCounter);
				}
				if (currentPlayer.getRemainCounters() <= 0 || foePlayer.getRemainCounters() <= 0) {
					String[] msgC;
					String[] msgF;
					int scoreC = currentPlayer.getFinishScore();
					int scoreF = foePlayer.getFinishScore();
					if (scoreC > scoreF) {
						msgC = new String[] { "END", "WYGRALES", "" + scoreC, "" + scoreF };
						msgF = new String[] { "END", "PRZEGRALES", "" + scoreF, "" + scoreC };
					} else if (scoreF > scoreC) {
						msgC = new String[] { "END", "PRZEGRALES", "" + scoreC, "" + scoreF };
						msgF = new String[] { "END", "WYGRALES", "" + scoreF, "" + scoreC };
					} else {
						msgC = new String[] { "END", "REMIS", "" + scoreC, "" + scoreF };
						msgF = new String[] { "END", "REMIS", "" + scoreF, "" + scoreC };
					}
					currentPlayer.getObjectOutputStream().writeObject(msgC);
					foePlayer.getObjectOutputStream().writeObject(msgF);
					break;
				}
				if (currentPlayer == player1) {
					currentPlayer = player2;
					foePlayer = player1;
				} else {
					currentPlayer = player1;
					foePlayer = player2;
				}
			}
		} catch (Exception e) {
			main.getGame().showInfo("POLACZENIE\nZOSTALO\nPRZERWANE");
			main.getGame().showScore("\n\n\nKONIEC GRY");
		} finally {
			try {
				g1Socket.close();
				g2Socket.close();
			} catch (IOException e) {
			}
		}
	}

}