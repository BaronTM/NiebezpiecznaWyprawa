package game.controller;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.model.GameServer;
import game.model.Gra;
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
    private AnchorPane joinAnchor;
    @FXML
    private ImageView cancelBut;
    @FXML
    private ImageView cancelJoinBut;
    @FXML
    private Label startButLab;
    @FXML
    private Label exitButLab;
    @FXML
    private Label joinButLab;
    @FXML
    private Label cancelButLab;
    @FXML
    private Label cancelJoinButLab;
    @FXML
    private ImageView joinError;
    
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
        Main.getExecutor().submit(new GameServer());
        Main.setGra(new Gra());
        try {
			Main.getGra().setSock(new Socket("127.0.0.1", 4242));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML public void joinGame() {
        clickBut();
        startAnchor.setVisible(false);
        joinAnchor.setVisible(true);
        System.out.println("Dolacz");
        Main.setGra(new Gra());
        try {
			Main.getGra().setSock(new Socket("127.0.0.1", 4242));
			System.out.println("Polaczono");
			Main.runGame();
		} catch (IOException e) {
			joinError.setVisible(true);
			hoverBut();
		}
    }

    @FXML public void cancelWaiting() {
    	clickBut();
    	startAnchor.setVisible(true);
        waitingAnchor.setVisible(false);
        Main.cancelExecutor();
    }
    
    @FXML public void cancelJoining() {
    	clickBut();
    	if (Main.getGra().getSock() != null) {
    		try {
				Main.getGra().getSock().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	startAnchor.setVisible(true);
        joinAnchor.setVisible(false);
		joinError.setVisible(true);
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
        joinAnchor.setVisible(false);
        joinError.setVisible(false);
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
		
		cancelJoinBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED, event -> {
			cancelJoinBut.setTranslateX(hoverTransX);
			cancelJoinBut.setTranslateY(hoverTransY);
			cancelJoinButLab.setTranslateX(hoverTransX);
			cancelJoinButLab.setTranslateY(hoverTransY);
		});
		cancelJoinBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED, event -> {
			cancelJoinBut.setTranslateX(0);
			cancelJoinBut.setTranslateY(0);
			cancelJoinButLab.setTranslateX(0);
			cancelJoinButLab.setTranslateY(0);
		});
    }

    
    
}