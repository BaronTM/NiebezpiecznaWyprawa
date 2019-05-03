package game.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sun.util.locale.provider.LocaleResources;

public class Gra extends UnicastRemoteObject implements RemoteGame, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5712053620818160194L;
	private transient Socket sock;
	private transient ObjectOutputStream oos;
	private transient ObjectInputStream ois;
	
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
			//Main.getExecutor().submit(new Thread(() -> remoteReader()));
//			Registry registry = LocateRegistry.createRegistry(5850);
//			registry.rebind("Gra", this);
			Environment env = new Environment();
			SessionAcceptor sa = env.newSessionAcceptor(5058);
			sa.acceptAll(this);
			System.out.println(sa.getLocalAddress().toString());
			
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
				System.out.println((String) obj);
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
		AnchorPane pane = (AnchorPane) Main.getMainStage().getScene().getRoot();
		Pionek p = new Pionek(Color.AQUA, 1);
		p.setLayoutX(100);
		p.setLayoutY(100);
		pane.getChildren().add(p);
		System.out.println("dziala!!!!");
		return true;
	}
	
}
