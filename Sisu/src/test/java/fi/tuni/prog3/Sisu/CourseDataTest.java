package fi.tuni.prog3.Sisu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fi.tuni.prog3.sisu.CourseData;
import fi.tuni.prog3.sisu.networkHandler;

public class CourseDataTest {
    @Test
    public void testGetAdditional() {
        CourseData tested = new CourseData(
                            new networkHandler().getCourseByGroupId(
                                "tut-cu-g-45460"));
        String expectedEn = "<p>The course gives an introduction to signals and related concepts. The  main focus is on electrical signals which are essential, e.g., in  electrical engineering, electronics, communications engineering, and  information technology. The introduction to signals happens by doing  practical measurements. At the same time we learn how to use typical  measurement equipment and we get to know the basic electronic  components. The course gives a practical introduction to subjects which  are discussed in more detail and in more theoretical manner in later  courses.</p><p>The course exam implementation type is considered separately for each academic year, so it may change.</p><p>Only one of the courses ELT-10017, ELT-10016, ELT-10002, ELT-10001, ELT-10000 can be included to university degrees.<br /><b>Partial completions of the course must be carried out during the same implementation round.</b></p>";
        String actualEn = tested.getAdditional().get("en");
        String expectedFi = "Kurssilla tutustutaan signaaleihin ja niihin liittyviin käsitteisiin. Pääpaino on sähköisissä signaaleissa, jotka ovat keskeisiä mm. sähkötekniikassa, elektroniikassa, tietoliikennetekniikassa ja tietotekniikassa. Tutustuminen tapahtuu käytännönläheisesti signaaleita mittaamalla. Samalla opitaan mittalaitteiden käyttöä ja tutustutaan tyypillisimpiin elektroniikkakomponentteihin. Kurssi antaa käytännönläheisen johdatuksen asioihin, joita käsitellään myöhemmillä kursseilla syvällisemmin ja teoreettisemmin.<p><br /></p><p>Opintojakson tenttimisen tapaa tarkastellaan lukuvuosittain ja tähän voidaan tehdä muutoksia.</p><p>Opintojaksoista ELT-10002, ELT-10001, ELT-10016, ELT-10017 ja ELT-10000 vain yksi voi sisältyä yliopiston tutkintoihin.</p><b>Osasuoritusten pitää liittyä samaan toteutuskertaan.</b><br />";
        String actualFi = tested.getAdditional().get("fi");
        assertEquals(expectedEn, actualEn);
        assertEquals(expectedFi, actualFi);
    }

