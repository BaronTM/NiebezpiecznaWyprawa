package test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import game.controller.Main;
import game.controller.MainWindowController;
import game.controller.StartGameController;
import game.model.DaemonThreadFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WindowTest extends Application{
	
	WindowTest main;
	Stage mainStage;
	Executor executor;
	
	@Override
    public void start(Stage primaryStage) {
        try {
        	FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/MainWindowView.fxml"));               
        	AnchorPane pane;
        	main = this;
            pane = loader.load();
            primaryStage.setMinHeight(500);
            primaryStage.setMinWidth(500);
            primaryStage.setResizable(false);
            mainStage = primaryStage;
            Scene scene = new Scene(pane);
            MainWindowController startGameController = loader.getController();
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

	

}
