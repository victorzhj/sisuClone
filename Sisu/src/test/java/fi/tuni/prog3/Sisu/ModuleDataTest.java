package fi.tuni.prog3.Sisu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fi.tuni.prog3.sisu.ModuleData;
import fi.tuni.prog3.sisu.networkHandler;

public class ModuleDataTest {
    @Test
    public void testGetId() {
        ModuleData tested = new ModuleData(
                new networkHandler().getModuleById(
                "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
        String expected = "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf";
        String actual = tested.getId();
        assertEquals(expected, actual);

    }

    @Test
    public void testGetGroupId() {
        ModuleData tested = new ModuleData(
                new networkHandler().getModuleById(
                "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
        String expected = "tut-sm-g-3662";
        String actual = tested.getGroupId();
        assertEquals(expected, actual);
    }
    

    @Test
    public void testGetModuleType() {
        ModuleData tested1 = new ModuleData(
                new networkHandler().getModuleById(
                "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
        String expected1 = "StudyModule";
        String actual1 = tested1.getModuleType();
        assertEquals(expected1, actual1);

        ModuleData tested2 = new ModuleData(
                new networkHandler().getModuleById(
                "otm-f5025fb3-8266-4e23-bdc3-d401973ce588"));
        String expected2 = "GroupingModule";
        String actual2 = tested2.getModuleType();
        assertEquals(expected2, actual2);
    }

    @Test
    public void testGetName() {
        ModuleData tested = new ModuleData(
                new networkHandler().getModuleById(
                "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
        String expectedEn = "Joint Studies in Information Technology";
        String actualEn = tested.getName().get("en");
        String expectedFi = "Tietotekniikan yhteiset opinnot";
        String actualFi = tested.getName().get("fi");
        assertEquals(expectedEn, actualEn);
        assertEquals(expectedFi, actualFi);
    }

    @Test
    public void testGetTargetCredits() {
        ModuleData tested = new ModuleData(
                new networkHandler().getModuleById(
                "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
        String expected = "15-";
        String actual = tested.getTargetCredits();
        assertEquals(expected, actual);
    }

    // TODO
    @Test
    public void testGetWhenSubModuleAreCourses() {
        ModuleData tested = new ModuleData(
                new networkHandler().getModuleByGroupId(
                "tut-sm-g-7209"));
        String shouldHave = "tut-cu-g-48863";
        assertTrue(tested.getWhenSubModuleAreCourses().containsKey(shouldHave));
        String expected = "Introduction to Architecture Studies I";
        String actual = tested.getWhenSubModuleAreCourses().get("tut-cu-g-48863").getName().get("en");
        assertEquals(expected, actual);
    }

    // TODO
    @Test
    void testGetWhenSubModuleAreModules() {
        ModuleData tested = new ModuleData(
                new networkHandler().getModuleByGroupId(
                "otm-640dcf49-18b4-4392-8226-8cc18ea32dfb"));
        String shouldHave = "otm-51cb3e6b-8fda-47bd-bb90-1b420456d49d";
        assertTrue(tested.getWhenSubModuleAreCourses().containsKey(shouldHave));
        String expected = "Joint Studies in Natural Sciences and Mathematics";
        String actual = tested.getWhenSubModuleAreCourses().get("otm-51cb3e6b-8fda-47bd-bb90-1b420456d49d").getName().get("en");
        assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
        ModuleData tested = new ModuleData(
                new networkHandler().getModuleById(
                "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
        String expected = "StudyModule : tut-sm-g-3662 : Tietotekniikan yhteiset opinnot";
        String actual = tested.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testFileWithoutContentUsingGroupId() {
        ModuleData tested = new ModuleData(
                new networkHandler().getModuleById(
                "4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
                String expected = "No Id";
                String actual = tested.getId();
                assertEquals(expected, actual);
        
                expected = "No groupId";
                actual = tested.getGroupId();
                assertEquals(expected, actual);

                expected = "GroupingModule";
                actual = tested.getModuleType();
                assertEquals(expected, actual);

                expected = "";
                actual = tested.getTargetCredits();
                assertEquals(expected, actual);

                String expectedEn = "No name";
                String actualEn = tested.getName().get("en");
                String expectedFi = "No name";
                String actualFi = tested.getName().get("fi");
                assertEquals(expectedEn, actualEn);
                assertEquals(expectedFi, actualFi);
    }

    @Test
    public void testFileWithoutContentUsingId() {
        ModuleData tested = new ModuleData(
                new networkHandler().getModuleById(
                "4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
                String expected = "No Id";
                String actual = tested.getId();
                assertEquals(expected, actual);
        
                expected = "No groupId";
                actual = tested.getGroupId();
                assertEquals(expected, actual);

                expected = "GroupingModule";
                actual = tested.getModuleType();
                assertEquals(expected, actual);

                expected = "";
                actual = tested.getTargetCredits();
                assertEquals(expected, actual);
        
                String expectedEn = "No name";
                String actualEn = tested.getName().get("en");
                String expectedFi = "No name";
                String actualFi = tested.getName().get("fi");
                assertEquals(expectedEn, actualEn);
                assertEquals(expectedFi, actualFi);
    }
}
