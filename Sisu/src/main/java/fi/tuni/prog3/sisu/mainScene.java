package fi.tuni.prog3.sisu;
import fi.tuni.prog3.sisu.settingsDialog;
import fi.tuni.prog3.sisu.settingsDialog.selectedData;
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
import javafx.scene.layout.HBox;

import static java.lang.System.out;



public class mainScene {
    
    private Stage stage;
    private Scene scene;
    private TreeView<treeItems> degreeProgram =new TreeView<treeItems>();
    private settingsDialog.selectedData selected;
    private settingsDialog settings;


    /**
     * Hidden class that represents stored data in treeview items
     */
    private class treeItems{
        treeItems(String name, String id, Boolean isLeaf,Boolean HasNoStudyModules){
            this.name = name;
            this.id = id;
            this.isLeaf = isLeaf;
            this.hasNoStudyModules = HasNoStudyModules;
        }
        private String name;
        private String id;
        private Boolean isLeaf;
        private Boolean hasNoStudyModules;
        /**
         * Returns the groupId of the item 
         * @return String group of the item
         */
        public String getId() {
            return id;
        }
        /**
         * Returns the name of the item
         * @return String name of the item
         */
        public String getName() {
            return name;
        }
        /**
         * Returns true if the item has no children or false if it does
         * @return Boolean  True if is leaf, False if is not leaf
         */
        public Boolean getIsLeaf() {
            return isLeaf;
        }
        /**
         * Returns True if the item is studyModule False if it isn't
         * @return Boolean True if is studyModule, False if isn't
         */
        public Boolean gethasNoStudyModules() {
            return hasNoStudyModules;
        }
        @Override
        /**
         * Gets the name of the item
         */
        public String toString() {
            return getName();
        }
    }



    mainScene(Stage mainStage){

        this.stage = mainStage;

        HBox group = new HBox();
        Button backButton = new Button();
        backButton.setPrefSize(100, 50);
        backButton.setText("back to settings");
        group.getChildren().add(backButton);
        Scene scene1 = new Scene(group, 300, 300);

        settings = new settingsDialog(this.stage, scene1);
        this.stage.setTitle("SISU");
        this.stage.setScene(settings.getScene());

        backButton.setOnAction((event) -> {
            this.stage.setScene(settings.getScene());
        });

/*        selected = settings.getSelectedData();
        System.out.println(selected.getStudName()); */

        mainStage.sceneProperty().addListener((observable, oldScene, newScene) -> {
            //selected = settings.getSelectedData();
            //System.out.println(selected.getStudName());

            if ( mainStage.getScene() == settings.getScene() ){
                return;
            }

            TreeItem<treeItems> rootItem = new TreeItem<treeItems>();
            degreeProgram.setRoot(rootItem);
            rootItem.setValue(new treeItems("DegreeProgrammes", "NO-ID", false, false));

            // get infomation of the selected degree programme or study module 
            selected = settings.getSelectedData();

            String degreeProgrammeId = selected.getDegreeProgrammeId();

            System.out.println("studName: " + selected.getStudName());
            System.out.println("studNumber: " + selected.getStudNumber());
            System.out.println("degreeProgrammeName: " + selected.getDegreeProgrammeName());
            System.out.println("degreeProgrammeId: " + selected.getDegreeProgrammeId());
            System.out.println("studyModuleName: " + selected.getStudyModuleName());
            System.out.println("studyModuleId: " + selected.getStudyModuleId());

            if ( degreeProgrammeId.isEmpty() ) {
                //käytä degreeprogrammedataa
                DegreeProgrammeData degree = new DegreeProgrammeData(
                    new networkHandler().getModuleById(degreeProgrammeId));
                    System.out.println("jippii");
            }
        });



        this.stage.show();
    }




/*    public void formatCourseView() {
        TreeItem<treeItems> rootItem = new TreeItem<treeItems>();
        degreeProgram.setRoot(rootItem);
        rootItem.setValue(new treeItems("DegreeProgrammes", "NO-ID", false, false));
        selected = settings.getSelectedData();
        
        
    }*/




    
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
