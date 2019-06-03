package game.controller;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.function.UnaryOperator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.model.GameServer;
import game.model.Gra;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StartGameController {
    private Main main;
    private Clip bowClip;
    private Clip clickClip;
    private int hoverTransX;
    private int hoverTransY;
    private Stage primaryStage; 
    private String regex;
    private StringProperty serverIpAddress;
    private int xx;
    private int yy;
    private UnaryOperator<Change> ipAddressFilter;
    @FXML
    private ImageView startBut;
    @FXML
    private ImageView joinBut;
    @FXML
    private ImageView exitBut;
    @FXML
    private ImageView setBut;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private AnchorPane startAnchor;
    @FXML
    private AnchorPane waitingAnchor;
    @FXML
    private AnchorPane joinAnchor;
    @FXML
    private AnchorPane setAnchor;
    @FXML
    private ImageView cancelBut;
    @FXML
    private ImageView cancelJoinBut;
    @FXML
    private ImageView backSetBut;
    @FXML
    private Label startButLab;
    @FXML
    private Label exitButLab;
    @FXML
    private Label joinButLab;
    @FXML
    private Label setButLab;
    @FXML
    private Label cancelButLab;
    @FXML
    private Label cancelJoinButLab;
    @FXML
    private Label backSetButLab;
    @FXML
    private Label ipLab;
    @FXML
    private ImageView ipSign;
    @FXML
    private ImageView joinError;
    @FXML
    private TextField ipTextField;
    

    public void setMain(Main main) {
        this.main = main;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML public void exitGame() {
    	clickBut();
    	main.exitGame();
    }
    
    @FXML public void setGame() {
    	clickBut();
    	startAnchor.setVisible(false);
    	setAnchor.setVisible(true);
    }

    @FXML public void createGame() {
        clickBut();
        startAnchor.setVisible(false);
        waitingAnchor.setVisible(true);
        Main.getExecutor().submit(new GameServer());
        try {
			Main.setGra(new Gra(1, main));
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
        try {
			Main.setGra(new Gra(2, main));
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			Main.getGra().setSock(new Socket(serverIpAddress.getValue(), 4242));
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
    
    @FXML public void backSet() {
    	clickBut();
    	startAnchor.setVisible(true);
        setAnchor.setVisible(false);
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
        setAnchor.setVisible(false);
    	startBut.setCursor(Cursor.HAND);
    	joinBut.setCursor(Cursor.HAND);
    	exitBut.setCursor(Cursor.HAND);
    	cancelBut.setCursor(Cursor.HAND);
    	setBut.setCursor(Cursor.HAND);
    	backSetBut.setCursor(Cursor.HAND);
    	initAudio();	
    	initHover();
    	regex = makePartialIPRegex();
    	ipAddressFilter = c -> {
    		String text = c.getControlNewText();
            if  (text.matches(regex)) {
                return c ;
            } else {
                return null ;
            }
    	};
    	ipTextField.setTextFormatter(new TextFormatter<>(ipAddressFilter));
    	serverIpAddress = new SimpleStringProperty();
    	serverIpAddress.setValue("127.0.0.1");
    	ipTextField.textProperty().bindBidirectional(serverIpAddress);
    	mainAnchor.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
    		boardImageMousePressed(e);
    	});
    	mainAnchor.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
    		boardImageMouseDragged(e);
    	});
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
		
		setBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED, event -> {
			setBut.setTranslateX(-hoverTransX * 0.7);
			setBut.setTranslateY(hoverTransY * 0.7);
			setButLab.setTranslateX(-hoverTransX * 0.7);
			setButLab.setTranslateY(hoverTransY * 0.7);
		});
		setBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED, event -> {
			setBut.setTranslateX(0);
			setBut.setTranslateY(0);
			setButLab.setTranslateX(0);
			setButLab.setTranslateY(0);
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
		
		backSetBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED, event -> {
			backSetBut.setTranslateX(hoverTransX);
			backSetBut.setTranslateY(hoverTransY);
			backSetButLab.setTranslateX(hoverTransX);
			backSetButLab.setTranslateY(hoverTransY);
		});
		backSetBut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED, event -> {
			backSetBut.setTranslateX(0);
			backSetBut.setTranslateY(0);
			backSetButLab.setTranslateX(0);
			backSetButLab.setTranslateY(0);
		});
    }
    
//    private String makePartialIPRegex() {
//        String partialBlock = "(([01]?[0-9]{0,2})|(2[0-4][0-9])|(25[0-5]))" ;
//        String subsequentPartialBlock = "(\\."+partialBlock+")" ;
//        String ipAddress = partialBlock+"?"+subsequentPartialBlock+"{0,3}";
//        return "^"+ipAddress ;
//    }
    
    private String makePartialIPRegex() {
    	String pattern = 
    	        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return pattern;
    }
    
    private void boardImageMousePressed(MouseEvent evt) {
        xx=(int) evt.getX();
        yy=(int) evt.getY();
    }

    private void boardImageMouseDragged(MouseEvent evt) {
        int x=(int) evt.getScreenX();
        int y=(int) evt.getScreenY();
        primaryStage.setX(x-xx);
        primaryStage.setY(y-yy);
    }

    
    
}