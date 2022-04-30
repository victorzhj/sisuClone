package fi.tuni.prog3.Sisu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.TreeMap;


import org.junit.jupiter.api.Test;

import fi.tuni.prog3.sisu.DegreeProgrammeData;
import fi.tuni.prog3.sisu.DegreeProgrammeModules;
import fi.tuni.prog3.sisu.networkHandler;

public class DegreeProgrammeDataTest {
    @Test
    public void testGetGroupId() {
        DegreeProgrammeData tested = new DegreeProgrammeData(
                                     new networkHandler().getModuleByGroupId(
                                        "otm-87fb9507-a6dd-41aa-b924-2f15eca3b7ae"));
        String expected = "otm-87fb9507-a6dd-41aa-b924-2f15eca3b7ae";
        String actual = tested.getGroupId();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetId() {
        DegreeProgrammeData tested = new DegreeProgrammeData(
                                     new networkHandler().getModuleById(
                                        "otm-d2728f44-3e53-4bad-84c4-dbd257ac0f34"));
        String expected = "otm-d2728f44-3e53-4bad-84c4-dbd257ac0f34";
        String actual = tested.getId();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetModulesAndGetFieldOfStudy() {
        DegreeProgrammeData tested1 = new DegreeProgrammeData(
                                     new networkHandler().getModuleById(
                                        "otm-df83fbbd-f82d-4fda-b819-78f6b2077fcb"));
        TreeMap<String, DegreeProgrammeModules> testTree1 = tested1.getModules();
        assertTrue(testTree1.isEmpty());
        TreeMap<String, DegreeProgrammeModules> testTree12 = tested1.getFieldOfStudy();
        String fieldOfStudyActual1 = testTree12.get("otm-640dcf49-18b4-4392-8226-8cc18ea32dfb").getName().get("en");
        assertEquals("Natural Sciences and Mathematics", fieldOfStudyActual1);

        DegreeProgrammeData tested2 = new DegreeProgrammeData(
                                     new networkHandler().getModuleById(
                                        "otm-d2728f44-3e53-4bad-84c4-dbd257ac0f34"));
        TreeMap<String, DegreeProgrammeModules> testTree21 = tested2.getFieldOfStudy();
        assertTrue(testTree21.isEmpty());
        TreeMap<String, DegreeProgrammeModules> testTree22 = tested2.getModules();
        String moduleActual = testTree22.get("tut-sm-g-7209").getName().get("en");
        assertEquals("Joint Studies in Architecture, BSc", moduleActual);

    }

    @Test
    public void testGetName() {
        DegreeProgrammeData tested = new DegreeProgrammeData(
                                     new networkHandler().getModuleById(
                                        "otm-87fb9507-a6dd-41aa-b924-2f15eca3b7ae"));
        String expectedEn = "Specialty Training in Emergency Medicine (55/2020)";
        String actualEn = tested.getName().get("en");
        String expectedFi = "Akuuttilääketieteen erikoislääkärikoulutus (55/2020)";
        String actualFi = tested.getName().get("fi");
        assertEquals(expectedEn, actualEn);
        assertEquals(expectedFi, actualFi);
    }

    @Test
    public void testFileWithoutContentUsingGroupId() {
        DegreeProgrammeData tested = new DegreeProgrammeData(
                                     new networkHandler().getModuleByGroupId(
                                        "-87fb9507-a6dd-41aa-b924-2f15eca3b7ae"));
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
    public void testFileWithoutContentUsinId() {
        DegreeProgrammeData tested = new DegreeProgrammeData(
                                     new networkHandler().getModuleById(
                                        "-87fb9507-a6dd-41aa-b924-2f15eca3b7ae"));
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