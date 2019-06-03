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
	private ObjectOutputStream actOosg;
	private ObjectOutputStream foeOosg;
	private ObjectInputStream actOisg;
	private ObjectInputStream foeOisg;
	private int currentPlayer;

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
		currentPlayer = 1;
		try {
			TimeUnit.SECONDS.sleep(7);
			while (true) {
				String[] commands = new String[] {"LOSUJ", "" + currentPlayer}; 
				oosg1.writeObject(commands);
				oosg2.writeObject(commands);
				if (currentPlayer == 1) {
					actOisg = oisg1;
					actOosg = oosg1;
					foeOisg = oisg2;
					foeOosg = oosg2;
				} else {
					actOisg = oisg2;
					actOosg = oosg2;
					foeOisg = oisg1;
					foeOosg = oosg1;					
				}
				String[] res = (String[]) actOisg.readObject();
				System.out.println(res[0] + "    " + res[1] + "    " + res[2] + "    ");
				foeOosg.writeObject(new String[] {"FOE", res[2]});
				String[] tf = (String[]) foeOisg.readObject();
				if (tf[1].equalsIgnoreCase("true")) {
					System.out.println("ruch po moscie");
				} else {
					if (res[1].equalsIgnoreCase(res[2])) {
						System.out.println("Przeciwnik nie zgadl i spada do wody a gracz sie rusza");
					} else {
						System.out.println("Przeciwnik zgadl a gracz wpada do wody");
					}
				}
				currentPlayer = currentPlayer == 1 ? 2 : 1;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
