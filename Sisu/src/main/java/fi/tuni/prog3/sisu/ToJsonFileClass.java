package fi.tuni.prog3.sisu;

import java.util.List;

/**
 * Class representation of data that needs to be saved as JSON
 */
public class ToJsonFileClass {
    public String studentName;
    public String studentNumber;
    public String degreeGroupId;
    public List<String> coursesGroupIds; 

    /**
     * Constructor for getting all the needed data.
     * @param studentName String student name.
     * @param studentNumber String student number.
     * @param degreeGroupId String degree groupId.
     * @param completedCourses List<String> list of completed courses groupIds.
     */
    public ToJsonFileClass(String studentName, String studentNumber, String degreeGroupId, List<String> completedCourses) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.degreeGroupId = degreeGroupId;
        this.coursesGroupIds = completedCourses;
    }
}
