package game.controller;

import game.model.Gra;
import game.model.Gracz;
import game.model.Pionek;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import test.ViewTest;


public class Main extends Application {
	
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
            
//            gra = new Gra();
//            Gracz g1 = new Gracz(1, "Adam", Color.RED);
//            Gracz g2 = new Gracz(2, "Roman", Color.LIME);
//            gra.dodajGracza(g1);
//            gra.dodajGracza(g2);
//                    
//            for (int i = 0; i < g1.getPionki().size(); i++) {
//            	Pionek p = g1.getPionki().get(i);
//            	pane.getChildren().add(p);
//            	p.setLayoutX(30 + i * 60 - p.getStageX());
//            	p.setLayoutY(620 - p.getStageY() + 10);
//            }
//            
//            for (int i = 0; i < g2.getPionki().size(); i++) {
//            	Pionek p = g2.getPionki().get(i);
//            	pane.getChildren().add(p);
//            	p.setLayoutX(290 + i * 60 - p.getStageX());
//            	p.setLayoutY(620 - p.getStageY() + 10);
//            }
            
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