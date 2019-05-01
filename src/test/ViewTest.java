package test;

import game.model.Pionek;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ViewTest extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			Pionek pionek1 = new Pionek(Color.BLUE, 1);
			Pionek pionek2 = new Pionek(Color.LIME, 2);
			Pionek pionek3 = new Pionek(Color.RED, 3);
			root.setLeft(pionek1);
			root.setCenter(pionek2);
			root.setRight(pionek3);
			scene.getStylesheets().add(ViewTest.class.getResource("/game/view/styl.css").toExternalForm());
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
