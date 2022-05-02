package fi.tuni.prog3.sisu;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Class representing mainscene which is opened after settings scene. 
 */
public class mainScene implements Runnable {
    
    private Stage stage;
    private TreeView<treeItems> degreeProgram =new TreeView<treeItems>();
    private settingsDialog.selectedData selected;
    private settingsDialog settings;

    /**
     * This is used to get all modules and courses under the selected degree in settingsDialog and added to treeview
     * @param selected Contains all the information user gives in settingsDialog (name, studentnumber and degree or study module)
     * @param isDegree Boolean value to inform if the selected is degree programme or study module
     */
    private void addModuleData(settingsDialog.selectedData selected, boolean isDegree) {

        TreeItem<treeItems> rootItem = new TreeItem<treeItems>();
        degreeProgram.setRoot(rootItem);
            
        ModuleData module;
        DegreeProgrammeData degreedata;

        // If degree, use studyModuleId
        if ( isDegree ){
            module = new ModuleData(
                new networkHandler().getModuleByGroupId(selected.getStudyModuleId()));
        
    
            degreedata = new DegreeProgrammeData(
                new networkHandler().getModuleByGroupId(selected.getStudyModuleId()));
        }

        // If selected is a module, there is a degree above. Use degreeprogrammeId
        else {
            module = new ModuleData(
                new networkHandler().getModuleByGroupId(selected.getDegreeProgrammeId()));
        
    
            degreedata = new DegreeProgrammeData(
                new networkHandler().getModuleByGroupId(selected.getDegreeProgrammeId()));
        }
        
        // Only modules under, get modules and add to treeview
        if ( module.getWhenSubModuleAreCourses().isEmpty() ) {
            TreeMap<String, ModuleData> modules = module.getWhenSubModuleAreModules();
            rootItem.setValue(new treeItems(module.getName().get("fi"), module.getId(), false, false, false));    

            addSubModules(rootItem, modules);

        } 

        // Only courses under, add to treeview
        else {
            rootItem.setValue(new treeItems(degreedata.getName().get("fi"), selected.getStudyModuleId(), false, true, true, module));

            getSubCourses(rootItem, module.getWhenSubModuleAreCourses());
        }
    }

    /**
     * Recursive function to get all submodules and courses to treeview
     * @param root upper branch in treeview, store all modules under this
     * @param modules all modules found under root
     */
    private void addSubModules(TreeItem<treeItems> root, TreeMap<String, ModuleData> modules) {

        for ( var module : modules.values() ){
            TreeItem<treeItems> branch = new TreeItem<treeItems>();

            // No submodules or courses under module
            if ( module.getWhenSubModuleAreModules().isEmpty() && module.getWhenSubModuleAreCourses().isEmpty() ) {
                branch.setValue(new treeItems(module.getName().get("fi"), module.getId(), true, true, false));
                //root.getChildren().add(branch);
            }
            // Both submodues and courses under branch.
            else if (!module.getWhenSubModuleAreModules().isEmpty() && !module.getWhenSubModuleAreCourses().isEmpty()) {
                //branch.setValue(new treeItems(module.getName().get("fi"), module.getId(), false, false, false));
                branch.setValue(new treeItems(module.getName().get("fi"), module.getId(), false, false, true, module));
                root.getChildren().add(branch);

                addSubModules(branch, module.getWhenSubModuleAreModules());

                //getSubCourses(branch, module.getWhenSubModuleAreCourses());
            }

            // Only submodules under branch
            else if ( module.getWhenSubModuleAreCourses().isEmpty() ) {
                branch.setValue(new treeItems(module.getName().get("fi"), module.getId(), false, false, false));
                root.getChildren().add(branch);

                addSubModules(branch, module.getWhenSubModuleAreModules()); 
            }

            // Only courses under branch
            else if ( module.getWhenSubModuleAreModules().isEmpty() ) {
                branch.setValue(new treeItems(module.getName().get("fi"), module.getId(), false, false, true, module));
                root.getChildren().add(branch);

                //getSubCourses(branch, module.getWhenSubModuleAreCourses());
            }
        }
    }

    /**
     * Stores all subcourses under branch
     * @param root branch that has the courses under it
     * @param courses courses under root branch
     */
    private void getSubCourses(TreeItem<treeItems> root, TreeMap<String, CourseData> courses) {

        for ( var course : courses.values() ){
            TreeItem<treeItems> branch = new TreeItem<treeItems>();

            branch.setValue(new treeItems(course.getName().get("fi"), course.getCode(), true, true, false));
            root.getChildren().add(branch);
        }
    }

