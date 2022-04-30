package fi.tuni.prog3.sisu;

import fi.tuni.prog3.sisu.settingsDialog.selectedData;
import fi.tuni.prog3.sisu.mainScene;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Sisu extends Application {


    @Override
    public void start(Stage stage) {
        

        mainScene mainScene = new mainScene(stage);

        
    }

    public static void main(String[] args) {
        launch();
    }

}