    @Test
    public void testGetCode() {
        CourseData tested = new CourseData(
                            new networkHandler().getCourseByGroupId(
                                "tut-cu-g-45460"));
        String expected = "EE.010";
        String actual = tested.getCode();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetContent() {
        CourseData tested = new CourseData(
                            new networkHandler().getCourseByGroupId(
                                "tut-cu-g-45460"));
        String expectedEn = "<h5>Core content</h5><ul><li>Electrical  quantities and units, the concept of analog signal: Direct and  alternating current and voltage. Ohm&#39;s law. Active and reactive power. Decibel.</li><li>Components and circuits:<br />Resistor, capacitor and inductor. Direct and alternating current circuits (Kirchhoff&#39;s laws, voltage and current division).</li><li>Laboratory equipment and measurements. Use of simulation software.</li><li>Frequency domain analysis, the concept of spectrum. Transfer function and filter.</li><li>Sampling: A/D and D/A converters, sampling theorem.</li></ul><h5>Complementary knowledge</h5><ul><li>Parasitic circuit elements.</li><li>Grounding and the properties of metallic cables.</li><li>Fourier series and Fourier transform. Filter implementations.</li><li>Digital filters. Convolution.</li></ul><h5>Specialist knowledge</h5><ul><li>Diode, operational amplifier.</li></ul>";
        String actualEn = tested.getContent().get("en");
        String expectedFi = "<h5>Ydinsisältö</h5><ul><li>Suureet ja yksiköt, analogisen signaalin käsite:<br />Tasa- ja vaihtovirta ja -jännite. Ohmin laki. Pätö- ja loisteho. Desibeli.</li><li>Komponentit ja virtapiirit:<br />Vastus, kela ja kondensaattori sekä tasa- ja vaihtovirtapiirit (Kirchhoffin lait, jännitteen- ja virranjako).</li><li>Laboratoriolaitteet ja mittaaminen. Simulointiohjelman käyttö.</li><li>Taajuustason analyysi, spektrin käsite. Siirtofunktio ja suodin.</li><li>Näytteenotto: AD- ja DA-muuntimet, näytteenottoteoreema.</li></ul><h5>Täydentävä tietämys</h5><ul><li>Parasiittiset piirielementit.</li><li>Maadoitukset ja metallijohtojen ominaisuudet.</li><li>Fourier-sarja ja -muunnos. Suodintoteutukset.</li><li>Digitaaliset suotimet. Konvoluutio.</li></ul><h5>Erityistietämys</h5><ul><li>Diodi, operaatiovahvistin.</li></ul>";
        String actualFi = tested.getContent().get("fi");
        assertEquals(expectedEn, actualEn);
        assertEquals(expectedFi, actualFi);
    }

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
    public void testGetDescription() {
        CourseData tested = new CourseData(
                            new networkHandler().getCourseByGroupId(
                                "tut-cu-g-45460"));
        String expectedEn = "Completing successfully all the laboratory works gives course grade 1. Better course grades (2-5) can be achieved by taking an exam in addition to the mandatory laboratory works. The Finnish implementation is organized in period 1 and the English implementation in periods 3-4.";
        String actualEn = tested.getDescription().get("en");
        assertEquals(expectedEn, actualEn);
        String expectedFi = "Hyväksytysti suoritetut laboratoriotyöt antavat kurssiarvosanan yksi. Parempi kurssiarvosana on mahdollista saada käymällä lisäksi tentissä. Suomenkielinen toteutus järjestetään periodissa 1 ja englanninkielinen toteutus periodeissa 3-4.";
        String actualFi = tested.getDescription().get("fi");
        assertEquals(expectedFi, actualFi);
    }

    @Test
    public void testGetEvaluationCriteria() {
        CourseData tested = new CourseData(
                            new networkHandler().getCourseByGroupId(
                                "tut-cu-g-45460"));
        String expectedEn = "To earn grade 3, student have to know the core content well: Understands the meaning of Ohm's law in circuits. Understands the meaning of active & reactive power and is able to calculate them. Is able to fluently use decibels in calculations. Is able to identify resistor, inductor & capacitor as well as know their basic functions. Is able to computationally analyze simple DC & AC circuits. Knows the typical measurement equipment and is able to do basic measurements carefully taking into account error sources. Understands the meaning of grounding in basic measurements. Is able to do basic measurements carefully with a data acquisition device attached to a computer and take into account error sources. Is able to interpret frequency-domain presentations and understands the connection between time & frequency domain. Knows the basic filter types and is able to derive transfer function for simple systems. Understands sampling theorem and other essentials of sampling process. Understands basic principles of DSP.";
        String actualEn = tested.getEvaluationCriteria().get("en");
        assertEquals(expectedEn, actualEn);
        String expectedFi = "Saadakseen arvosanan 3, opiskelijan tulee tuntea kurssin ydinsisältö hyvin: Ymmärtää Ohmin lain merkityksen virtapiireissä. Ymmärtää pätö- ja loistehon merkityksen ja osaa laskea ne. Osaa käyttää desibelejä teholaskuissa sujuvasti. Tunnistaa vastuksen, kelan ja kondensaattorin sekä tietää niiden perustoiminnan. Osaa laskennallisesti analysoida yksinkertaisia tasa- ja vaihtojännitepiirejä. Tuntee tyypilliset mittalaitteet ja osaa tehdä perusmittauksia huolellisesti ottaen huomioon virhelähteet. Ymmärtää maadoituksen merkityksen perusmittauksissa. Osaa tehdä perusmittauksia tietokoneeseen liitettävällä signaalinkeruulaitteella huolellisesti ottaen huomioon virhelähteet. Osaa tulkita taajuustason esityksiä ja ymmärtää yhteyden aika- ja taajuustason välillä. Tuntee suotimien perustyypit ja osaa johtaa siirtofunktion yksinkertaisille järjestelmille. Ymmärtää näytteenottoteoreeman ja muut keskeiset näytteenottoon liittyvät näkökulmat. Ymmärtää digitaalisen signaalinkäsittelyn perusperiaatteita.";
        String actualFi = tested.getEvaluationCriteria().get("fi");
        assertEquals(expectedFi, actualFi);
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
