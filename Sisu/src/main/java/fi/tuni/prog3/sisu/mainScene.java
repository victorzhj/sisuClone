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

import java.util.TreeMap;



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

    private void getModuleData(settingsDialog.selectedData selected, boolean isDegree) {

        TreeItem<treeItems> rootItem = new TreeItem<treeItems>();
        degreeProgram.setRoot(rootItem);
            
        ModuleData module;
        DegreeProgrammeData degreedata;

        if ( isDegree ){
            module = new ModuleData(
                new networkHandler().getModuleByGroupId(selected.getStudyModuleId()));
    
            System.out.println("test");
    
    
            degreedata = new DegreeProgrammeData(
                new networkHandler().getModuleByGroupId(selected.getStudyModuleId()));
        }

        else {
            module = new ModuleData(
                new networkHandler().getModuleByGroupId(selected.getDegreeProgrammeId()));
    
            System.out.println("test");
    
    
            degreedata = new DegreeProgrammeData(
                new networkHandler().getModuleByGroupId(selected.getDegreeProgrammeId()));
        }
        


/*
        System.out.println("degree data:");
        System.out.println(degreedata.getName().get("fi"));
        System.out.println(degreedata.getId());
        System.out.println("Alimoduulien määrä: " + degreedata.getModules().size());
        
        
        System.out.println("module data:");
        System.out.println(module.getName().get("fi"));
        System.out.println(module.getId());
        System.out.println("Alikurssien määrä: " + module.getWhenSubModuleAreCourses().size());
        System.out.println("Alimoduulien määrä: " + module.getWhenSubModuleAreModules().size()); 
       */ 
        
        // Only modules under
        if ( module.getWhenSubModuleAreCourses().isEmpty() ) {
            TreeMap<String, ModuleData> modules = module.getWhenSubModuleAreModules();
            rootItem.setValue(new treeItems(module.getName().get("fi"), module.getId(), false, false));    

            // Make root item (degree) for treeview
            System.out.println("alimoduulien avaimet:");
            for ( var mod : modules.values() ) {
                System.out.println(mod.getName().get("fi"));
            }
            //rootItem.getChildren().add(getSubModules(rootItem, modules));
            getSubModules(rootItem, modules);

        } 

        // Only courses under
        else {
            rootItem.setValue(new treeItems(degreedata.getName().get("fi"), selected.getStudyModuleId(), false, true));
            
            //TreeItem<treeItems> subCourses = getSubCourses(rootItem, module.getWhenSubModuleAreCourses());
            //rootItem.getChildren().add(subCourses);

            getSubCourses(rootItem, module.getWhenSubModuleAreCourses());
        }
        
    }

    private void getSubModules(TreeItem<treeItems> root, TreeMap<String, ModuleData> modules) {

        for ( var module : modules.values() ){
            TreeItem<treeItems> branch = new TreeItem<treeItems>();

            // No submodules or courses under module
            if ( module.getWhenSubModuleAreModules().isEmpty() && module.getWhenSubModuleAreCourses().isEmpty() ) {
                branch.setValue(new treeItems(module.getName().get("fi"), module.getId(), true, true));
                root.getChildren().add(branch);
            }

            // Only submodules under branch
            else if ( module.getWhenSubModuleAreCourses().isEmpty() ) {
                branch.setValue(new treeItems(module.getName().get("fi"), module.getId(), false, false));
                root.getChildren().add(branch);

                //TreeItem<treeItems> subBranch = new TreeItem<treeItems>();
                //branch.getChildren().add(getSubModules(subBranch, module.getWhenSubModuleAreModules()));
                getSubModules(branch, module.getWhenSubModuleAreModules());

                
            }

            // when only courses under branch
            else if ( module.getWhenSubModuleAreModules().isEmpty() ) {
                branch.setValue(new treeItems(module.getName().get("fi"), module.getId(), false, false));
                root.getChildren().add(branch);
                //TreeItem<treeItems> subBranch = new TreeItem<treeItems>();

                //branch.getChildren().add(getSubCourses(branch, module.getWhenSubModuleAreCourses()));

                getSubCourses(branch, module.getWhenSubModuleAreCourses());
                
            }

        }
        System.out.println("jippii");
    }


    private void getSubCourses(TreeItem<treeItems> root, TreeMap<String, CourseData> courses) {

        for ( var course : courses.values() ){
            TreeItem<treeItems> branch = new TreeItem<treeItems>();

            branch.setValue(new treeItems(course.getName().get("fi"), course.getCode(), true, true));
            root.getChildren().add(branch);
        }
        //return root;

    }



    mainScene(Stage mainStage){

        this.stage = mainStage;

        HBox group = new HBox(degreeProgram);
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


            // get infomation of the selected degree programme or study module 
            selected = settings.getSelectedData();

            String degreeProgrammeId = selected.getDegreeProgrammeId();

            System.out.println("studName: " + selected.getStudName());
            System.out.println("studNumber: " + selected.getStudNumber());
            System.out.println("degreeProgrammeName: " + selected.getDegreeProgrammeName());
            System.out.println("degreeProgrammeId: " + selected.getDegreeProgrammeId());
            System.out.println("studyModuleName: " + selected.getStudyModuleName());
            System.out.println("studyModuleId: " + selected.getStudyModuleId());

            if ( degreeProgrammeId.equals("No DegreeProgramme") ) {
                //käytä degreeprogrammedataa

                getModuleData(selected, true); 
            }

            else {
                getModuleData(selected, false);
            }




        
    });

    this.stage.show();
    }
}