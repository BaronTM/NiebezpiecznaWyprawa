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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowController {
    private Main main;
    private Stage primaryStage;

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
//			session = env.newSessionConnector("172.28.112.208", 1234).connect();
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