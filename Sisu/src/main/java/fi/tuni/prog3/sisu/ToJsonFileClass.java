package fi.tuni.prog3.sisu;

import java.util.List;

public class ToJsonFileClass {
    public String studentName;
    public String studentNumber;
    public String degreeGroupId;
    public List<String> coursesGroupIds; 

    public ToJsonFileClass(String studentName, String studentNumber, String degreeGroupId, List<String> completedCourses) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.degreeGroupId = degreeGroupId;
        this.coursesGroupIds = completedCourses;
    }
}
