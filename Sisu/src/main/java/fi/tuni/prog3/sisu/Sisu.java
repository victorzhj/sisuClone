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
        
        /*
        group = new FlowPane();
        Button backButton = new Button();
        backButton.setPrefSize(100, 50);
        backButton.setText("back to settings");
        group.getChildren().add(backButton);
        Scene scene1 = new Scene(group, 300, 300);


        settingsDialog settings = new settingsDialog(stage, scene1, group);
        stage.setTitle("SISU");
        stage.setScene(settings.getScene());


        backButton.setOnAction((event) -> {
            stage.setScene(settings.getScene());
        });
        */
        mainScene mainScene = new mainScene(stage);




        //stage.show();

        
        

        


        /*
        Button startButton = new Button("Go to settings");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) 
        });
        */

        //stage.setScene(arg0);

        /*
        var label = new Label("SISU");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
        */

        
    }

    public static void main(String[] args) {
        launch();
    }

}