package game.controller;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import game.model.Gamer;
import game.model.Pawn;

public class ChoiceWindowController {
    private Stage choiceWindowStage;

    @FXML Rectangle dice;
    @FXML TextField textChoice;
    @FXML Button isTrue;
    @FXML Button isFalse;

    List<Pawn> pawnsA;
    List<Pawn> pawnsB;
    Gamer gamerA = new Gamer(1, "graczA", Color.RED);
    Gamer gamerB = new Gamer(1, "graczA", Color.GREEN);
    private String[] answer;
    DrawWindowController draw = new DrawWindowController();
    Animation animation;

    public void setAnimationWindowStage(Stage choiceWindowStage) {
        this.choiceWindowStage = choiceWindowStage;
    }

    @FXML public void setChoice(String foeClaim) {
    	RotateTransition transition = new RotateTransition();
        transition.setNode(dice);
        for (int i = 1; i <=30; i++) {
            transition.setDuration(Duration.seconds(i * 0.2));
            transition.setFromAngle((i - 1) * 1080 * (31 - i));
            transition.setToAngle(i * 1080 * (31 - i));
        }
        textChoice.setText(foeClaim);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        animation = transition;
        animation.play();
    }

    @FXML public void setTrue() {
    	answer = new String[] {"Answer", "true"};
    	closeWindow();
    }

    @FXML public void setFalse() {
    	answer = new String[] {"Answer", "false"};
    	closeWindow();
    }

    public String[] getAnswer() {
    	return answer;
    }

    public TextField getTextChoice() {
    	return textChoice;
    }

    public void closeWindow() {
    	choiceWindowStage.close();
    }
}