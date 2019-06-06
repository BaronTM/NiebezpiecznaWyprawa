package game.controller;

import java.io.IOException;
import java.util.Optional;

import javax.sound.sampled.Clip;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
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

    @FXML private Button drawBut;
    @FXML private Label infoTxt;
    @FXML private Label additionalInfoTxt;
    @FXML private Label scoreInfoTxt;
    @FXML private ImageView boardImage;
    @FXML private AnchorPane mainAnchor;
    @FXML private TextArea textInstr;
    @FXML private TextField lab;

    private Clip bowClip;
    private Clip clickClip;

    public void initialize() {
    	FadeTransition fade01 = new FadeTransition(Duration.seconds(1), infoTxt);
    	fade01.setFromValue(0);
    	fade01.setToValue(1);
    	PauseTransition pause = new PauseTransition(Duration.seconds(2));
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
    	
    	
    	// Additional info text animation
    	TranslateTransition transition = new TranslateTransition();
    	transition.setDuration(Duration.seconds(2));
    	transition.setFromY(0);
    	transition.setToY(300);
    	
    	FadeTransition fadeAdditional = new FadeTransition(Duration.seconds(1));
    	fadeAdditional.setFromValue(0);
    	fadeAdditional.setToValue(1);
    	
    	FadeTransition fadeAdditional2 = new FadeTransition(Duration.seconds(1));
    	fadeAdditional2.setFromValue(1);
    	fadeAdditional2.setToValue(0);
    	
    	SequentialTransition seqAdditional = new SequentialTransition();
    	seqAdditional.getChildren().addAll(fadeAdditional, fadeAdditional2);
    	
		ParallelTransition parallelTransition = new ParallelTransition(additionalInfoTxt, transition, seqAdditional);
		Main.setAdditionalInfoTxtSeq(parallelTransition);
    	

    	infoTxt.setStyle("-fx-background-color: transparent;");
    	infoTxt.setVisible(false);

    	scoreInfoTxt.setStyle("-fx-background-color: transparent;");
    	scoreInfoTxt.setVisible(false);
    	
    	additionalInfoTxt.setStyle("-fx-background-color: transparent;");
    	additionalInfoTxt.setVisible(false);

    	main.setInfoTxt(infoTxt);
    	main.setScoreInfoTxt(scoreInfoTxt);
    	main.setAdditionalInfoTxt(additionalInfoTxt);

    	boardImage.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
    		boardImageMousePressed(e);
    	});
    	boardImage.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
    		boardImageMouseDragged(e);
    	});
    	drawBut.setVisible(false);
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

    @FXML public void startDraw() {
        draw();
    }

    public void draw() {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/DrawWindowView.fxml"));
        try {
            AnchorPane pane = loader.load();
            Stage drawWindowStage = new Stage();
            drawWindowStage.setTitle("Losowanie");
            drawWindowStage.initModality(Modality.WINDOW_MODAL);
            drawWindowStage.initOwner(primaryStage);
            drawWindowStage.setMinHeight(650);
            drawWindowStage.setMinWidth(500);
            drawWindowStage.setHeight(650);
            drawWindowStage.setWidth(500);
            drawWindowStage.setMaxHeight(650);
            drawWindowStage.setMaxWidth(500);
            Scene scene = new Scene(pane);
            drawWindowStage.initStyle(StageStyle.UNDECORATED);
            drawWindowStage.setScene(scene);
            drawWindowStage.setX(primaryStage.getX() + 100);
            drawWindowStage.setY(primaryStage.getY() + 75);

            DrawWindowController animationWindowController = loader.getController();
            animationWindowController.setAnimationWindowStage(drawWindowStage);
            animationWindowController.setAnimation();
            drawWindowStage.showAndWait();
            drawBut.setVisible(false);
            main.getGame().sendObj(animationWindowController.getResult());
            drawWindowStage.close();
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

    @FXML private void exitGame() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Wyjście z gry");
    	alert.setHeaderText("Wybrałeś opcję ZAKOŃCZ");
    	alert.setContentText("Czy na pewno chcesz wyjść?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		Main.exitGame();
    	} else {}
    }

    public Button getDrawBut() {
    	return drawBut;
    }

    public void comunicate(String foeClaim) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/ChoiceWindowView.fxml"));
        try {
            AnchorPane pane = loader.load();
            Stage choiceWindowStage = new Stage();
            choiceWindowStage.setTitle("Wybor");
            choiceWindowStage.initModality(Modality.WINDOW_MODAL);
            choiceWindowStage.initOwner(main.getMainStage());
            choiceWindowStage.setMinHeight(650);
            choiceWindowStage.setMinWidth(500);
            choiceWindowStage.setMaxHeight(650);
            choiceWindowStage.setMaxWidth(500);
            choiceWindowStage.setHeight(650);
            choiceWindowStage.setWidth(500);
            choiceWindowStage.setX(primaryStage.getX() + 100);
            choiceWindowStage.setY(primaryStage.getY() + 75);
            Scene scene = new Scene(pane);
            choiceWindowStage.initStyle(StageStyle.UNDECORATED);
            choiceWindowStage.setScene(scene);
            ChoiceWindowController animationWindowController = loader.getController();
            animationWindowController.setAnimationWindowStage(choiceWindowStage);
            animationWindowController.setChoice(foeClaim);
            choiceWindowStage.showAndWait();
            main.getGame().sendObj(animationWindowController.getAnswer());
            choiceWindowStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void showInstr() {
    	Stage instrWindowStage = new Stage();
    	FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/InstructionWindowView.fxml"));
        try {
        	AnchorPane pane = loader.load();
        	((InstructionWindowController) loader.getController()).setStage(instrWindowStage);
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

            scene.getStylesheets().add(ViewTest.class.getResource("/game/view/style.css").toExternalForm());
            instrWindowStage.showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML public void showAuth() {
    	Stage authWindowStage = new Stage();
    	FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game/view/AuthorsWindowView.fxml"));
        try {
        	AnchorPane pane = loader.load();
        	((AuthorsWindowController) loader.getController()).setStage(authWindowStage);
        	authWindowStage.setTitle("Instrukcja");
        	authWindowStage.initModality(Modality.WINDOW_MODAL);
        	authWindowStage.initOwner(Main.getMainStage());
        	authWindowStage.setMinHeight(650);
        	authWindowStage.setMinWidth(500);
        	authWindowStage.setHeight(650);
        	authWindowStage.setWidth(500);
        	authWindowStage.setMaxHeight(650);
        	authWindowStage.setMaxWidth(500);
        	authWindowStage.initStyle(StageStyle.UNDECORATED);
        	authWindowStage.setX(primaryStage.getX() + 100);
        	authWindowStage.setY(primaryStage.getY() + 75);
            Scene scene = new Scene(pane);
            authWindowStage.setScene(scene);

            scene.getStylesheets().add(ViewTest.class.getResource("/game/view/style.css").toExternalForm());
            authWindowStage.showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void exitG() {
    	Main.exitGame();
    }

	@FXML public void hoverBut() {
    	bowClip.stop();
    	bowClip.setMicrosecondPosition(0);
    	bowClip.start();
    }

    @FXML public void clickBut() {
    	clickClip.stop();
    	clickClip.setMicrosecondPosition(0);
    	clickClip.start();
    }
}