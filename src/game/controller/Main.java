package game.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.model.DaemonThreadFactory;
import game.model.Game;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import test.ViewTest;

/**
 * Klasa obsluguje wyswietlanie okna startowego
 * Tutaj ustawiamy parametry polaczenia, rozpoczynamy gre lub dolaczamy do juz istniejacej
 * @author Karol Kusmierz
 */
public class Main extends Application {

	/**
	 * kontroler do wyswietlenia okna startowego
	 */
	private static Stage mainStage;
	/**
	 * klasa glowna
	 */
	private static Main main;
    /**
     * etykieta wskazujaca biezacy ruch
     */
    private static Label infoTxt;
    /**
     * etykieta wskazujaca efekt losowania
     */
    private static Label additionalInfoTxt;
    /**
     * etykieta wskazujaca punktacje
     */
    private static Label scoreInfoTxt;
    /**
     * laczenie kilku animacji w jedna sekwencje przy ruchu
     */
    private static SequentialTransition infoTxtSeq;
    /**
     * laczenie kilku animacji w jedna sekwencje przy zliczaniu punktacji
     */
    private static SequentialTransition scoreTxtSeqOn;
    /**
     * rownoczesne laczenie kilku animacji w jedna sekwencje przy zmianie gracza
     */
    private static ParallelTransition additionalInfoTxtSeq;

	/**
	 * klasa Game do obslugi gry
	 */
	private static Game game;
	/**
	 * wykonawca watku
	 */
	private static ExecutorService executor;
	/**
	 * gniazdo serwera
	 */
	private static ServerSocket serverSocket;
	/**
	 * kontroler do obslugi planszy graczy
	 */
	private static MainWindowController mwc;

