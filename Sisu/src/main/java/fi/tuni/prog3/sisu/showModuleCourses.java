package fi.tuni.prog3.sisu;

import java.util.TreeMap;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

/**
 * This class makes a button of all the courses that are in a certain module.
 */
public class showModuleCourses {
    /**
     * Display all the course buttons of the module.
     * @param courses Modules list of courses.
     * @param courseInfoPane The StackPane where the buttons are displayed.
     * @return ListView<Button> Containing all the buttons.
     */
    public static ListView<Button> display(TreeMap<String, CourseData> courses, StackPane courseInfoPane) {
        ListView<Button> allCourses = new ListView<Button>();

        for (var value : courses.values()) {
            Button course = new Button();
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
        }
        return allCourses;
    }
}
