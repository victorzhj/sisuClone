package fi.tuni.prog3.sisu;

import java.util.TreeMap;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.StackPane;
/**
 * This class makes a checkbox of all the courses that are in a certain module.
 */
public class showModuleCourses {
    //private final Node rootIcon = 
    //    new ImageView(new Image(getClass().getResourceAsStream("check.png")));
    
    /**
     * Display all the course checkboxes of the module.
     * @param courses Modules list of courses.
     * @param courseInfoPane The StackPane where the checkboxes are displayed.
     * @return ListView<Button> Containing all the checkboxes.
     */
    public ListView<CheckBox> display(TreeItem<treeItems> subroot, TreeMap<String, CourseData> courses, StackPane courseInfoPane) {
        ListView<CheckBox> allCourses = new ListView<CheckBox>();
        

        for (var value : courses.values()) {
            CheckBox course = new CheckBox();
            course.setMinWidth(300);
            if (value.getName().get("fi").equals("No name")) {
                course.setText(value.getName().get("fi"));
            } else {
                course.setText(value.getName().get("en"));
            }
            allCourses.getItems().add(course);
            
            course.setOnMouseEntered(event -> {
                courseInfoPane.getChildren().clear();
                showCourseInfoClass.display(value, courseInfoPane);
            });

            treeItems temp = new treeItems(value.getName().get("fi"), value.getGroupId(), true, false, false);
            TreeItem<treeItems> branch = new TreeItem<treeItems>(temp);
            course.setOnMouseClicked(event -> {
                
                if (course.isSelected()) {
                    subroot.getChildren().add(branch); 
                } else if (!course.isSelected()) {
                    subroot.getChildren().remove(branch);
                }
            });
        }
        return allCourses;
    }
}
