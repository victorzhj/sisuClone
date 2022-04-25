package fi.tuni.prog3.sisu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
public class settingsDialog extends Application{
    private TreeView<treeItems> degreeProgramsList =new TreeView<treeItems>();
    private networkHandler networker = new networkHandler();
    private Button confirmButton = new Button();
    private TextField nameField = new TextField();
    private TextField studentNumber = new TextField();
    private GridPane settingsGrid = new GridPane();
    private class treeItems{
        treeItems(String name, String id){
            this.name = name;
            this.id = id;
        }
        private String name;
        private String id;

        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        @Override
        public String toString() {
            return getName();
        }
    }


    @Override
    public void start(Stage mainStage) throws Exception {
        nameField.setPrefSize(200, 50);
        confirmButton.setPrefSize(100, 50);
        studentNumber.setMinSize(200, 50);
        degreeProgramsList.setPrefSize(400, 200);
        studentNumber.setText("Enter student number");
        nameField.setText("Enter name");
        confirmButton.setText("Confirm");
        settingsGrid.add(nameField, 0, 0, 1, 1);
        settingsGrid.add(studentNumber, 0, 1,1, 1);
        settingsGrid.add(degreeProgramsList, 2, 0, 1, 3);
        settingsGrid.add(confirmButton, 0,2, 1 ,1);
        confirmButton.setAlignment(Pos.CENTER);
        TreeItem<treeItems> rootItem = new TreeItem<treeItems>();

        rootItem.setValue(new treeItems("DegreeProgrammes", "NO-ID"));
        degreeProgramsList.setRoot(rootItem);

        var degrees = DegreesData.getDegreesInformation();
        var scene = new Scene(settingsGrid);
        mainStage.setScene(scene);
        mainStage.show();
        for(var degree : degrees.values()){
            TreeItem<treeItems> branch = new TreeItem<treeItems>();
            branch.setValue(new treeItems(degree.get("name"), degree.get("groupId")));
            DegreeProgrammeData degreeProgram = new DegreeProgrammeData(networker.getModuleByGroupId(degree.get("groupId")));
            for(var testi : degreeProgram.getFieldOfStudy().values()){
                System.out.println(testi.getId());
                TreeItem<treeItems> subBranch = new TreeItem<treeItems>();
                if(testi.getName().get("fi").equals("No name")){
                    subBranch.setValue(new treeItems(testi.getName().get("en"), testi.getId()));
                }
                else{
                    subBranch.setValue(new treeItems(testi.getName().get("fi"), testi.getId()));
                }
                branch.getChildren().add(subBranch);
            }
            rootItem.getChildren().add(branch);
        }
        degreeProgramsList.setMaxSize(400, 400);
        
        
    }

    public static void main(String[] args){
        launch();
    }
}
