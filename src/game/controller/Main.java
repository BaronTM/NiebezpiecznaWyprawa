package game.controller;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.model.Gra;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import test.ViewTest;


public class Main extends Application {
	
	private static Socket socket;
	private static Gra gra;
	private static ExecutorService executor;
	
    @Override
    public void start(Stage primaryStage) {
        try {
            //FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/MainWindowView.fxml"));
        	FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/StartGameView.fxml"));            
        	AnchorPane pane;
            pane = loader.load();
            primaryStage.setMinHeight(500);
            primaryStage.setMinWidth(500);
            primaryStage.setResizable(false);
            Scene scene = new Scene(pane);
            StartGameController startGameController = loader.getController();
            startGameController.setMain(this);
            startGameController.setPrimaryStage(primaryStage);
            scene.getStylesheets().add(ViewTest.class.getResource("/game/view/styl.css").toExternalForm());
            executor = Executors.newFixedThreadPool(3);
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		Main.socket = socket;
	}

	public static Gra getGra() {
		return gra;
	}

	public static void setGra(Gra gra) {
		Main.gra = gra;
	}

	public static ExecutorService getExecutor() {
		return executor;
	}

	public static void setExecutor(ExecutorService executor) {
		Main.executor = executor;
	}
    
}