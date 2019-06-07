package game.controller;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Klasa obsluguje wyswietlanie okna ze wskazaniem prawdy/falszu, po owczesnym wskazaniu wylosowania przez przeciwnika
 * @author Karol Kusmierz
 */
public class ChoiceWindowController {

	/**
	 * kontener do wyswietlenia okna
	 */
	private Stage choiceWindowStage;

    /**
     * ksztalt bedacy podkladem wyswietlanej wartosci wskazanej przez przeciwnika
     */
    @FXML Rectangle dice;
    /**
     * pole tekstowe do wyswietlania wartosci wskazanej przez przeciwnika
     */
    @FXML TextField textChoice;
    /**
     * przycisk do wskazania wartosci przawdziwej
     */
    @FXML Button isTrue;
    /**
     * przycisk do wskazania wartosci falszywej
     */
    @FXML Button isFalse;

    /**
     * tabela do przekazania wartosci wskazanej przez gracza
     */
    private String[] answer;
    /**
     * kontroler do obslugi okna losowania wartosci
     */
    DrawWindowController draw = new DrawWindowController();
    /**
     * klasa do obslugi animacji elementow
     */
    Animation animation;

    /**
	 * Metoda do ustawiania glownego kontenera dla okna
	 * @param choiceWindowStage parametr typu Stage wskazujacy obslugiwany kontener
	 */
    public void setChoiceWindowStage(Stage choiceWindowStage) {
        this.choiceWindowStage = choiceWindowStage;
    }

    /**
	 * Metoda do obslugi animacji i przekazania inforacji o losowaniu przeciwnika
	 * @param foeClaim parametr typu String przekazujacy informacje - prawdziwa lub nie - od przeciwnika
	 */
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

    /**
	 * Metoda do oznaczenia informacji od gracza, ze zgadza sie z wynikiem przeciwnika i zamkniecia biezacego okna
	 */
    @FXML public void setTrue() {
    	answer = new String[] {"Answer", "true"};
    	closeWindow();
    }

    /**
	 * Metoda do oznaczenia informacji od gracza, ze nie zgadza sie z wynikiem przeciwnika i zamkniecia biezacego okna
	 */
    @FXML public void setFalse() {
    	answer = new String[] {"Answer", "false"};
    	closeWindow();
    }

    /**
	 * Metoda do przekazania wyboru gracza
	 * @return tablica z wartoscia wskazania gracza
	 */
    public String[] getAnswer() {
    	return answer;
    }

    /**
	 * Metoda do zwrocenia wyboru gracza
	 * @return pole do wyswietlenia wartosci wskazanej przez przeciwnika
	 */
    public TextField getTextChoice() {
    	return textChoice;
    }

    /**
	 * Metoda do zamykania biezacego okna
	 */
    public void closeWindow() {
    	choiceWindowStage.close();
    }
}