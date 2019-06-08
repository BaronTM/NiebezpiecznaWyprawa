package test2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Pawn;

/**
 * Klasa odpowiedzialna za test ukladu elementow w oknie
 * @author Ernest Paprocki
 *
 */
public class ViewTest extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			Pawn pionek1 = new Pawn(Color.BLUE, 1);
			Pawn pionek2 = new Pawn(Color.LIME, 2);
			Pawn pionek3 = new Pawn(Color.RED, 3);
			root.setLeft(pionek1);
			root.setCenter(pionek2);
			root.setRight(pionek3);
			scene.getStylesheets().add(ViewTest.class.getResource("/game/view/style.css").toExternalForm());
			primaryStage.setTitle("Welcome app");
			primaryStage.setScene(scene);
			primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}