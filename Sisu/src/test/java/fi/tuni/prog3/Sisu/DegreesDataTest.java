package fi.tuni.prog3.Sisu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import fi.tuni.prog3.sisu.DegreesData;

public class DegreesDataTest {
    @Test
    public void testGetDegreesInformation() {
        String degreeName = "Tekniikan ja luonnontieteiden tiedekunnan yleinen tohtoriohjelma";
        TreeMap<String, TreeMap<String, String>> tested = DegreesData.getDegreesInformation();
        String extectedName = degreeName;
        String expectedGroupId = "otm-89c48ef2-1fd8-4ce7-a0b2-5688c0b12e50";
        String expectedCredits = "240-";
        String actualName = tested.get(degreeName).get("name");
        String actualGroupId = tested.get(degreeName).get("groupId");
        String actualCredits = tested.get(degreeName).get("credits");
        assertEquals(extectedName, actualName);
        assertEquals(expectedGroupId, actualGroupId);
        assertEquals(expectedCredits, actualCredits);
    }

    @Test
    public void testGetDegreesNames() {
        TreeSet<String> toBeTested = DegreesData.getDegreesNames();
        
        assertEquals("Akuuttilääketieteen erikoislääkärikoulutus (55/2020)", toBeTested.pollFirst());
        assertEquals("Akuuttilääketieteen erikoislääkärikoulutus (56/2015)", toBeTested.pollFirst());
    }
}
