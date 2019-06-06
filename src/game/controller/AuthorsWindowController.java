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

public class AuthorsWindowController {
    private Clip bowClip;
    private Clip clickClip;
    private int hoverTransX;
    private int hoverTransY;
    private Stage stage;

	@FXML private ImageView exitBut;
	@FXML private Label exitButLab;

	Stage autWindowStage = new Stage();

	public void setStage(Stage s) {
		stage = s;
	}

	@FXML public void closeAut() {
		clickBut();
		stage.close();
	}

	@FXML public void hoverBut() {
    	bowClip.stop();
    	bowClip.setMicrosecondPosition(0);
    	bowClip.start();
    }

    @FXML public void clickBut() {
    	clickClip.stop();
    	clickClip.setMicrosecondPosition(0);
    	clickClip.start();
    }

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