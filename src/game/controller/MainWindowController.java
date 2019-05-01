package game.controller;

import java.io.IOException;

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