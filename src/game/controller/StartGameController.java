package game.controller;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StartGameController {
    private Main main;
    private Clip bowClip;
    private Clip clickClip;
    private int hoverTransX;
    private int hoverTransY;
    private Stage primaryStage; 
    @FXML
    private ImageView startBut;
    @FXML
    private ImageView joinBut;
    @FXML
    private ImageView exitBut;
    @FXML
    private AnchorPane startAnchor;
    @FXML
    private AnchorPane waitingAnchor;
    @FXML
    private ImageView cancelBut;
    @FXML
    private Label startButLab;
    @FXML
    private Label exitButLab;
    @FXML
    private Label joinButLab;
    @FXML
    private Label cancelButLab;    
    
    
    public void setMain(Main main) {
        this.main = main;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML public void exitGame() {
    	clickBut();
        primaryStage.close();
    }

    @FXML public void createGame() {
        clickBut();
        startAnchor.setVisible(false);
        waitingAnchor.setVisible(true);
    }
    
    @FXML public void joinGame() {
        System.out.println("Dolacz");
        clickBut();
    }

    @FXML public void cancelWaiting() {
    	clickBut();
    	startAnchor.setVisible(true);
        waitingAnchor.setVisible(false);
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
    	hoverTransX = 50;
    	hoverTransY = -20;
    	startAnchor.setVisible(true);
        waitingAnchor.setVisible(false);
    	startBut.setCursor(Cursor.HAND);
    	joinBut.setCursor(Cursor.HAND);
    	exitBut.setCursor(Cursor.HAND);
    	cancelBut.setCursor(Cursor.HAND);
    	initAudio();	
    	initHover();
    }
    
    private void initAudio() {
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
    
    private void initHover() {
    	cancelBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED, event -> {
			cancelBut.setTranslateX(hoverTransX);
			cancelBut.setTranslateY(hoverTransY);
			cancelButLab.setTranslateX(hoverTransX);
			cancelButLab.setTranslateY(hoverTransY);
		});
		cancelBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED, event -> {
			cancelBut.setTranslateX(0);
			cancelBut.setTranslateY(0);
			cancelButLab.setTranslateX(0);
			cancelButLab.setTranslateY(0);
		});
		
		joinBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED, event -> {
			joinBut.setTranslateX(hoverTransX);
			joinBut.setTranslateY(hoverTransY);
			joinButLab.setTranslateX(hoverTransX);
			joinButLab.setTranslateY(hoverTransY);
		});
		joinBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED, event -> {
			joinBut.setTranslateX(0);
			joinBut.setTranslateY(0);
			joinButLab.setTranslateX(0);
			joinButLab.setTranslateY(0);
		});
		
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
		
		startBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED, event -> {
			startBut.setTranslateX(hoverTransX);
			startBut.setTranslateY(hoverTransY);
			startButLab.setTranslateX(hoverTransX);
			startButLab.setTranslateY(hoverTransY);
		});
		startBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED, event -> {
			startBut.setTranslateX(0);
			startBut.setTranslateY(0);
			startButLab.setTranslateX(0);
			startButLab.setTranslateY(0);
		});
    }

    
    
}