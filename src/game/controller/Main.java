package game.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.model.DaemonThreadFactory;
import game.model.Game;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import test.ViewTest;

public class Main extends Application {
	private static Stage mainStage;
	private static Main main;
    private static Label infoTxt;
    private static Label scoreInfoTxt;
    private static SequentialTransition infoTxtSeq;
    private static SequentialTransition scoreTxtSeqOn;

	private static Game game;
	private static ExecutorService executor;
	private static ServerSocket serverSocket;
	private static MainWindowController mwc;

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

    public static void main(String[] args) {
        launch(args);
    }

	public static Game getGame() {
		return game;
	}

	public static void setGame(Game game) {
		Main.game = game;
	}

	public static ExecutorService getExecutor() {
		return executor;
	}

	public static void setExecutor(ExecutorService executor) {
		Main.executor = executor;
	}

	public static Stage getMainStage() {
		return mainStage;
	}

	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static void setServerSocket(ServerSocket serverSocket) {
		Main.serverSocket = serverSocket;
	}

	public static Label getInfoTxt() {
		return infoTxt;
	}

	public static void setInfoTxt(Label infoTxt) {
		Main.infoTxt = infoTxt;
	}

	public static SequentialTransition getInfoTxtSeq() {
		return infoTxtSeq;
	}

	public static void setInfoTxtSeq(SequentialTransition infoTxtSeq) {
		Main.infoTxtSeq = infoTxtSeq;
	}

	public static SequentialTransition getScoreTxtSeq() {
		return scoreTxtSeqOn;
	}

	public static void setScoreTxtSeq(SequentialTransition scoreTxtSeq) {
		Main.scoreTxtSeqOn = scoreTxtSeq;
	}

	public static MainWindowController getMainWindowController() {
		return mwc;
	}

	public static Label getScoreInfoTxt() {
		return scoreInfoTxt;
	}

	public static void setScoreInfoTxt(Label scoreInfoTxt) {
		Main.scoreInfoTxt = scoreInfoTxt;
	}

	public static void setMainWindowController(MainWindowController mainWindowController) {
		mwc = mainWindowController;
	}

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

	public static void exitGame() {
		cancelExecutor();
        mainStage.close();
		System.exit(0);
	}
}