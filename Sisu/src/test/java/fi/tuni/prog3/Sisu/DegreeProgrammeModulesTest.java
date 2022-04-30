package fi.tuni.prog3.Sisu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import fi.tuni.prog3.sisu.DegreeProgrammeModules;
import fi.tuni.prog3.sisu.networkHandler;

public class DegreeProgrammeModulesTest {
    @Test
    public void testGetGroupId() {
        DegreeProgrammeModules tested = new DegreeProgrammeModules(
                new networkHandler().getModuleById(
                "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
        String expected = "tut-sm-g-3662";
        String actual = tested.getGroupId();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetId() {
        DegreeProgrammeModules tested = new DegreeProgrammeModules(
                new networkHandler().getModuleById(
                "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
        String expected = "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf";
        String actual = tested.getId();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetName() {
        DegreeProgrammeModules tested = new DegreeProgrammeModules(
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
    public void testFileWithoutContentUsingGroupId() {
        DegreeProgrammeModules tested = new DegreeProgrammeModules(
                new networkHandler().getModuleById(
                "4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
                String expected = "No Id";
                String actual = tested.getId();
                assertEquals(expected, actual);
        
                expected = "No groupId";
                actual = tested.getGroupId();
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
        DegreeProgrammeModules tested = new DegreeProgrammeModules(
                new networkHandler().getModuleById(
                "4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));
                String expected = "No Id";
                String actual = tested.getId();
                assertEquals(expected, actual);
        
                expected = "No groupId";
                actual = tested.getGroupId();
                assertEquals(expected, actual);
        
                String expectedEn = "No name";
                String actualEn = tested.getName().get("en");
                String expectedFi = "No name";
                String actualFi = tested.getName().get("fi");
                assertEquals(expectedEn, actualEn);
                assertEquals(expectedFi, actualFi);
    }
}
