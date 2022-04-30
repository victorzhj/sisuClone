package fi.tuni.prog3.sisu;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class showCourseInfoClass {
    public static void display(CourseData course, StackPane pane) {
        VBox courseInfo = new VBox();
        courseInfo.setMaxWidth(350);

        Label nameLabel = new Label();
        Label creditsLabel = new Label("Credits amount: " + course.getCredits());

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        String content;
        String outcome;
        String additional;
        if (course.getName().get("fi").equals("Ei nimeä")) {
            nameLabel.setText(course.getName().get("en"));
        } else {
            nameLabel.setText(course.getName().get("fi"));
        }
        if (course.getContent().get("fi").equals("Ei sisältö tekstiä")) {
            content = course.getContent().get("en");
        } else {
            content = course.getContent().get("fi");
        }
        if (course.getOutcomes().get("fi").equals("Ei tulos tekstiä")) {
            outcome = course.getOutcomes().get("en");
        } else {
            outcome = course.getOutcomes().get("fi");
        }
        if (course.getAdditional().get("fi").equals("Ei lisättävää tekstiä")) {
            additional = course.getAdditional().get("en");
        } else {
            additional = course.getAdditional().get("fi");
        }
        webEngine.loadContent("<div> <h2>" + "Content </h2>" + content + "</div" + 
                                  "<div> <h2>" + "Outcomes </h2>" + outcome + "</div" + 
                                  "<div> <h2>" + "Additional </h2>" + additional + "</div");
        
        courseInfo.getChildren().addAll(nameLabel, creditsLabel, browser);
        pane.getChildren().add(courseInfo);
    }
}
