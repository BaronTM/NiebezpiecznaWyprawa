package controller;

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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Game;
import model.GameServer;

/**
 * Klasa do obslugi uruchomienia gry
 * Tutaj jeden z graczy rozpoczyna gre, tym samym uruchamiajac serwer, drugi dolacza do gry
 * Mo�na wyjsc z gry, gdy przyadkowo uruchomilismy okno
 * Dodatkowo ustawiamy IP przeciwnikow do polaczenia sie miedzy soba
 * @author Ernest Paprocki
 *
 */
public class StartGameController {
    /**
     * Klasa glowna
     */
    private Main main;
    /**
     * odtwarza dzwiek po wskazaniu przycisku kursorem myszy
     */
    private Clip bowClip;
    /**
     * odtwarza dzwiek po kliknieciu na przycisk
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
     * kontroler do wyswietlenia okna
     */
    private Stage primaryStage;
    /**
     * ciag walidujacy IP
     */
    private String regex;
    /**
     * parametr ustawiajacy IP
     */
    private StringProperty serverIpAddress;
    /**
     * parametr modyfikujacy pierwsza wspolrzedna polozenia kursora myszy
     */
    private int xx;
    /**
     * parametr modyfikujacy druga wspolrzedna polozenia kursora myszy
     */
    private int yy;
    /**
     * operator do ustawieniu IP we wlasciwym formacie
     */
    private UnaryOperator<Change> ipAddressFilter;

    /**
     * obraz definiujacy przycisk startu gry
     */
    @FXML private ImageView startBut;
    /**
     * obraz definiujacy przycisk dolaczenia do gry
     */
    @FXML private ImageView joinBut;
    /**
     * obraz definiujacy przycisk wyjscia z gry
     */
    @FXML private ImageView exitBut;
    /**
     * obraz definiujacy przycisk ustawien gry
     */
    @FXML private ImageView setBut;
    /**
     * panel glowny gry
     */
    @FXML private AnchorPane mainAnchor;
    /**
     * panel rozpoczecia gry
     */
    @FXML private AnchorPane startAnchor;
    /**
     * panel rozpoczecia gry i oczekiwania na drugiego gracza
     */
    @FXML private AnchorPane waitingAnchor;
    /**
     * panel dolaczenia do gry przed rozpoczeciem
     */
    @FXML private AnchorPane joinAnchor;
    /**
     * panel ustawienia IP
     */
    @FXML private AnchorPane setAnchor;
    /**
     * obraz definiujacy przycisk anulowania przy oczekiwaniu na drugiego gracza
     */
    @FXML private ImageView cancelBut;
    /**
     * obraz definiujacy przycisk anulowania przy dolaczeniu przed rozpoczeciem gry
     */
    @FXML private ImageView cancelJoinBut;
    /**
     * obraz definiujacy przycisk powrotu z ustawien do ekranu glownego
     */
    @FXML private ImageView backSetBut;
    /**
     * etykieta wskazujaca przypisk do rozpoczecia gry
     */
    @FXML private Label startButLab;
    /**
     * etykieta wskazujaca przypisk do wyjscia
     */
    @FXML private Label exitButLab;
    /**
     * etykieta wskazujaca przypisk do dolaczenia do gry
     */
    @FXML private Label joinButLab;
    /**
     * etykieta wskazujaca przypisk do przejscia do ustawien
     */
    @FXML private Label setButLab;
    /**
     * etykieta wskazujaca przypisk do anulowania przy oczekiwaniu na drugiego gracza
     */
    @FXML private Label cancelButLab;
    /**
     * etykieta wskazujaca przypisk do anulowaniu dolaczenia przed uruchomieniem gry
     */
    @FXML private Label cancelJoinButLab;
    /**
     * etykieta wskazujaca przypisk do powrotu z okna ustawien
     */
    @FXML private Label backSetButLab;
    /**
     * etykieta z IP
     */
    @FXML private Label ipLab;
    /**
     * tlo etykiety z IP
     */
    @FXML private ImageView ipSign;
    /**
     * obraz okreslajacy blad dolaczenia przy nieuruchomieniu gry
     */
    @FXML private ImageView joinError;
    /**
     * pole tekstowe wskazujace na adres IP
     */
    @FXML private TextField ipTextField;

