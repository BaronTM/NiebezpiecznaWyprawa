package game.controller;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import game.model.Gracz;
import game.model.Pionek;
import game.model.Plansza;

public class WybierzWindowController {
    private Stage wybierzWindowStage;

    @FXML Rectangle kostka;
    @FXML TextField wybor;
    @FXML Button prawda;
    @FXML Button falsz;

    private int p1 = 1;
    private int p2 = 1;
    List<Pionek> pionkiA;
    List<Pionek> pionkiB;
    Gracz graczA = new Gracz(1, "graczA", Color.RED);
    Gracz graczB = new Gracz(1, "graczA", Color.GREEN);
    private Plansza plansza;
    private Pionek pionek1;
    private Pionek pionek2;
    private String[] answer;

    LosujWindowController los = new LosujWindowController();

    Animation animation;

    public void setAnimationWindowStage(Stage wybierzWindowStage) {
        this.wybierzWindowStage = wybierzWindowStage;
    }

    @FXML public void setChoice(String foeClaim) {
    	//LosujWindowController los = new LosujWindowController();
            RotateTransition transition = new RotateTransition();
            transition.setNode(kostka);
            for (int i = 1; i <=30; i++) {
                transition.setDuration(Duration.seconds(i * 0.2));
                transition.setFromAngle((i - 1) * 1080 * (31 - i));
            	transition.setToAngle(i * 1080 * (31 - i));
            }
            wybor.setText(foeClaim);
            transition.setCycleCount(1);
            transition.setAutoReverse(false);
            animation = transition;
            animation.play();
    }
    
    @FXML
    public void setTrue() {
    	answer = new String[] {"Answer", "true"};
    	closeWindow();
    }
    
    @FXML
    public void setFalse() {
    	answer = new String[] {"Answer", "false"};  
    	closeWindow();
    }
    
    public String[] getAnswer() {
    	return answer;
    }
    
    public TextField getWybor() {
    	return wybor;
    }
    
    public void closeWindow() {
    	wybierzWindowStage.close();
    }
}