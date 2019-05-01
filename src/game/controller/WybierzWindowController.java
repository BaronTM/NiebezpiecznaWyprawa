package game.controller;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WybierzWindowController {
    private Stage wybierzWindowStage;

    @FXML Rectangle kostka;
    @FXML TextField wybor;
    @FXML Button prawda;
    @FXML Button falsz;


    Animation animation;

    public void setAnimationWindowStage(Stage wybierzWindowStage) {
        this.wybierzWindowStage = wybierzWindowStage;
    }

    @FXML public void setChoice() {
    	LosujWindowController los = new LosujWindowController();
            RotateTransition transition = new RotateTransition();
            transition.setNode(kostka);
            for (int i = 1; i <=30; i++) {
                transition.setDuration(Duration.seconds(i * 0.2));
                transition.setFromAngle((i - 1) * 1080 * (31 - i));
            	transition.setToAngle(i * 1080 * (31 - i));
            }
            wybor.setText(Integer.toString(los.getA()));
            transition.setCycleCount(1);
            transition.setAutoReverse(false);
            animation = transition;
            animation.play();
    }

    @FXML public void przesunPrawda() {
    	wybierzWindowStage.close();
    	LosujWindowController los = new LosujWindowController();
    	if (true == los.getWskazanie())
    		przesunPoMoscie();
    	else
    		przesunDoWody();
    }

    @FXML public void przesunFalsz() {
    	wybierzWindowStage.close();
    	LosujWindowController los = new LosujWindowController();
    	if (false == los.getWskazanie())
    		przesunPoMoscie();
    	else
    		przesunDoWody();
    }

    public void przesunPoMoscie() {}
    public void przesunDoWody() {}

}