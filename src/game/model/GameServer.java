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

import game.controller.Main;
import javafx.application.Platform;


public class GameServer implements Runnable {
	
	private Socket g1Socket;
	private Socket g2Socket;
	private ObjectOutputStream oosg1;
	private ObjectOutputStream oosg2;
	private ObjectInputStream oisg1;
	private ObjectInputStream oisg2;
	
	@Override
	public void run() {
		try {
			ServerSocket gniazdoSerwera = new ServerSocket(4242);	
			Main.setServerSocket(gniazdoSerwera);
			g1Socket = gniazdoSerwera.accept();
			oosg1 = new ObjectOutputStream(g1Socket.getOutputStream());		
			oisg1 = new ObjectInputStream(g1Socket.getInputStream());	
			Main.getExecutor().submit(new ObslugaGracza(g1Socket, oisg1));
			System.out.println("Socket gamer 1: " + g1Socket.getPort());
			
			g2Socket = gniazdoSerwera.accept();
			oosg2 = new ObjectOutputStream(g2Socket.getOutputStream());		
			oisg2 = new ObjectInputStream(g2Socket.getInputStream());				
			Main.getExecutor().submit(new ObslugaGracza(g2Socket, oisg2));
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
		} catch (SocketException e) {
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	
	public class ObslugaGracza implements Runnable {
		ObjectInputStream wej;
		Socket gniazdoKlienta;

		public ObslugaGracza(Socket socket, ObjectInputStream ois) {
			try {
				gniazdoKlienta = socket;
				wej = ois;
			} catch (Exception e) {
			}
		}

		@Override
		public void run() {
			Object o = null;
			
			try {
				while ((o = wej.readObject()) != null) {
					
				}
			} catch (Exception e) {
			}
		}
	}
	
}
