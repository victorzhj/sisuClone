package fi.tuni.prog3.sisu;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import java.util.TreeMap;



public class mainScene {
    
    private Stage stage;
    private TreeView<treeItems> degreeProgram =new TreeView<treeItems>();
    private settingsDialog.selectedData selected;
    private settingsDialog settings;


    /**
     * Hidden class that represents stored data in treeview items
     */
    private class treeItems{
        treeItems(String name, String id, Boolean isLeaf,Boolean HasNoStudyModules, Boolean courseSubModules){
            this.name = name;
            this.id = id;
            this.isLeaf = isLeaf;
            this.hasNoStudyModules = HasNoStudyModules;
            this.courseSubModules = courseSubModules;
        }

        treeItems(String name, String id, Boolean isLeaf,Boolean HasNoStudyModules, Boolean courseSubModules, ModuleData thisModule){
            this.name = name;
            this.id = id;
            this.isLeaf = isLeaf;
            this.hasNoStudyModules = HasNoStudyModules;
            this.courseSubModules = courseSubModules;
            this.thisModule = thisModule;
        }
        private String name;
        private String id;
        private Boolean isLeaf;
        private Boolean hasNoStudyModules;
        private Boolean courseSubModules;
        private ModuleData thisModule;
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
        /**
         * Returns True if items submodules are courses. Otherwise False.
         * @return Boolean True if items submodules are courses. Otherwise False.
         */
        public Boolean getCourseSubModules() {
            return courseSubModules;
        }
        /**
         * Returns this TreeItems module. If there are any.
         * @return Returns this TreeItems module. If there are any.
         */
        public ModuleData getThisModule() {
            return this.thisModule;
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
        
    
            degreedata = new DegreeProgrammeData(
                new networkHandler().getModuleByGroupId(selected.getStudyModuleId()));
        }

        else {
            module = new ModuleData(
                new networkHandler().getModuleByGroupId(selected.getDegreeProgrammeId()));
        
    
            degreedata = new DegreeProgrammeData(
                new networkHandler().getModuleByGroupId(selected.getDegreeProgrammeId()));
        }
        
        // Only modules under
        if ( module.getWhenSubModuleAreCourses().isEmpty() ) {
            TreeMap<String, ModuleData> modules = module.getWhenSubModuleAreModules();
            rootItem.setValue(new treeItems(module.getName().get("fi"), module.getId(), false, false, false));    

            getSubModules(rootItem, modules);

        } 

        // Only courses under
        else {
            rootItem.setValue(new treeItems(degreedata.getName().get("fi"), selected.getStudyModuleId(), false, true, true, module));

            getSubCourses(rootItem, module.getWhenSubModuleAreCourses());
        }
    }

    private void getSubModules(TreeItem<treeItems> root, TreeMap<String, ModuleData> modules) {

        for ( var module : modules.values() ){
            TreeItem<treeItems> branch = new TreeItem<treeItems>();

            // No submodules or courses under module
            if ( module.getWhenSubModuleAreModules().isEmpty() && module.getWhenSubModuleAreCourses().isEmpty() ) {
                branch.setValue(new treeItems(module.getName().get("fi"), module.getId(), true, true, false));
                root.getChildren().add(branch);
            }

            // Only submodules under branch
            else if ( module.getWhenSubModuleAreCourses().isEmpty() ) {
                branch.setValue(new treeItems(module.getName().get("fi"), module.getId(), false, false, false));
                root.getChildren().add(branch);

                getSubModules(branch, module.getWhenSubModuleAreModules());

                
            }

            // when only courses under branch
            else if ( module.getWhenSubModuleAreModules().isEmpty() ) {
                branch.setValue(new treeItems(module.getName().get("fi"), module.getId(), false, false, true, module));
                root.getChildren().add(branch);

                getSubCourses(branch, module.getWhenSubModuleAreCourses());
                
            }

        }
    }


    private void getSubCourses(TreeItem<treeItems> root, TreeMap<String, CourseData> courses) {

        for ( var course : courses.values() ){
            TreeItem<treeItems> branch = new TreeItem<treeItems>();

            branch.setValue(new treeItems(course.getName().get("fi"), course.getCode(), true, true, false));
            root.getChildren().add(branch);
        }
    }

    mainScene(Stage mainStage){

        this.stage = mainStage;

        HBox group = new HBox(degreeProgram);
        group.setMinHeight(400);
        group.setMinWidth(1000);
        Button backButton = new Button();
        backButton.setPrefSize(100, 50);
        backButton.setText("back to settings");
        Scene scene1 = new Scene(group);

        // Create StackPane to show buttons of courses.
        StackPane showAllCourses = new StackPane();
        showAllCourses.setMinHeight(400);
        showAllCourses.setMinWidth(400);
        showAllCourses.setStyle("-fx-background-color: grey;");
        
        // Create StackPane to show single course information.
        StackPane showCourseInfo = new StackPane();
        showCourseInfo.setMinSize(300, 400);
        showAllCourses.setStyle("-fx-background-color: grey;");

        group.getChildren().addAll(showAllCourses, showCourseInfo, backButton);

        settings = new settingsDialog(this.stage, scene1);
        this.stage.setTitle("SISU");
        this.stage.setScene(settings.getScene());

        backButton.setOnAction((event) -> {
            this.stage.setScene(settings.getScene());
        });

        // Add a listener to show that modules courses.
        degreeProgram.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            treeItems thisNode = newValue.getValue();
            // Check if the clicked item in the TreeView is a module and not a course.
            if (thisNode.getCourseSubModules()) {
                showAllCourses.getChildren().clear();
                ListView<Button> thisCourseInfo = showModuleCourses.display(newValue.getValue().getThisModule().getWhenSubModuleAreCourses(), showCourseInfo);
                showAllCourses.getChildren().add(thisCourseInfo);
            }
        });

        mainStage.sceneProperty().addListener((observable, oldScene, newScene) -> {


            if ( mainStage.getScene() == settings.getScene() ){
                return;
            }


            // get infomation of the selected degree programme or study module 
            selected = settings.getSelectedData();

            String degreeProgrammeId = selected.getDegreeProgrammeId();


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