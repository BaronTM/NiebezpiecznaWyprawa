package game.controller;

import java.io.IOException;
import java.util.Optional;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import test.ViewTest;

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
    private Label scoreInfoTxt;

    @FXML
    private ImageView boardImage;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextArea textInstr;

    @FXML
    private TextField lab;

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

    	FadeTransition fadeOn = new FadeTransition(Duration.seconds(1), scoreInfoTxt);
    	fadeOn.setFromValue(0);
    	fadeOn.setToValue(1);
    	SequentialTransition seqOn = new SequentialTransition();
    	seqOn.getChildren().addAll(fadeOn);
    	Main.setScoreTxtSeq(seqOn);

    	infoTxt.setStyle("-fx-background-color: transparent;");
    	infoTxt.setVisible(false);

    	scoreInfoTxt.setStyle("-fx-background-color: transparent;");
    	scoreInfoTxt.setVisible(false);

    	main.setInfoTxt(infoTxt);
    	main.setScoreInfoTxt(scoreInfoTxt);

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

    public void przekaz(String foeClaim) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/WybierzWindowView.fxml"));
        try {
            AnchorPane pane = loader.load();
            Stage wybierzWindowStage = new Stage();
            wybierzWindowStage.setTitle("Wybor");
            wybierzWindowStage.initModality(Modality.WINDOW_MODAL);
            wybierzWindowStage.initOwner(main.getMainStage());//(losujWindowStage);
            wybierzWindowStage.setMinHeight(650);
            wybierzWindowStage.setMinWidth(500);
            wybierzWindowStage.setMaxHeight(650);
            wybierzWindowStage.setMaxWidth(500);
            wybierzWindowStage.setHeight(650);
            wybierzWindowStage.setWidth(500);
            wybierzWindowStage.setX(primaryStage.getX() + 100);
            wybierzWindowStage.setY(primaryStage.getY() + 75);
            Scene scene = new Scene(pane);
            wybierzWindowStage.initStyle(StageStyle.UNDECORATED);
            wybierzWindowStage.setScene(scene);
            WybierzWindowController animationWindowController = loader.getController();
            animationWindowController.setAnimationWindowStage(wybierzWindowStage);
            animationWindowController.setChoice(foeClaim);
            wybierzWindowStage.showAndWait();
            main.getGra().sendObj(animationWindowController.getAnswer());
            wybierzWindowStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showInstr() {
    	Stage instrWindowStage = new Stage();
    	FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/InstrukcjaWindowView.fxml"));
        try {
        	AnchorPane pane = loader.load();
        	((InstrukcjaWindowController) loader.getController()).setStage(instrWindowStage);
        	instrWindowStage.setTitle("Instrukcja");
        	instrWindowStage.initModality(Modality.WINDOW_MODAL);
        	instrWindowStage.initOwner(Main.getMainStage());
        	instrWindowStage.setMinHeight(650);
        	instrWindowStage.setMinWidth(500);
        	instrWindowStage.setHeight(650);
        	instrWindowStage.setWidth(500);
        	instrWindowStage.setMaxHeight(650);
        	instrWindowStage.setMaxWidth(500);
            instrWindowStage.initStyle(StageStyle.UNDECORATED);
            instrWindowStage.setX(primaryStage.getX() + 100);
            instrWindowStage.setY(primaryStage.getY() + 75);
            Scene scene = new Scene(pane);
            instrWindowStage.setScene(scene);

            scene.getStylesheets().add(ViewTest.class.getResource("/game/view/styl.css").toExternalForm());
            instrWindowStage.showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}