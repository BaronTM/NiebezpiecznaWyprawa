package game.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.cojen.dirmi.Environment;
import org.cojen.dirmi.Session;
import org.cojen.dirmi.SessionAcceptor;

import game.controller.Main;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sun.util.locale.provider.LocaleResources;

public class Gra extends UnicastRemoteObject implements RemoteGame, Serializable{
	
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
				if (obj instanceof Gra) {
					String s = (String) obj;
					Method m = Gra.class.getDeclaredMethod(s);
					m.invoke(this);
				} else if (obj instanceof String[]) {
//					String[] s = (String[]) obj;
//					Method m = Gra.class.getDeclaredMethod(s[0]);
//					m.invoke(this, s[1]);
					showInfo("asdas asd asd aasd gdfsgsd fg sd");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void remoteSender() {
		try {
			oos.writeObject("Pozdrawiam");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startNewGame() {
		g1 = new Gracz(1, "Player1", Color.LAVENDER);
		g2 = new Gracz(2, "Player2", Color.BROWN);
		Pane pane = (Pane) Main.getMainStage().getScene().getRoot();
		Platform.runLater(() -> {
				int x = 10;
				int y = 540;
				for (Pionek p : g1.getPionki()) {
					p.setLayoutX(x);
					p.setLayoutY(y);
					pane.getChildren().add(p);
					x += 60;
				}
				x += 20;
				for (Pionek p : g2.getPionki()) {
					p.setLayoutX(x);
					p.setLayoutY(y);
					pane.getChildren().add(p);
					x += 60;
				}
		    });
	}
	
	public void showInfo(String s) {
		TextArea t = Main.getInfoTxt();
		//t.setText(s);
		//t.setVisible(true);
		//System.out.println("powinnno pokazac");
		Main.getInfoTxtSeq().play();
		
	}

	@Override
	public String getDiceResult() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getDecision() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveCounter() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean showScore() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateData() throws RemoteException {		
		System.out.println("dziala!!!!");
		return true;
	}
	
}
