package game.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

	{
		salvages = 0;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket gniazdoSerwera = new ServerSocket(4242);
			Main.setServerSocket(gniazdoSerwera);
			g1Socket = gniazdoSerwera.accept();
			oosg1 = new ObjectOutputStream(g1Socket.getOutputStream());
			oisg1 = new ObjectInputStream(g1Socket.getInputStream());
			System.out.println("Socket gamer 1: " + g1Socket.getPort());

			g2Socket = gniazdoSerwera.accept();
			oosg2 = new ObjectOutputStream(g2Socket.getOutputStream());
			oisg2 = new ObjectInputStream(g2Socket.getInputStream());
			System.out.println("Socket gamer 2: " + g2Socket.getPort());
			gniazdoSerwera.close();
			Platform.runLater(() -> {
				Main.runGame();
				try {
					oosg1.writeObject("startNewGame");
					oosg2.writeObject("startNewGame");
				} catch (SecurityException | IOException e) {
					// TODO Auto-generated catch block
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
			if (str.equals("connected"))
				System.out.println(s);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
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
			
			currentPlayer.getObjectOutputStream().writeObject(player1.moveCounter());
			foePlayer.getObjectOutputStream().writeObject(player1.moveCounter());
			currentPlayer.getObjectOutputStream().writeObject(player2.moveCounter());
			foePlayer.getObjectOutputStream().writeObject(player2.moveCounter());
			
			while (true) {
				String[] commands = new String[] {"LOSUJ", "" + currentPlayer.getId()}; 
				player1.getObjectOutputStream().writeObject(commands);
				player2.getObjectOutputStream().writeObject(commands);
				
				String[] res = (String[]) currentPlayer.getObjectInputStream().readObject();
				System.out.println(res[0] + "    " + res[1] + "    " + res[2] + "    ");
				foePlayer.getObjectOutputStream().writeObject(new String[] {"FOE", res[2]});
				String[] tf = (String[]) foePlayer.getObjectInputStream().readObject();
				if (tf[1].equalsIgnoreCase("true")) {
					System.out.println("ruch po moscie");
					currentPlayer.getObjectOutputStream().writeObject(new String[] {"PRZECIWNIK UWIERZYL"});
					int step = Integer.parseInt(res[2]);
					currentPlayer.addToMove(step);
					String[] move = currentPlayer.moveCounter();
					currentPlayer.getObjectOutputStream().writeObject(move);
					foePlayer.getObjectOutputStream().writeObject(move);
				} else {
					if (res[1].equalsIgnoreCase(res[2])) {
						System.out.println("Przeciwnik nie zgadl i spada do wody a gracz sie rusza");
						currentPlayer.getObjectOutputStream().writeObject(new String[] {"PRZECIWNIK SPRAWDZIL\nI NIE ZGADL"});
						foePlayer.getObjectOutputStream().writeObject(new String [] {"NIE ZGADLES"});
						String[] water = foePlayer.counterToWater();						
						currentPlayer.getObjectOutputStream().writeObject(water);
						foePlayer.getObjectOutputStream().writeObject(water);
						int step = Integer.parseInt(res[2]);
						currentPlayer.addToMove(step);
						String[] move = currentPlayer.moveCounter();
						currentPlayer.getObjectOutputStream().writeObject(move);
						foePlayer.getObjectOutputStream().writeObject(move);
						String[] moveNewCounter = foePlayer.moveCounter();
						currentPlayer.getObjectOutputStream().writeObject(moveNewCounter);
						foePlayer.getObjectOutputStream().writeObject(moveNewCounter);
					} else {
						System.out.println("Przeciwnik zgadl a gracz wpada do wody");
						currentPlayer.getObjectOutputStream().writeObject(new String[] {"PRZECIWNIK SPRAWDZIL\nI ZGADL"});
						foePlayer.getObjectOutputStream().writeObject(new String [] {"ZGADLES"});
						String[] water = currentPlayer.counterToWater();						
						currentPlayer.getObjectOutputStream().writeObject(water);
						foePlayer.getObjectOutputStream().writeObject(water);
						String[] move = currentPlayer.moveCounter();
						currentPlayer.getObjectOutputStream().writeObject(move);
						foePlayer.getObjectOutputStream().writeObject(move);
					}
				}
				if (currentPlayer.getCurrentCounterPositionStep() >= 9) {
					int moveX = Plansza.getKamienieWsp()[salvages][0];
					int moveY = Plansza.getKamienieWsp()[salvages][1];
					salvages++;
					currentPlayer.setCurrentCounterPosition(new int[] {moveX, moveY});
					String[] move = currentPlayer.moveCounter();
					currentPlayer.getObjectOutputStream().writeObject(move);
					foePlayer.getObjectOutputStream().writeObject(move);
					currentPlayer.newSalvage();
					String[] moveNewCounter = currentPlayer.moveCounter();
					currentPlayer.getObjectOutputStream().writeObject(moveNewCounter);
					foePlayer.getObjectOutputStream().writeObject(moveNewCounter);
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
			e.printStackTrace();
		}

	}

}
