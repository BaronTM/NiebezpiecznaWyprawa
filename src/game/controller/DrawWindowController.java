package game.controller;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DrawWindowController {
    private Stage drawWindowStage;
    private int drawInt;
    private String drawStr;
    private int choiceInt;
    private static String choiceStr;
    private static boolean showing;

    @FXML Rectangle dice;
    @FXML TextField numb;
    @FXML Button button1;
    @FXML Button button2;
    @FXML Button button3;
    @FXML Button button4;

    Animation animation;
    Random generator = new Random();

	public void setAnimationWindowStage(Stage drawWindowStage) {
        this.drawWindowStage = drawWindowStage;
    }

    @FXML public int setAnimation() {
            RotateTransition transition = new RotateTransition();
            transition.setNode(dice);
            for (int i = 1; i <=30; i++) {
                transition.setDuration(Duration.seconds(i * 0.2));
                transition.setFromAngle((i - 1) * 1080 * (31 - i));
            	transition.setToAngle(i * 1080 * (31 - i));
            }
            transition.setCycleCount(Animation.INDEFINITE);
            transition.setAutoReverse(false);
            animation = transition;
            animation.play();
            drawInt = generator.nextInt(6) + 1;
    		if (drawInt <= 4) {
    			numb.setText(Integer.toString(drawInt));
    			drawStr = drawInt + "";
    		} else {
    			numb.setText("X");
    			drawStr = "X";
    		}
    	return drawInt;
    }

    @FXML public void closeWindow() {
        drawWindowStage.close();
    }

    @FXML public String choseFirst() {
    	choiceInt = 1;
    	choiceStr = choiceInt + "";
    	closeWindow();
    	return choiceStr;
    }
    @FXML public String choseSecond() {
    	choiceInt = 2;
    	choiceStr = choiceInt + "";
    	closeWindow();
    	return choiceStr;
    }
    @FXML public String choseThird() {
    	choiceInt = 3;
    	choiceStr = choiceInt + "";
    	closeWindow();
    	return choiceStr;
    }
    @FXML public String choseFourth() {
    	choiceInt = 4;
    	choiceStr = choiceInt + "";
    	closeWindow();
    	return choiceStr;
    }

    public String[] getResult() {
    	return new String[] {"RESULT", drawStr, choiceStr};
    }

    public String getChoiceStr() {
    	return choiceStr;
    }

    public void setChoiceStr(String choiceStr) {
    	DrawWindowController.choiceStr = choiceStr;
    }

    public boolean getShowing() {
    	return showing;
    }
}