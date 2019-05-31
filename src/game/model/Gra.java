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
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

public class Gra implements Serializable{
	
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remoteReader() {
		Object obj = null;
		try {
			while ((obj = ois.readObject()) != null) {
				String s = (String) obj;
				Method m = Gra.class.getDeclaredMethod(s);
				m.invoke(this);
				while (true) {
				obj = ois.readObject();
				String[] str = (String[]) obj;
				Pionek g = str[1].equals("g1") ? g1.getAktualnyPionek() : g2.getAktualnyPionek();
				double xx = Double.parseDouble(str[2]);
				double yy = Double.parseDouble(str[3]);
				for (String ssss : str) System.out.println(ssss);
				Method m2 = Gra.class.getDeclaredMethod(str[0], Pionek.class, double.class, double.class);
				m2.invoke(this, g, xx, yy);
				}
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
		try {
			oos.writeObject("connected");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
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

    public void przesunPoMoscie(Pionek pionek, double finalX, double finalY) {
    	Path animationPath = new Path();
    	MoveTo moveTo = new MoveTo();
    	QuadCurveTo curve = new QuadCurveTo();
    	moveTo = new MoveTo(pionek.getStageX(), pionek.getStageY());
    	
    	curve = new QuadCurveTo((pionek.getStageX() + finalX) / 2, (pionek.getStageY() + finalY) / 2, finalX, finalY);
        animationPath.getElements().addAll(moveTo, curve);

        PathTransition transition = new PathTransition();
        transition.setNode(pionek);
        transition.setDuration(Duration.seconds(2));
        transition.setPath(animationPath);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.setOrientation(OrientationType.NONE);
        transition.setInterpolator(Interpolator.LINEAR);
        Animation animation = transition;
        animation.play();
    }
	
}
