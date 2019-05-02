package game.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.controller.Main;
import javafx.application.Platform;


public class GameServer implements Runnable {
	
	private Socket g1Socket;
	private Socket g2Socket;
	private ObjectOutputStream oosg1;
	private ObjectOutputStream oosg2;
	private ObjectInputStream oisg1;
	private ObjectInputStream oisg2;
	
	private Rozgrywka rozgrywka;

	
	@Override
	public void run() {
		try {
			ServerSocket gniazdoSerwera = new ServerSocket(4242);	
			Main.setServerSocket(gniazdoSerwera);
			g1Socket = gniazdoSerwera.accept();
			oosg1 = new ObjectOutputStream(g1Socket.getOutputStream());			
			Main.getExecutor().submit(new ObslugaGracza(g1Socket));
			System.out.println("Socket gamer 1: " + g1Socket.getPort());
			
			g2Socket = gniazdoSerwera.accept();
			oosg2 = new ObjectOutputStream(g2Socket.getOutputStream());			
			Main.getExecutor().submit(new ObslugaGracza(g2Socket));
			System.out.println("Socket gamer 2: " + g2Socket.getPort());
			gniazdoSerwera.close();
			Platform.runLater(() -> {
					Main.runGame();
			  });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public class ObslugaGracza implements Runnable {
		ObjectInputStream wej;
		Socket gniazdoKlienta;

		public ObslugaGracza(Socket socket) {
			try {
				gniazdoKlienta = socket;
				wej = new ObjectInputStream(gniazdoKlienta.getInputStream());
			} catch (Exception e) {
			}
		}

		@Override
		public void run() {
			Object o1 = null;
			Object o2 = null;
			
			try {
				while ((o1 = wej.readObject()) != null) {
					o2 = wej.readObject();
				}
			} catch (Exception e) {
			}
		}
	}
}
