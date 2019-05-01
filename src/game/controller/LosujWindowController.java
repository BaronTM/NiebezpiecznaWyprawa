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
    private int b;
    private boolean wskazanie;

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
            int a = generator.nextInt(6) + 1;
    		if (a <= 4)
    			liczba.setText(Integer.toString(a));
    		else
    			liczba.setText("X");
    	return a;
    }

    public int getA() {
    	return a;
    }

    @FXML public void closeWindow() {
        losujWindowStage.close();
    }

    @FXML public int wybranoPierwszy() {
    	przekaz();
    	b = 1;
    	ustawWskazanie(b);
    	return b;
    }
    @FXML public int wybranoDrugi() {
    	przekaz();
    	b = 2;
    	ustawWskazanie(b);
    	return b;
    }
    @FXML public int wybranoTrzeci() {
    	przekaz();
    	b = 3;
    	ustawWskazanie(b);
    	return b;
    }
    @FXML public int wybranoCzwarty() {
    	przekaz();
    	b = 4;
    	ustawWskazanie(b);
    	return b;
    }

    public boolean getWskazanie() {
    	return wskazanie;
    }

    public boolean ustawWskazanie(int b) {
    	if (b == a)
    		wskazanie = true;
    	else
    		wskazanie = false;
    	return wskazanie;
    }

    public void przekaz() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/WybierzWindowView.fxml"));
        try {
        	losujWindowStage.close();
            AnchorPane pane = loader.load();
            Stage wybierzWindowStage = new Stage();
            wybierzWindowStage.setTitle("Wybor");
            wybierzWindowStage.initModality(Modality.WINDOW_MODAL);
            wybierzWindowStage.initOwner(primaryStage);//(losujWindowStage);
            wybierzWindowStage.setMinHeight(650);
            wybierzWindowStage.setMinWidth(500);
            Scene scene = new Scene(pane);
            wybierzWindowStage.setScene(scene);

            WybierzWindowController animationWindowController = loader.getController();
            animationWindowController.setAnimationWindowStage(wybierzWindowStage);
            animationWindowController.setChoice();
            wybierzWindowStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}