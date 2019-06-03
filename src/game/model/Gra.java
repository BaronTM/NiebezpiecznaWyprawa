package game.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Future;

import game.controller.Main;
import game.controller.MainWindowController;
import game.controller.WybierzWindowController;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Gra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5712053620818160194L;
	private transient Socket sock;
	private transient ObjectOutputStream oos;
	private transient ObjectInputStream ois;
	private Gracz g1;
	private Gracz g2;
	private Main main;
	private int gamerId;

	public Gra(int nr, Main m) throws RemoteException {
		super();
		gamerId = nr;
		main = m;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remoteReader() {
		Object obj = null;
		try {
			obj = ois.readObject();
			String s = (String) obj;
			Method m = Gra.class.getDeclaredMethod(s);
			m.invoke(this);
			while ((obj = ois.readObject()) != null) {
				String[] commands = (String[]) obj;
				for (String saa : commands) {
					System.out.println(saa);
				}
				if (commands[0].equalsIgnoreCase("LOSUJ")) {
					int nr = Integer.parseInt(commands[1]);
					if (nr == gamerId) {
						showInfo("Rzuc kostka");
						main.getMainWindowController().getLosBut().setVisible(true);
					} else {
						showInfo("Przeciwnik rzuca");						
					}
				} else if (commands[0].equalsIgnoreCase("FOE")) {
					Platform.runLater(() -> {
					main.getMainWindowController().przekaz(commands[1]);
					});
				}
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
	
	public void sendObj(Object o) {
		try {
			oos.writeObject(o);
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
			int x = 45;
			int y = 765;
			for (Pionek p : g1.getPionki()) {
				p.przesunPoMoscie(x, y);
				pane.getChildren().add(p);
				x += 80;
			}
			x = 415;
			for (Pionek p : g2.getPionki()) {
				p.przesunPoMoscie(x, y);
				pane.getChildren().add(p);
				x += 80;
			}
		});
		showInfo("ZACZYNAMY");
		sendObj("connected");
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

//	public void przesunDoWody(Pionek pionek, Gracz gracz) {
//    	Path animationPath = new Path();
//    	MoveTo moveTo = new MoveTo();
//    	QuadCurveTo curve = new QuadCurveTo();
//    	moveTo = new MoveTo(pionek.getLayoutX(), pionek.getLayoutY());
//    	if (gracz == g1)
//    		// wsporzedne do zmiany
//    		curve = new QuadCurveTo((pionek.getLayoutX() + 60) / 2, (pionek.getLayoutY() + 180) / 2, 60, 180);
//    	else
//    		curve = new QuadCurveTo((pionek.getLayoutX() + 180) / 2, (pionek.getLayoutY() + 180) / 2, 180, 180);
//    	animationPath.getElements().addAll(moveTo, curve);
//
//        PathTransition transition = new PathTransition();
//        transition.setNode(pionek);
//        transition.setDuration(Duration.seconds(2));
//        transition.setPath(animationPath);
//        transition.setCycleCount(1);
//        transition.setAutoReverse(false);
//        transition.setOrientation(OrientationType.NONE);
//        transition.setInterpolator(Interpolator.LINEAR);
//        animation = transition;
//
//        pionek.setVisible(false);
//    }


	
}
