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
    public void testGetContent() {
        CourseData tested = new CourseData(
                            new networkHandler().getCourseByGroupId(
                                "tut-cu-g-45460"));
        String expectedEn = "<h5>Core content</h5><ul><li>Electrical  quantities and units, the concept of analog signal: Direct and  alternating current and voltage. Ohm&#39;s law. Active and reactive power. Decibel.</li><li>Components and circuits:<br />Resistor, capacitor and inductor. Direct and alternating current circuits (Kirchhoff&#39;s laws, voltage and current division).</li><li>Laboratory equipment and measurements. Use of simulation software.</li><li>Frequency domain analysis, the concept of spectrum. Transfer function and filter.</li><li>Sampling: A/D and D/A converters, sampling theorem.</li></ul><h5>Complementary knowledge</h5><ul><li>Parasitic circuit elements.</li><li>Grounding and the properties of metallic cables.</li><li>Fourier series and Fourier transform. Filter implementations.</li><li>Digital filters. Convolution.</li></ul><h5>Specialist knowledge</h5><ul><li>Diode, operational amplifier.</li></ul>";
        String actualEn = tested.getContent().get("en");
        assertEquals(expectedEn, actualEn);
        String expectedFi = "<h5>Ydinsisältö</h5><ul><li>Suureet ja yksiköt, analogisen signaalin käsite:<br />Tasa- ja vaihtovirta ja -jännite. Ohmin laki. Pätö- ja loisteho. Desibeli.</li><li>Komponentit ja virtapiirit:<br />Vastus, kela ja kondensaattori sekä tasa- ja vaihtovirtapiirit (Kirchhoffin lait, jännitteen- ja virranjako).</li><li>Laboratoriolaitteet ja mittaaminen. Simulointiohjelman käyttö.</li><li>Taajuustason analyysi, spektrin käsite. Siirtofunktio ja suodin.</li><li>Näytteenotto: AD- ja DA-muuntimet, näytteenottoteoreema.</li></ul><h5>Täydentävä tietämys</h5><ul><li>Parasiittiset piirielementit.</li><li>Maadoitukset ja metallijohtojen ominaisuudet.</li><li>Fourier-sarja ja -muunnos. Suodintoteutukset.</li><li>Digitaaliset suotimet. Konvoluutio.</li></ul><h5>Erityistietämys</h5><ul><li>Diodi, operaatiovahvistin.</li></ul>";
        String actualFi = tested.getContent().get("fi");
        assertEquals(expectedFi, actualFi);
    }

    @Test
    public void testGetAdditional() {
        
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

    @Test
    public void testFileWithoutContentUsingGroupId() {
        CourseData tested = new CourseData(
                new networkHandler().getCourseByGroupId(
                "4535973c-4aa6-4f3f-b560-38e3cbc3baaf"));

                String actual = tested.getGroupId();
                assertEquals(null, actual);

                String expected = "0";
                actual = tested.getCredits();
                assertEquals(expected, actual);

                expected = "No code";
                actual = tested.getCode();
                assertEquals(expected, actual);
        
                String expectedEn = "No name";
                String actualEn = tested.getName().get("en");
                String expectedFi = "No name";
                String actualFi = tested.getName().get("fi");
                assertEquals(expectedEn, actualEn);
                assertEquals(expectedFi, actualFi);

                expectedEn = "No outcomes text";
                actualEn = tested.getOutcomes().get("en");
                expectedFi = "No outcomes text";
                actualFi = tested.getOutcomes().get("fi");
                assertEquals(expectedEn, actualEn);
                assertEquals(expectedFi, actualFi);
                
                expectedEn = "No additional text";
                actualEn = tested.getAdditional().get("en");
                expectedFi = "No additional text";
                actualFi = tested.getAdditional().get("fi");
                assertEquals(expectedEn, actualEn);
                assertEquals(expectedFi, actualFi);
                
                expectedEn = "No content text";
                actualEn = tested.getContent().get("en");
                expectedFi = "No content text";
                actualFi = tested.getContent().get("fi");
                assertEquals(expectedEn, actualEn);
                assertEquals(expectedFi, actualFi);
    }
}
