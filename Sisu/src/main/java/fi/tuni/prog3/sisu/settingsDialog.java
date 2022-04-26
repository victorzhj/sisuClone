package fi.tuni.prog3.sisu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;


/**
 * Class that represents the start menu of the Sisu application
 */
public class settingsDialog extends Application implements Runnable{
    private TreeView<treeItems> degreeProgramsList =new TreeView<treeItems>();
    private networkHandler networker = new networkHandler();
    private Button confirmButton = new Button();
    private TextField nameField = new TextField();
    private TextField studentNumber = new TextField();
    private GridPane settingsGrid = new GridPane();
    private ProgressBar loadingBar = new ProgressBar();
    private Label errorLabel = new Label();
    double downloadProgress = 0;

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
    /**
     * Data structure class that is passed to main window
     */
    public class toReturnData{
        private String studNumber;
        private String studName;
        private String degreeProgrammeName;
        private String degreeProgrammeId;
        private String studyModuleName;
        private String studyModuleId;
        /**
         * Default Constructor to generate the structure
         * @param studName StudentName
         * @param studNumber StudentNumber
         * @param degreeProgrammeName
         * @param degreeProgrammeId
         * @param studyModuleName
         * @param studyModuleId
         */
        private toReturnData(String studName, String studNumber, String degreeProgrammeName,
         String degreeProgrammeId, String studyModuleName, String studyModuleId){

            this.studName = studName;
            this.studNumber = studNumber;
            this.degreeProgrammeId = degreeProgrammeId;
            this.degreeProgrammeName = degreeProgrammeName;
            this.studyModuleId = studyModuleId;
            this.studyModuleName = studyModuleName;
        }
        /**
         * Get DegreeProgrammeId
         * @return String DegreeProgrammeId
         */
        public String getDegreeProgrammeId() {
            return degreeProgrammeId;
        }
        /**
         * Get DegreeProgrammeName
         * @return String DegreeProgrammeName
         */
        public String getDegreeProgrammeName() {
            return degreeProgrammeName;
        }
        /**
         * Get StudentName
         * @return String StudentName
         */
        public String getStudName() {
            return studName;
        }
        /**
         * Get StudentNumber
         * @return String StudentNumber
         */
        public String getStudNumber() {
            return studNumber;
        }
        /**
         * Get StudyModuleId
         * @return String StudyModuleId
         */
        public String getStudyModuleId() {
            return studyModuleId;
        }
        /**
         * Get StudyModuleName
         * @return String StudyModuleName
         */
        public String getStudyModuleName() {
            return studyModuleName;
        }
        
    }

    
    /** 
     * Starts main event loop for the window and initializes all the components
     * @param mainStage stage to attach to
     */
    @Override
    public void start(Stage mainStage){

        Thread fillTreeView = new Thread(this);
        fillTreeView.start();

        nameField.setPrefSize(200, 50);
        confirmButton.setPrefSize(100, 50);
        studentNumber.setMinSize(200, 50);
        degreeProgramsList.setPrefSize(400, 200);
        loadingBar.setPrefWidth(400);
        studentNumber.setText("Enter student number");
        nameField.setText("Enter name");
        confirmButton.setText("Confirm");
        settingsGrid.add(nameField, 0, 0, 1, 1);
        settingsGrid.add(studentNumber, 0, 1,1, 1);
        settingsGrid.add(degreeProgramsList, 2, 0, 1, 3);
        settingsGrid.add(confirmButton, 0,2, 1 ,1);
        settingsGrid.add(loadingBar, 2, 4, 3, 1);
        confirmButton.setAlignment(Pos.CENTER);
        var scene = new Scene(settingsGrid);
        mainStage.setScene(scene);
        mainStage.show();
        
        //Button handler event. Reads data in every field and generates toReturnData that will be passed to other parts of the program
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                toReturnData data;
                if(degreeProgramsList.getSelectionModel().isEmpty()){
                    return;
                }
                TreeItem<treeItems> degreeProgramme = degreeProgramsList.getSelectionModel().getSelectedItem().getParent();
                TreeItem<treeItems> studyModule = degreeProgramsList.getSelectionModel().getSelectedItem();
                if(!studyModule.getValue().getIsLeaf() && !studyModule.getValue().hasNoStudyModules){
                    errorLabel.setText("Please choose a Study plan");
                    settingsGrid.add(errorLabel, 0, 3,1,1);
                    return;
                }
                else if (studyModule.getValue().gethasNoStudyModules() && !studyModule.getValue().getIsLeaf()){

                    data= new toReturnData(nameField.getText(), studentNumber.getText(), "No DegreeProgramme",
                    "No DegreeProgramme", studyModule.getValue().getName(), studyModule.getValue().getId());
                }
                else{
                    data= new toReturnData(nameField.getText(), studentNumber.getText(), degreeProgramme.getValue().getName(),
                    degreeProgramme.getValue().getId(), studyModule.getValue().getName(), studyModule.getValue().getId());
                }
                
                
                //TODO add function to call where the data should be stored
                /*storeData(data)*/
            }
        });
        
        }
        
        
        

    
    /** 
     * This will be moved to main screen later
     * @param args
     */
    public static void main(String[] args){
        launch();
    }
    //Fills the treeview in separate thread so the gui wont block
    @Override
    public void run() {
        TreeItem<treeItems> rootItem = new TreeItem<treeItems>();
        degreeProgramsList.setRoot(rootItem);
        rootItem.setValue(new treeItems("DegreeProgrammes", "NO-ID", false, false));
        var degrees = DegreesData.getDegreesInformation();
        var maxProgress = degrees.values().size();
        System.out.println(maxProgress);
        for(var degree : degrees.values()){
            TreeItem<treeItems> branch = new TreeItem<treeItems>();
            
            DegreeProgrammeData degreeProgram = new DegreeProgrammeData(networker.getModuleByGroupId(degree.get("groupId")));
            branch.setValue(new treeItems(degree.get("name"), degree.get("groupId"), false, false));
            if(degreeProgram.getFieldOfStudy().values().size() == 0){
                branch.setValue(new treeItems(degree.get("name"), degree.get("groupId"), false, true));
            }
            for(var testi : degreeProgram.getFieldOfStudy().values()){
                System.out.println(testi.getId());
                TreeItem<treeItems> subBranch = new TreeItem<treeItems>();
                if(testi.getName().get("fi").equals("No name")){
                    subBranch.setValue(new treeItems(testi.getName().get("en"), testi.getId(), true, true));
                }
                else{
                    subBranch.setValue(new treeItems(testi.getName().get("fi"), testi.getId(), true, true));
                }
                branch.getChildren().add(subBranch);
            }
            rootItem.getChildren().add(branch);
        downloadProgress++;
        Platform.runLater(new Runnable() {
            @Override public void run(){
                loadingBar.setProgress(downloadProgress/maxProgress);
            }
        });
        }
    }
}