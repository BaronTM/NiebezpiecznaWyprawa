package game.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameServer implements Runnable {
	
	private ObjectOutputStream wyj;
	private ObjectInputStream wej;
	private Object o1;
	private Object o2;
	
	
	@Override
	public void run() {
		System.out.println("Uruchamianie serwera");
		try {
			ServerSocket gniazdoSerwera = new ServerSocket(4242);
			Socket clientSocket = gniazdoSerwera.accept();
			wyj = new ObjectOutputStream(clientSocket.getOutputStream());	
			wej = new ObjectInputStream(clientSocket.getInputStream());
			while ((o1 = wej.readObject()) != null) {
				o2 = wej.readObject();
				// dzialanie
			}
		} catch (Exception e) {
		}
	}	

}
