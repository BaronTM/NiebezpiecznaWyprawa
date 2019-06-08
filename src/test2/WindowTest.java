package test2;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import controller.Main;
import controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DaemonThreadFactory;

/**
 * Klasa odpowiedzialna za test dzialania okna
 * @author Ernest Paprocki
 *
 */
public class WindowTest extends Application{

	WindowTest main;
	Stage mainStage;
	Executor executor;

	@Override
    public void start(Stage primaryStage) {
        try {
        	FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/MainWindowView.fxml"));
        	AnchorPane pane;
        	primaryStage.initStyle(StageStyle.UNDECORATED);
            pane = loader.load();
            pane.addEventHandler(MouseEvent.MOUSE_CLICKED, l -> {
            	int x = (int) l.getSceneX();
            	int y = (int) l.getSceneY();
            	System.out.println( "x: " + x + "    y: " + y);
            });
            primaryStage.setMinHeight(500);
            primaryStage.setMinWidth(500);
            primaryStage.setResizable(false);
            mainStage = primaryStage;
            Scene scene = new Scene(pane);
            MainWindowController startGameController = loader.getController();
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
}