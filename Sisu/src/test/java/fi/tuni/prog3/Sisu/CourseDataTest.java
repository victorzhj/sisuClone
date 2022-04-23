package fi.tuni.prog3.Sisu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fi.tuni.prog3.sisu.CourseData;
import fi.tuni.prog3.sisu.networkHandler;

public class CourseDataTest {

    @Test
    public void testGetCredits() {
        CourseData tested = new CourseData(
                            new networkHandler().getCourseByGroupId(
                                "tut-cu-g-45460"));
        String expected = "5";
        String actual = tested.getCredits();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetGroupId() {
        CourseData tested = new CourseData(
                            new networkHandler().getCourseByGroupId(
                                "tut-cu-g-45460"));
        String expected = "tut-cu-g-45460";
        String actual = tested.getGroupId();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetName() {
        CourseData tested = new CourseData(
                            new networkHandler().getCourseByGroupId(
                                "tut-cu-g-45460"));
        String expectedEn = "Signals and Measurements";
        String actualEn = tested.getName().get("en");
        assertEquals(expectedEn, actualEn);
        String expectedFi = "Signaalit ja mittaaminen";
        String actualFi = tested.getName().get("fi");
        assertEquals(expectedFi, actualFi);
    }

    @Test
    public void testGetOutcomes() {
        CourseData tested = new CourseData(
                            new networkHandler().getCourseByGroupId(
                                "tut-cu-g-45460"));
        String expectedEn = "<p>After completing the course, the student<br />1) understands Ohm&#39;s and Kirchhoff&#39;s laws and is able to analyze simple DC and AC circuits,<br />1) knows measurement principles and error sources,<br />2) is able to use basic laboratory equipment, a simulation software and a data acquisition device,<br />3) is able to measure current and voltage,<br />4) understands the concept of frequency spectrum and is able to measure and interpret it using a measurement device,<br />5) understands the signal sampling principle and the sampling theorem.</p>";
        String actualEn = tested.getOutcomes().get("en");
        assertEquals(expectedEn, actualEn);
        String expectedFi = "Opintojakson suoritettuaan opiskelija<br />1) ymmärtää Ohmin ja Kirchhoffin lait sekä osaa analysoida yksinkertaisia tasa- ja vaihtovirtapiirejä,<br />2) tietää mittaamisen periaatteet ja virhelähteet,<br />3) osaa käyttää laboratorion perusmittalaitteita, simulointiohjelmaa ja tietokoneeseen liitettävää signaalinkeruulaitetta,<br />4) osaa mitata virran ja jännitteen,<br />5) ymmärtää taajuusspektrin käsitteen ja osaa mitata ja tulkita sen mittalaitteella,<br />6) ymmärtää signaalin näytteenoton periaatteen ja näytteenottoteoreeman.";
        String actualFi = tested.getOutcomes().get("fi");
        assertEquals(expectedFi, actualFi);
    }

    @Test
    public void testToString() {
        CourseData tested = new CourseData(
                            new networkHandler().getCourseByGroupId(
                                "tut-cu-g-45460"));
        String expected = "EE.010 : tut-cu-g-45460 : Signaalit ja mittaaminen";
        String actual = tested.toString();
        assertEquals(expected, actual);
    }
}
