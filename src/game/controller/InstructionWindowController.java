package game.controller;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Klasa obsluguje wyswietlanie okna z instrukcja gry
 * @author Karol Kusmierz
 */
public class InstructionWindowController {

	/**
     * plik do odtworzenia dzwieku po wskazaniu przycisku kursorem myszy
     */
    private Clip bowClip;
    /**
     * plik do odtworzenia dzwieku po kliknieciu w przycisk
     */
    private Clip clickClip;
    /**
     * parametr do przesuniecia przycisku w poziomie po wskazaniu przycisku kursorem myszy
     */
    private int hoverTransX;
    /**
     * parametr do przesuniecia przycisku w pionie po wskazaniu przycisku kursorem myszy
     */
    private int hoverTransY;
    /**
     * kontener do wyswietlenia okna z informacja o autorach
     */
    private Stage stage;

    /**
	 * obraz reprezentujacy przycisk zamkniecia okna
	 */
	@FXML private ImageView exitBut;
	/**
	 * etykieta wskazuja przycisk do zamkniecia okna
	 */
	@FXML private Label exitButLab;

		/**
	 * Metoda do ustawiania glownego kontenera dla okna
	 * @param s parametr typu Stage wskazujacy obslugiwany kontener
	 */
	public void setStage(Stage s) {
		stage = s;
	}

	/**
	 * Metoda do zamykania okna z instrukcja, z obsluga odtworzenia dzwieku
	 */
	@FXML public void closeInstr() {
		clickBut();
		stage.close();
	}

	/**
	 * Metoda do odtwarzania dzwieku po wskazaniu kursorem myszy na przycisk zamykania okna
	 */
	@FXML public void hoverBut() {
    	bowClip.stop();
    	bowClip.setMicrosecondPosition(0);
    	bowClip.start();
    }

	/**
	 * Metoda do odtwarzania dzwieku po kliknieciu na przycisk zamykania okna
	 */
    @FXML public void clickBut() {
    	clickClip.stop();
    	clickClip.setMicrosecondPosition(0);
    	clickClip.start();
    }

    /**
	 * Metoda do inicjalizacji, definiujaca parametry do obslugi przycisku zamykania okna
	 */
	public void initialize() {
    	hoverTransX = -50;
    	hoverTransY = -20;
    	exitBut.setCursor(Cursor.HAND);
		exitBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED, event -> {
			exitBut.setTranslateX(hoverTransX);
			exitBut.setTranslateY(hoverTransY);
			exitButLab.setTranslateX(hoverTransX);
			exitButLab.setTranslateY(hoverTransY);
		});
		exitBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED, event -> {
			exitBut.setTranslateX(0);
			exitBut.setTranslateY(0);
			exitButLab.setTranslateX(0);
			exitButLab.setTranslateY(0);
		});
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("bin/game/view/sounds/bow.wav").getAbsoluteFile());
	    	bowClip = AudioSystem.getClip();
	    	bowClip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {}
		AudioInputStream audioInputStream2;
		try {
			audioInputStream2 = AudioSystem.getAudioInputStream(new File("bin/game/view/sounds/click.wav").getAbsoluteFile());
	    	clickClip = AudioSystem.getClip();
	    	clickClip.open(audioInputStream2);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {}
	}
}