package fi.tuni.prog3.sisu;

import java.util.TreeMap;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class showModuleCourses {
    public static ListView<Button> display(TreeMap<String, CourseData> courses, StackPane courseInfoPane) {
        ListView<Button> allCourses = new ListView<Button>();

        for (var value : courses.values()) {
            Button course = new Button();
            course.setMinWidth(300);
            if (value.getName().get("fi").equals("Ei nimeä")) {
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