    /**
     * @param main parametr wskazujacy klase glowna typu Main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * @param primaryStage kontroler okna typu Stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Metoda do zamkniecia okna
     */
    @FXML public void exitGame() {
    	clickBut();
    	main.exitGame();
    }

    /**
     * Metoda do zarzadzania panelami dla wyswietlenia ustawien gry (IP)
     */
    @FXML public void setGame() {
    	clickBut();
    	startAnchor.setVisible(false);
    	setAnchor.setVisible(true);
    }

    /**
     * Metoda do uruchomienia gry i wyswietlenia okna oczekiwania na drugiego gracza
     */
    @FXML public void createGame() {
        clickBut();
        startAnchor.setVisible(false);
        waitingAnchor.setVisible(true);
        Main.getExecutor().submit(new GameServer(main));
        try {
			Main.setGame(new Game(1, main));
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
    }

    /**
     * Metoda do dolaczenia do gry
     * Obsluguje wyswietlenie informacji o niewystartowaniu gry przy probie dolaczenia
     */
    @FXML public void joinGame() {
        clickBut();
        startAnchor.setVisible(false);
        joinAnchor.setVisible(true);
        try {
			Main.setGame(new Game(2, main));
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
        try {
			Main.getGame().setSock(new Socket(serverIpAddress.getValue(), 4242));
			Main.runGame();
		} catch (IOException e) {
			joinError.setVisible(true);
			hoverBut();
		}
    }

    /**
     * Metoda do zrezygnowania z oczekiwania na drugiego gracza
     */
    @FXML public void cancelWaiting() {
    	clickBut();
    	startAnchor.setVisible(true);
        waitingAnchor.setVisible(false);
        Main.cancelExecutor();
    }

    /**
     * Metoda do powrotu z okna ustawien
     */
    @FXML public void backSet() {
    	clickBut();
    	startAnchor.setVisible(true);
        setAnchor.setVisible(false);
    }

    /**
     * Metoda do powrotu z okna dolaczenia do gry gdy jeszcze nie wystartowala
     */
    @FXML public void cancelJoining() {
    	clickBut();
    	if (Main.getGame().getSock() != null) {
    		try {
				Main.getGame().getSock().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	startAnchor.setVisible(true);
        joinAnchor.setVisible(false);
		joinError.setVisible(true);
    }

    /**
     * Metoda obsluguja dzwiek przy wskazaniu przycisku kursorem myszy
     */
    @FXML public void hoverBut() {
    	bowClip.stop();
    	bowClip.setMicrosecondPosition(0);
    	bowClip.start();
    }

    /**
     * Metoda obslugujaca dzwiek przy kliknieciu przycisku
     */
    @FXML public void clickBut() {
    	clickClip.stop();
    	clickClip.setMicrosecondPosition(0);
    	clickClip.start();
    }

    /**
     * Metoda obslugujaca startowe zachowanie okna, jego parametry i obsluge kursora
     */
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

    /**
     * Metoda inicjujaca ustawienia dzwiekowe
     */
    private void initAudio() {
    	AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("bin/view/sounds/bow.wav").getAbsoluteFile());
	    	bowClip = AudioSystem.getClip();
	    	bowClip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {}
		AudioInputStream audioInputStream2;
		try {
			audioInputStream2 = AudioSystem.getAudioInputStream(new File("bin/view/sounds/click.wav").getAbsoluteFile());
	    	clickClip = AudioSystem.getClip();
	    	clickClip.open(audioInputStream2);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {}
    }

    /**
     * Metoda inicjujaca reakcje na wskazanie elementu kursorem badz klikniecie na nim
     */
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

    /**
     * Metoda weryfikujaca wprowadzany adres IP
     * @return zwraca ciag bedacy adresem IP
     */
    private String makePartialIPRegex() {
    	String pattern =
    	        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return pattern;
    }

    /**
     * Metoda do obslugi klikniecia
     * @param evt zdarzenie kursora typu MouseEvent
     */
    private void boardImageMousePressed(MouseEvent evt) {
        xx=(int) evt.getX();
        yy=(int) evt.getY();
    }

    /**
     * Metoda do obslugi ruchu kursora
     * @param evt zdarzenie kursora typu MouseEvent
     */
    private void boardImageMouseDragged(MouseEvent evt) {
        int x=(int) evt.getScreenX();
        int y=(int) evt.getScreenY();
        primaryStage.setX(x-xx);
        primaryStage.setY(y-yy);
    }
}