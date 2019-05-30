package game.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import game.controller.Main;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Gra extends UnicastRemoteObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5712053620818160194L;
	private transient Socket sock;
	private transient ObjectOutputStream oos;
	private transient ObjectInputStream ois;
	private Gracz g1;
	private Gracz g2;
	
	
	public Gra() throws RemoteException {
		super();
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
//			Registry registry = LocateRegistry.createRegistry(5850);
//			registry.rebind("Gra", this);
//			Environment env = new Environment();
//			SessionAcceptor sa = env.newSessionAcceptor(5058);
//			sa.acceptAll(this);
//			System.out.println(sa.getLocalAddress().toString());			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getSocketOfSecondPlayer() {
		Object obj = null;
		String s = null;
		try {
			oos.writeObject("Socket");
			while ((obj = ois.readObject()) != null) {
				System.out.println("pobrano objekt z serwera");
				System.out.println(obj.getClass());
				s = (String) obj;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public void remoteReader() {
		Object obj = null;
		try {
			while ((obj = ois.readObject()) != null) {
				System.out.println("pobrano objekt z serwera");
				System.out.println(obj.getClass());
				System.out.println((String) obj.toString());
				String s = (String) obj;
				Method m = Gra.class.getDeclaredMethod(s);
				m.invoke(this);
//				if (obj instanceof String[]) {
//					String[] s = (String[]) obj;
//					Method m = Gra.class.getDeclaredMethod("showInfo");
//					m.invoke(this, s[1]);
////					showInfo(s[1]);
//				} else if (obj instanceof Gra) {
//					String s = (String) obj;
//					Method m = Gra.class.getDeclaredMethod(s);
//					m.invoke(this);
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void startNewGame() {
		g1 = new Gracz(1, "Player1", Color.LAVENDER);
		g2 = new Gracz(2, "Player2", Color.BROWN);
		Pane pane = (Pane) Main.getMainStage().getScene().getRoot();
		Platform.runLater(() -> {
				int x = 45;
				int y = 765;
				for (Pionek p : g1.getPionki()) {
					p.setPosition(x, y);
					pane.getChildren().add(p);
					x += 80;
				}
				x = 415;
				for (Pionek p : g2.getPionki()) {
					p.setPosition(x, y);
					pane.getChildren().add(p);
					x += 80;
				}
		    });
		showInfo("ZACZYNAMY");
	}
	
	public void showInfo(String s) {
		Platform.setImplicitExit(false);
		Platform.runLater(() -> {
			Label t = Main.getInfoTxt();
			t.setText(s.toUpperCase());
			t.setVisible(true);
			Main.getInfoTxtSeq().play();
		});
	}
	
}
