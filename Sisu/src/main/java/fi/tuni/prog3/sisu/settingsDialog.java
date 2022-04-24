package fi.tuni.prog3.sisu;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Dialog;
import java.util.TreeMap;
public class settingsDialog extends Application{
    private TreeView<treeItems> degreeProgramsList =new TreeView<treeItems>();
    private networkHandler networker = new networkHandler();
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
        TreeItem<treeItems> rootItem = new TreeItem<treeItems>();
        rootItem.setValue(new treeItems("DegreeProgrammes", "NO-ID"));
        degreeProgramsList.setRoot(rootItem);
        for(var degreeProgram : DegreesData.getDegreesInformation().values()){
            TreeItem<treeItems> branch = new TreeItem<treeItems>();
            branch.setValue(new treeItems(degreeProgram.get("name"), degreeProgram.get("groupId")));
            System.out.println(degreeProgram.get("groupId"));
            //System.out.println(networker.getModuleByGroupId(degreeProgram.get("groupId"))[0]);
            ModuleData studyModules = new ModuleData(networker.getModuleByGroupId(degreeProgram.get("groupId")));
            for(ModuleData module : studyModules.getWhenSubModuleAreModules().values()){
                TreeItem<treeItems> subBranch = new TreeItem<treeItems>();
                subBranch.setValue(new treeItems(module.getName().get("fi"), module.getId()));
            }

        }
        var scene = new Scene(degreeProgramsList);
        mainStage.setScene(scene);
        mainStage.show();
        
    }

    public static void main(String[] args){
        launch();
    }
}
