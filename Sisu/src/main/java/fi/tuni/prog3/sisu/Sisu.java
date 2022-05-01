package fi.tuni.prog3.sisu;

import javafx.application.Application;
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