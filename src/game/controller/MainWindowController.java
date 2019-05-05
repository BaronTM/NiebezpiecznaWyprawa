package game.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.cojen.dirmi.Environment;
import org.cojen.dirmi.Session;

import game.model.RemoteGame;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainWindowController {
    private Main main;
    private Stage primaryStage;
    
    @FXML
    private Button losBut;
    
    @FXML
    private Label infoTxt;
    
    public void initialize() {
    	
    	FadeTransition fade01 = new FadeTransition(Duration.seconds(1), infoTxt);
    	fade01.setFromValue(0);
    	fade01.setToValue(1);
    	PauseTransition pause = new PauseTransition(Duration.seconds(3));
    	FadeTransition fade02 = new FadeTransition(Duration.seconds(1), infoTxt);
    	fade02.setFromValue(1);
    	fade02.setToValue(0);
    	SequentialTransition seq = new SequentialTransition();
    	seq.getChildren().addAll(fade01, pause, fade02);
    	Main.setInfoTxtSeq(seq);
    	infoTxt.setStyle("-fx-background-color: transparent;");
    	infoTxt.setVisible(false);
    	
    	main.setInfoTxt(infoTxt);	
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML public void closeMainWindow() {
        primaryStage.close();
    }

    @FXML public void uruchomLosowanie() {
        losuj();
    }

    public void losuj() {
    	    	
//    	try {
//    		Registry registry = LocateRegistry.getRegistry("172.28.112.208");
//			RemoteGame remoteGame = (RemoteGame) registry.lookup("Gra"); 
//			remoteGame.updateData();
//		} catch (RemoteException | NotBoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
    	
//    	Environment env = new Environment();
//        //String host = args[0];
//        Session session;
//		try {
//			session = env.newSessionConnector("localhost", 5058).connect();
//			RemoteGame remoteGame = (RemoteGame) session.receive();
//	        remoteGame.updateData();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        
        
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/LosujWindowView.fxml"));
        try {
            AnchorPane pane = loader.load();
            Stage losujWindowStage = new Stage();
            losujWindowStage.setTitle("Losowanie");
            losujWindowStage.initModality(Modality.WINDOW_MODAL);
            losujWindowStage.initOwner(primaryStage);
            losujWindowStage.setMinHeight(650);
            losujWindowStage.setMinWidth(500);
            Scene scene = new Scene(pane);
            losujWindowStage.setScene(scene);

            LosujWindowController animationWindowController = loader.getController();
            animationWindowController.setAnimationWindowStage(losujWindowStage);
            animationWindowController.setAnimation();
            losujWindowStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}