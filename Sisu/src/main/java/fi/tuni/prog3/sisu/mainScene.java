package fi.tuni.prog3.sisu;
import fi.tuni.prog3.sisu.settingsDialog;
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
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;



public class mainScene {
    
    private Stage stage;
    private Scene scene;

    mainScene(Stage mainStage){

        this.stage = mainStage;

        FlowPane group = new FlowPane();
        Button backButton = new Button();
        backButton.setPrefSize(100, 50);
        backButton.setText("back to settings");
        group.getChildren().add(backButton);
        Scene scene1 = new Scene(group, 300, 300);

        settingsDialog settings = new settingsDialog(this.stage, scene1);
        mainStage.setTitle("SISU");
        mainStage.setScene(settings.getScene());

        backButton.setOnAction((event) -> {
            mainStage.setScene(settings.getScene());
        });




        mainStage.show();
    }
    
/*      Label testi = new Label(selected.studName);
        group.getChildren().add(testi);
        */







        


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
