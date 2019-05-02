package game.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.model.DaemonThreadFactory;
import game.model.Gra;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import test.ViewTest;


public class Main extends Application {

	private static Stage mainStage;
	private static Main main;
	
	private static Gra gra;
	private static ExecutorService executor;
	private static ServerSocket serverSocket;
	
    @Override
    public void start(Stage primaryStage) {
        try {
            //FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/MainWindowView.fxml"));
        	FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/StartGameView.fxml"));            
        	AnchorPane pane;
        	main = this;
            pane = loader.load();
            primaryStage.setMinHeight(500);
            primaryStage.setMinWidth(500);
            primaryStage.setResizable(false);
            mainStage = primaryStage;
            Scene scene = new Scene(pane);
            StartGameController startGameController = loader.getController();
            startGameController.setMain(main);
            startGameController.setPrimaryStage(mainStage);
            scene.getStylesheets().add(ViewTest.class.getResource("/game/view/styl.css").toExternalForm());
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
		
	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static void setServerSocket(ServerSocket serverSocket) {
		Main.serverSocket = serverSocket;
	}

	public static void cancelExecutor() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
				gra.getSock().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
	        mainWindowController.setMain(main);
	        mainWindowController.setPrimaryStage(mainStage);
	        scene.getStylesheets().add(ViewTest.class.getResource("/game/view/styl.css").toExternalForm());
	        mainStage.setScene(scene);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}