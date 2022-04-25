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

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        List<String> ids = new ArrayList<String>();

        for(var id : DegreesData.getDegreesInformation().values()){
            ids.add(id.get("groupId"));
        }
        var studyModules = networker.asynctesti(ids);

        for(var degreeProgram : DegreesData.getDegreesInformation().values()){
            TreeItem<treeItems> branch = new TreeItem<treeItems>();
            branch.setValue(new treeItems(degreeProgram.get("name"), degreeProgram.get("groupId")));
            System.out.println(degreeProgram.get("groupId"));
            //System.out.println(networker.getModuleByGroupId(degreeProgram.get("groupId"))[0]);
            for(var module : studyModules){
                String[] body = {module.body(), "0"};
                DegreeProgrammeModules testiModuulit = new DegreeProgrammeModules(body);
                
                    TreeItem<treeItems> subBranch = new TreeItem<treeItems>();
                    subBranch.setValue(new treeItems(testiModuulit.getName().get("fi"), testiModuulit.getId()));
                    branch.getChildren().add(subBranch);
                
            }
            

            

            
            rootItem.getChildren().add(branch);

        }
        var scene = new Scene(degreeProgramsList);
        mainStage.setScene(scene);
        mainStage.show();
        
    }

    public static void main(String[] args){
        launch();
    }
}