    /**
     * Constructor for mainscene
     * @param mainStage Both scenes are used in this stage
     */
    public mainScene(Stage mainStage){
        TreeSet<String> listOfCompletedCourses = new TreeSet<>();
        degreeProgram.setMinWidth(400);

        this.stage = mainStage;
        Thread fillTreeView = new Thread(this);
        fillTreeView.start();
        HBox group = new HBox(degreeProgram);
        group.setMinHeight(400);
        group.setMinWidth(1000);

        VBox buttonBox = new VBox();
        buttonBox.setMinWidth(100);

        Button backButton = new Button();
        backButton.setPrefSize(100, 50);
        backButton.setText("back to settings");
        Scene scene1 = new Scene(group);

        Button exitButton = new Button("Quit");
        exitButton.setPrefSize(100, 50);
        buttonBox.getChildren().addAll(backButton, exitButton);

        // Create StackPane to show buttons of courses.
        StackPane showAllCourses = new StackPane();
        showAllCourses.setMinHeight(400);
        showAllCourses.setMinWidth(400);
        showAllCourses.setStyle("-fx-background-color: grey;");
        
        // Create StackPane to show single course information.
        StackPane showCourseInfo = new StackPane();
        showCourseInfo.setMinSize(300, 400);
        showAllCourses.setStyle("-fx-background-color: grey;");

        group.getChildren().addAll(showAllCourses, showCourseInfo, buttonBox);

        // Create settingsDialog and set it to stage
        settings = new settingsDialog(this.stage, scene1);
        this.stage.setTitle("SISU");
        this.stage.setScene(settings.getScene());

        // Move back to settingsDialog
        backButton.setOnAction((event) -> {
            // Clear everything
            listOfCompletedCourses.clear();
            degreeProgram.setRoot(null);
            showAllCourses.getChildren().clear();
            showCourseInfo.getChildren().clear();
            this.stage.setScene(settings.getScene());
        });

        exitButton.setOnAction(event -> {
            ToJsonFileClass temp;
            List<String> tempList = new ArrayList<>(listOfCompletedCourses);
            if (selected.getDegreeProgrammeName().equals("No DegreeProgramme")) {
                temp = new ToJsonFileClass(selected.getStudName(), selected.getStudNumber(), 
                selected.getStudyModuleName(), tempList);
            } else {
                temp = new ToJsonFileClass(selected.getStudName(), selected.getStudNumber(), 
                selected.getDegreeProgrammeName(), tempList);
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try {
                String studentName = selected.getStudName().replaceAll(" ", "_");
                FileWriter writer = new FileWriter(new File(studentName+"_courses.json"));
                gson.toJson(temp, writer);
                writer.flush();
                writer.close();
            } catch (IOException e){
                System.out.println(e);
            }
            Platform.exit();
        });
        
        // Add a listener to show that modules courses.
        degreeProgram.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            
            if (newValue != null) {
                treeItems thisNode = newValue.getValue();
                // Check if the clicked item in the TreeView is a module and not a course.
                if (thisNode.getCourseSubModules()) {
                    showAllCourses.getChildren().clear();
                    ListView<CheckBox> thisCourseInfo = new showModuleCourses().display(newValue, newValue.getValue().getThisModule().getWhenSubModuleAreCourses(), showCourseInfo, listOfCompletedCourses);
                    showAllCourses.getChildren().add(thisCourseInfo);
                } else {
                    showAllCourses.getChildren().clear();
                }
            }
        });

        // Listener for scene change. If moved from settingsDialog to mainScene, update treeview
        

    

    this.stage.show();
    }

    @Override
    public void run() {
        this.stage.sceneProperty().addListener((observable, oldScene, newScene) -> {

            if ( this.stage.getScene() == settings.getScene() ){
                return;
            }

            // get infomation of the selected degree programme or study module 
            selected = settings.getSelectedData();
            String degreeProgrammeId = selected.getDegreeProgrammeId();

            // Selected is a degree
            if ( degreeProgrammeId.equals("No DegreeProgramme") ) {

                addModuleData(selected, true); 
            }

            // Selected is a module
            else {
                addModuleData(selected, false);
            } 
    });
        
    }
}