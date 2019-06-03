package game.controller;

import java.io.IOException;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LosujWindowController {
    private Stage losujWindowStage;
	private Stage primaryStage;
    private int a;
    private String a1;
    private int b;
    private static String b1;
    private static boolean wskazanie;

    @FXML Rectangle kostka;
    @FXML TextField liczba;
    @FXML Button button1;
    @FXML Button button2;
    @FXML Button button3;
    @FXML Button button4;

    Animation animation;
    Random generator = new Random();

	public void setAnimationWindowStage(Stage losujWindowStage) {
        this.losujWindowStage = losujWindowStage;
    }

    @FXML public int setAnimation() {
            RotateTransition transition = new RotateTransition();
            transition.setNode(kostka);
            for (int i = 1; i <=30; i++) {
                transition.setDuration(Duration.seconds(i * 0.2));
                transition.setFromAngle((i - 1) * 1080 * (31 - i));
            	transition.setToAngle(i * 1080 * (31 - i));
            }
            transition.setCycleCount(Animation.INDEFINITE);
            transition.setAutoReverse(false);
            animation = transition;
            animation.play();
            a = generator.nextInt(6) + 1;
    		if (a <= 4) {
    			liczba.setText(Integer.toString(a));
    			a1 = a + "";
    		} else {
    			liczba.setText("X");
    			a1 = "X";
    		}
    	return a;
    }

    /*public String getA() {
    	System.out.println("getA1: " + a1);
    	return a1;
    }

    public void setA(String a1) {
    	LosujWindowController.a1 = a1;
    }*/

    @FXML public void closeWindow() {
        losujWindowStage.close();
    }

    @FXML public String wybranoPierwszy() {
    	b = 1;
    	b1 = b + "";
    	closeWindow();
    	return b1;
    }
    @FXML public String wybranoDrugi() {
    	b = 2;
    	b1 = b + "";
    	closeWindow();
    	return b1;
    }
    @FXML public String wybranoTrzeci() {
    	b = 3;
    	b1 = b + "";
    	return b1;
    }
    @FXML public String wybranoCzwarty() {
    	b = 4;
    	b1 = b + "";
    	closeWindow();
    	return b1;
    }
    
    public String[] getResult() {
    	return new String[] {"RESULT", a1, b1};
    }

    public String getB() {
    	return b1;
    }

    public void setB(String b1) {
    	LosujWindowController.b1 = b1;
    }

    public boolean getWskazanie() {
    	return wskazanie;
    }
}