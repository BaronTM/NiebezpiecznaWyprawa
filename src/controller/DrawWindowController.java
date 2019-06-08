package controller;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Klasa obsluguje wyswietlanie okna z symulacja rzutu zmodyfikowana kostka.
 * Tutaj gracz przekazuje przeciwnikowi prawidlowy wynik lub oszukuje po losowaniu
 * @author Karol Kusmierz
 */
public class DrawWindowController {
    /**
     * kontroler do wyswietlenia okna losowania
     */
    private Stage drawWindowStage;
    /**
     * parametr do przechowania wartosci wylosowanej, liczba naturalna
     */
    private int drawInt;
    /**
     * parametr do przechowania zmodyfikowanej wartosci wylosowanej, tekstowo
     */
    private String drawStr;
    /**
     * parametr do przechowania wartosci przekazanej przeciwnikowi, liczba naturalna
     */
    private int choiceInt;
    /**
     * parametr do przechowania wartosci przekazanej przeciwnikowi, tekst
     */
    private static String choiceStr;

    /**
     * ksztalt bedacy podkladem wyswietlanej wylosowanej wartosci gracza
     */
    @FXML Rectangle dice;
    /**
     *  pole tekstowe do wyswietlania wartosci wylosowanej przez gracza
     */
    @FXML TextField numb;
    /**
     * przycisk do wskazania przeciwnikowi wartosci '1'
     */
    @FXML Button button1;
    /**
     * przycisk do wskazania przeciwnikowi wartosci '2'
     */
    @FXML Button button2;
    /**
     * przycisk do wskazania przeciwnikowi wartosci '3'
     */
    @FXML Button button3;
    /**
     * przycisk do wskazania przeciwnikowi wartosci '4'
     */
    @FXML Button button4;

    /**
     * klasa do obslugi animacji elementow
     */
    Animation animation;
    /**
     * klasa do generowania wartosci losowych z rozkladu jednostajnego na (0, 1)
     */
    Random generator = new Random();

    /**
	 * @param drawWindowStage parametr wskazujacy obslugiwany kontener
	 */
	public void setDrawWindowStage(Stage drawWindowStage) {
        this.drawWindowStage = drawWindowStage;
    }

    /**
	 * Metoda do obslugi animacji i przekazania inforacji o losowaniu po rzucie kostka
	 * @return zwraca wylosowana wartosc zmodyfikowana
	 */
    @FXML public String setAnimation() {
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
    	return drawStr;
    }

    /**
	 * Metoda do zamykania biezacego okna
	 */
    @FXML public void closeWindow() {
        drawWindowStage.close();
    }

    /**
	 * Metoda do przekazania przeciwnikowi wyniku '1' po rzucie kostka
	 * @return zwraca wskazana wartosc '1'
	 */
    @FXML public String choseFirst() {
    	choiceInt = 1;
    	choiceStr = choiceInt + "";
    	closeWindow();
    	return choiceStr;
    }

    /**
	 * Metoda do przekazania przeciwnikowi wyniku '2' po rzucie kostka
	 * @return zwraca wskazana wartosc '2'
	 */
    @FXML public String choseSecond() {
    	choiceInt = 2;
    	choiceStr = choiceInt + "";
    	closeWindow();
    	return choiceStr;
    }

    /**
	 * Metoda do przekazania przeciwnikowi wyniku '3' po rzucie kostka
	 * @return zwraca wskazana wartosc '3'
	 */
    @FXML public String choseThird() {
    	choiceInt = 3;
    	choiceStr = choiceInt + "";
    	closeWindow();
    	return choiceStr;
    }

    /**
	 * Metoda do przekazania przeciwnikowi wyniku '4' po rzucie kostka
	 * @return zwraca wskazana wartosc '4'
	 */
    @FXML public String choseFourth() {
    	choiceInt = 4;
    	choiceStr = choiceInt + "";
    	closeWindow();
    	return choiceStr;
    }

    /**
	 * @return zwraca tablice z wartoscia wylosowana oraz przekazana przeciwnikowi
	 */
    public String[] getResult() {
    	return new String[] {"RESULT", drawStr, choiceStr};
    }

    /**
	 * @return zwraca wartosc przekazana przeciwnikowi
	 */
    public String getChoiceStr() {
    	return choiceStr;
    }

    /**
	 * @param choiceStr parametr typu String przekazujacy wynik wskazany przeciwnikowi
	 */
    public void setChoiceStr(String choiceStr) {
    	DrawWindowController.choiceStr = choiceStr;
    }
}