	/**
	 * Metoda do uruchomienia okna startowego
	 * @param primaryStage parametr typu Stage wskazujacy obslugiwany kontener okna startowego
	 */
	@Override
    public void start(Stage primaryStage) {
        try {
        	FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/StartGameView.fxml"));
        	AnchorPane pane;
        	main = this;
            pane = loader.load();
            primaryStage.setMinHeight(500);
            primaryStage.setMinWidth(500);
            primaryStage.setResizable(false);
            mainStage = primaryStage;
            mainStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            StartGameController startGameController = loader.getController();
            startGameController.setMain(main);
            startGameController.setPrimaryStage(mainStage);
            scene.getStylesheets().add(ViewTest.class.getResource("/game/view/style.css").toExternalForm());
            executor = Executors.newFixedThreadPool(4, new DaemonThreadFactory());
            mainStage.setScene(scene);
            mainStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * metoda glowna do obslugi aplikacji
     * @param args przekazywane parametry
     */
    public static void main(String[] args) {
        launch(args);
    }

	/**
	 * Metoda do przekazania stanu gry
	 * @return zwraca parametry gry
	 */
	public static Game getGame() {
		return game;
	}

	/**
	 * Metoda do ustawienia stanu gry
	 * @param game stan gry typu Game
	 */
	public static void setGame(Game game) {
		Main.game = game;
	}

	/**
	 * Metoda do przekazania stanu watku
	 * @return zwraca parametry watku
	 */
	public static ExecutorService getExecutor() {
		return executor;
	}

	/**
	 * Metoda do ustawienia stanu watku
	 * @param executor stan watku typu ExecutorService
	 */
	public static void setExecutor(ExecutorService executor) {
		Main.executor = executor;
	}

	/**
	 * Metoda do przekazania kontenera planszy graczy
	 * @return zwraca kontener planszy graczy
	 */
	public static Stage getMainStage() {
		return mainStage;
	}

	/**
	 * Metoda do przekazania gniazda serwera
	 * @return zwraca gniazdo serwera
	 */
	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	/**
	 * Metoda do ustawienia gniazda serwera
	 * @param serverSocket stan gniazda serwera typu ServerSocket
	 */
	public static void setServerSocket(ServerSocket serverSocket) {
		Main.serverSocket = serverSocket;
	}

	/**
	 * Metoda do przekazania biezacego ruchu
	 * @return zwraca informacje o biezacym ruchu
	 */
	public static Label getInfoTxt() {
		return infoTxt;
	}

	/**
	 * Metoda do ustawienia biezacego ruchu w etykiecie
	 * @param infoTxt informacja o biezacym ruchu typu Label
	 */
	public static void setInfoTxt(Label infoTxt) {
		Main.infoTxt = infoTxt;
	}

	/**
	 * Metoda do przekazania informacji o sekwencji laczacej akcje przy ruchu
	 * @return zwraca sewencje animacji ruchu
	 */
	public static SequentialTransition getInfoTxtSeq() {
		return infoTxtSeq;
	}

	/**
	 * Metoda do ustawienia sekwencji do wykonania ruchu
	 * @param infoTxtSeq sekwencja animacji do wykonania ruchu typu SequentialTransition
	 */
	public static void setInfoTxtSeq(SequentialTransition infoTxtSeq) {
		Main.infoTxtSeq = infoTxtSeq;
	}

	/**
	 * Metoda do przekazania informacji o sekwencji laczacej akcje do przekazania punktacji
	 * @return zwraca sekwencje animacji wskazujaca punktacje
	 */
	public static SequentialTransition getScoreTxtSeq() {
		return scoreTxtSeqOn;
	}

	/**
	 * Metoda do ustawienia sekwencji do przekazania punktacji
	 * @param scoreTxtSeq sekwencja animacji do przekazania punktacji typu SequentialTranstion
	 */
	public static void setScoreTxtSeq(SequentialTransition scoreTxtSeq) {
		Main.scoreTxtSeqOn = scoreTxtSeq;
	}

	/**
	 * Metoda do ustawienia glownej planszy graczy
	 * @return zwraca plansze dla graczy
	 */
	public static MainWindowController getMainWindowController() {
		return mwc;
	}

	/**
	 * Metoda do przekazania informacji o punktacji
	 * @return informacje o punktacji
	 */
	public static Label getScoreInfoTxt() {
		return scoreInfoTxt;
	}

	/**
	 * Metoda  do ustewiania informacji o punktacji
	 * @param scoreInfoTxt etykieta z punktacja typu Label
	 */
	public static void setScoreInfoTxt(Label scoreInfoTxt) {
		Main.scoreInfoTxt = scoreInfoTxt;
	}

	/**
	 * Metoda do przekazania efektu losowania
	 * @return efekt losowania
	 */
	public static Label getAdditionalInfoTxt() {
		return additionalInfoTxt;
	}

	/**
	 * Metoda do ustawienia informacji o efekcie losowania
	 * @param additionalInfoTxt etykieta z informacja bedaca efektem losowania
	 */
	public static void setAdditionalInfoTxt(Label additionalInfoTxt) {
		Main.additionalInfoTxt = additionalInfoTxt;
	}

	/**
	 * Metoda do przekazania animacji przy zmanie gracza
	 * @return laczne animacje do przekazania stanu przy zmianie gracza typu ParallelTranstion
	 */
	public static ParallelTransition getAdditionalInfoTxtSeq() {
		return additionalInfoTxtSeq;
	}

	/**
	 * Metoda do ustawienia aniacji przy zmianie gracza
	 * @param additionalInfoTxtSeq animacja przy zmianie gracza
	 */
	public static void setAdditionalInfoTxtSeq(ParallelTransition additionalInfoTxtSeq) {
		Main.additionalInfoTxtSeq = additionalInfoTxtSeq;
	}

	/**
	 * Metoda do ustawienia planszy graczy
	 * @param mainWindowController kontroler planszy graczy
	 */
	public static void setMainWindowController(MainWindowController mainWindowController) {
		mwc = mainWindowController;
	}

	/**
	 * Metoda do zerwania watku
	 */
	public static void cancelExecutor() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
				game.getSock().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		executor.shutdownNow();
		executor = Executors.newFixedThreadPool(4, new DaemonThreadFactory());
	}

	/**
	 * Metoda do uruchomienia okna z plansza graczy
	 */
	public static void runGame() {
        try {
        	FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/MainWindowView.fxml"));
        	AnchorPane pane;
			pane = loader.load();
	        Scene scene = new Scene(pane);
	        MainWindowController mainWindowController = loader.getController();
	        mwc = mainWindowController;
	        mainWindowController.setMain(main);
	        mainWindowController.setPrimaryStage(mainStage);
	        scene.getStylesheets().add(ViewTest.class.getResource("/game/view/style.css").toExternalForm());
	        mainStage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda do zamkniecia aplikacji
	 */
	public static void exitGame() {
		cancelExecutor();
        mainStage.close();
		System.exit(0);
	}
}