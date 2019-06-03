package game.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;

import org.w3c.dom.ls.LSOutput;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainWindowController {
    private Main main;
    private Stage primaryStage;
    private int xx;
    private int yy;
    
    @FXML
    private Button losBut;
    
    @FXML
    private Label infoTxt;
    
    @FXML
    private ImageView boardImage;
    
    @FXML
    private AnchorPane mainAnchor;
    
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
    	boardImage.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
    		boardImageMousePressed(e);
    	});
    	boardImage.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
    		boardImageMouseDragged(e);
    	});
    	losBut.setVisible(false);
    	
//    	mainAnchor.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//    		double x = e.getX();
//    		double y = e.getY();
//    		System.out.println("X: " + x + "    Y: " + y);
//    	});
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
        
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/LosujWindowView.fxml"));
        try {
            AnchorPane pane = loader.load();
            Stage losujWindowStage = new Stage();
            losujWindowStage.setTitle("Losowanie");
            losujWindowStage.initModality(Modality.WINDOW_MODAL);
            losujWindowStage.initOwner(primaryStage);
            losujWindowStage.setMinHeight(650);
            losujWindowStage.setMinWidth(500);
            losujWindowStage.setHeight(650);
            losujWindowStage.setWidth(500);
            losujWindowStage.setMaxHeight(650);
            losujWindowStage.setMaxWidth(500);
            Scene scene = new Scene(pane);
            losujWindowStage.initStyle(StageStyle.UNDECORATED);
            losujWindowStage.setScene(scene);
            losujWindowStage.setX(primaryStage.getX() + 100);
            losujWindowStage.setY(primaryStage.getY() + 75);
            
            LosujWindowController animationWindowController = loader.getController();
            animationWindowController.setAnimationWindowStage(losujWindowStage);
            animationWindowController.setAnimation();
            losujWindowStage.showAndWait();
            losBut.setVisible(false);
            main.getGra().sendObj(animationWindowController.getResult());
            losujWindowStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private void boardImageMousePressed(MouseEvent evt) {
        xx=(int) evt.getX();
        yy=(int) evt.getY() + 80;
    }

    private void boardImageMouseDragged(MouseEvent evt) {
        int x=(int) evt.getScreenX();
        int y=(int) evt.getScreenY();
        primaryStage.setX(x-xx);
        primaryStage.setY(y-yy);
    }
    
    @FXML
    private void exitGame() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Wyjście z gry");
    	alert.setHeaderText("Rozgrywka nie została zakończona!");
    	alert.setContentText("Czy na pewno chcesz wyjść?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		Main.exitGame();
    	} else {
    	    // ... user chose CANCEL or closed the dialog
    	}
    }
    
    public Button getLosBut() {
    	return losBut;
    }
    
}