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

    LosujWindowController los = new LosujWindowController();

    Animation animation;

    public void setAnimationWindowStage(Stage wybierzWindowStage) {
        this.wybierzWindowStage = wybierzWindowStage;
    }

    @FXML public void setChoice() {
    	//LosujWindowController los = new LosujWindowController();
            RotateTransition transition = new RotateTransition();
            transition.setNode(kostka);
            for (int i = 1; i <=30; i++) {
                transition.setDuration(Duration.seconds(i * 0.2));
                transition.setFromAngle((i - 1) * 1080 * (31 - i));
            	transition.setToAngle(i * 1080 * (31 - i));
            }
            wybor.setText(los.getB());
            transition.setCycleCount(1);
            transition.setAutoReverse(false);
            animation = transition;
            animation.play();
    }

    @FXML public void przesunPrawda() {
    	wybierzWindowStage.close();
    	//LosujWindowController los = new LosujWindowController();
    	if (true == los.getWskazanie()) {
    		przesunPoMoscie();
    	}
    	else {
    		przesunDoWody();
    	}
    }

    @FXML public void przesunFalsz() {
    	wybierzWindowStage.close();
    	LosujWindowController los = new LosujWindowController();
    	if (false == los.getWskazanie())
    		przesunPoMoscie();
    	else
    		przesunDoWody();
    }

    public void przesunPoMoscie() {
    	pionkiA.get(p1-1);
    	pionkiB.get(p2-1);
    	//if (graczA)
    }
    public void przesunDoWody() {}

    public void przesuniecie(int ile, Pionek pionek) {
    	Path animationPath = new Path();
    	int[][] lewe = Plansza.getLeweWspDesek();
    	int[][] prawe = Plansza.getPraweWspDesek();
    	int[][] kamienie = Plansza.getKamienieWsp();
    	ile = Integer.parseInt(los.getB());
        MoveTo moveTo = new MoveTo(lewe[p1 - 1][0],lewe[p1 - 1][1]);
        QuadCurveTo curve = new QuadCurveTo((lewe[p1 - 1][0] + lewe[p1 - 1 + ile][0]) / 2, (lewe[p1 - 1][1] + lewe[p1 - 1 + ile][1]) / 2, lewe[p1 - 1 + ile][0], lewe[p1 - 1 + ile][1]);
        animationPath.getElements().addAll(moveTo, curve);

        PathTransition transition = new PathTransition();
        transition.setNode(pionek);
        transition.setDuration(Duration.seconds(2));
        transition.setPath(animationPath);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.setOrientation(OrientationType.NONE);
        transition.setInterpolator(Interpolator.LINEAR);
        animation = transition;
    }

}
