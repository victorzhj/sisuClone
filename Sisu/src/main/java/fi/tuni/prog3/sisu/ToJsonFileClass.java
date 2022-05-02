package fi.tuni.prog3.sisu;

import java.util.List;

/**
 * Class representation of data that needs to be saved as JSON
 */
public class ToJsonFileClass {
    public String studentName;
    public String studentNumber;
    public String degreeGroupId;
    public String type;
    public List<String> coursesGroupIds; 

    /**
     * Constructor for getting all the needed data.
     * @param studentName String student name.
     * @param studentNumber String student number.
     * @param degreeGroupId String degree groupId.
     * @param completedCourses List<String> list of completed courses groupIds.
     * @param type String the type of the program. Can be either "studyModule" or "degreeProgramme".
     */
    public ToJsonFileClass(String studentName, String studentNumber, String degreeGroupId, List<String> completedCourses, String type) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.degreeGroupId = degreeGroupId;
        this.type = type;
        this.coursesGroupIds = completedCourses;
    }

    /**
     * Return the student name.
     * @return String the student name.
     */
    public String getStudentName() {
        return this.studentName;
    }

    /**
     * Return the student number.
     * @return String the student number.
     */
    public String getStudentNumber() {
        return this.studentNumber;
    }

    /**
     * Return the degree/studyModule groupId.
     * @return String the degree/studyModule groupId.
     */
    public String getDegreeGroupId() {
        return this.degreeGroupId;
    }

    /**
     * Return the type. Can be either "studyModule" or "degreeProgramme".
     * @return String the type. Can be either "studyModule" or "degreeProgramme".
     */
    public String getType() {
        return this.type;
    }

    /**
     * Return a list that contains completed courses groupIds.
     * @return List<String> a list that contains completed courses groupIds.
     */
    public List<String> getCoursesGroupIds() {
        return this.coursesGroupIds;
    }
}
