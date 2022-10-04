package com.ef.mockitoJUnit;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import com.ef.mockitoJUnit.PatientAppointment.Doctor;
import org.junit.jupiter.api.*;
import com.ef.mockitoJUnit.PatientAppointment.ClinicCalendar;
import com.ef.mockitoJUnit.PatientAppointment.PatientAppointment;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.condition.OS.MAC;
import static org.junit.jupiter.api.condition.OS.LINUX;

class ClinicCalendarTest {

    private static ClinicCalendar calendar;
    private static Doctor doctor;

    @BeforeAll
    public static void testClassSetup() {
        calendar = new ClinicCalendar(LocalDate.of(2022, 10, 3));
        doctor = new Doctor();
        System.out.println("Before all...");
    }

    /*
    @BeforeEach
    public void init(TestInfo testInfo,  RepetitionInfo repetitionInfo) {

        System.out.println("Before each...");
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        int totalRepetitions = repetitionInfo.getTotalRepetitions();
        String methodName = testInfo.getTestMethod().get().getName();
        System.out.println(String.format("About to execute repetition %d of %d for %s",
                currentRepetition, totalRepetitions, methodName));

    }
    */

    @Test
    public void allowEntryOfAnAppointment() {

        System.out.println("Entry of an appointment...");

        calendar.addAppointment("Shannon", "Fisher", "0001", "9/23/2023 06:00 PM");

        List<PatientAppointment> appointments = calendar.getAppointments();

        assertNotNull(appointments);
        assertEquals(4, appointments.size());

        PatientAppointment enteredAppt = appointments.get(0);

        // In a grouped assertion all assertions are executed, and all
        // failures will be reported together.
        Assertions.assertAll(
                () -> assertEquals("Shannon", enteredAppt.getPatientFirstName()),
                () -> assertEquals("Fisher", enteredAppt.getPatientLastName()),
                () -> Assertions.assertSame("DR. BEN MWANGI", enteredAppt.getDoctor()),
                () -> assertEquals("9/23/2023 06:00 PM",
                        enteredAppt.getAppointmentDateTime().format(DateTimeFormatter.ofPattern("M/d/yyyy hh:mm a"))));
    }

    @Test
    public void returnTrueForHasAppointmentsIfThereAreAppointments() {

        System.out.println("Has appointments...");

        calendar.addAppointment("Shannon", "Fisher", "0002", "10/03/2022 06:00 PM");
        Assertions.assertTrue(calendar.hasAppointment(LocalDate.of(2022, 11, 03)));
    }

    @Test
    public void returnFalseForHasAppointmentsIfThereAreNoAppointments() {

        System.out.println("No appointments...");

        Assertions.assertFalse(calendar.hasAppointment(LocalDate.of(2018, 9, 1)));
    }

    @Test
    @DisplayName("returnSizeofCurrentDaysAppointments")
    public void returnSizeofCurrentDaysAppointments() {

        System.out.println("Current day appointments...");

        calendar.addAppointment("Shannon", "Fisher", "0001", "9/23/2022 06:00 PM");
        calendar.addAppointment("Derek", "Steward", "0002", "9/23/2022 01:00 PM");
        calendar.addAppointment("Paul", "Purcell", "0003", "today 08:00 PM");

        assertEquals(1, calendar.getTodayAppointments().size());
    }

    @ParameterizedTest
    @ValueSource(strings = { "0001", "0002", "0003" })
    @DisplayName("ParameterizedTest - Validate the list of doctorIDs")
    void test_WhenProvidedValidArrayOfDoctorIDs_ThenDoesNotThrowExecption(String doctorId) {
        Assertions.assertDoesNotThrow(
                () ->  doctor.getDoctorByID(doctorId), "Invalid doctor Identifier:" + doctorId);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0004", "0005"})
    @DisplayName("ParameterizedTest - Throws IllegalArgumentException when invalid List is provided")
    void test_WhenInvalidArrayOfDoctorIDsIsProvided_ThenThrowsIllegalArgumentException(String doctorId) {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> doctor.getDoctorByID(doctorId),
                "Invalid doctor Identifier:" + doctorId
        );
    }

    @Test
    void test_timeoutNotExceededWhenFetchingDoctorsID(){

        String doctorId = Assertions.assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(1000);
            return doctor.getDoctorByID("0002");
        }, "Timeout exceeded when fetching doctor");
        //Assertions.assertNotNull(doctorId);
        assertEquals("DR. HILLARY BII", doctorId);

    }

    @Test
    @Disabled("Disabled until bug XYZ is fixed")
    void test_DisablingOfTest(){
        System.out.println("Testing disabled test");
    }

    @Test
    @DisabledOnOs({MAC, LINUX})
    void onlyOnWindows() {
        System.out.println("This test is executed on WINDOWS OS");
    }

    @Test
    @DisabledOnJre({JRE.JAVA_8, JRE.JAVA_9})
    void onlyOnJREABOVEVersion9() {
         System.out.println("Test executes on JRE versions above 9");
    }

    @Test
    @DisabledForJreRange(min = JRE.JAVA_18, max = JRE.JAVA_18)
    void onlyOnJREVersion16AndAbove() {
         System.out.println("Test executes on JRE versions above 16");
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void testWithExplicitLocalMethodSource(String argument) {
        assertNotNull(argument);
    }

    static Stream<String> stringProvider() {
        return Stream.of("apple", "bananas");
    }
    /*
    @Test
    @RepeatedTest(10)
    void repeat10Times(){

    } */

    @ParameterizedTest
    @CsvSource({
       "apple,         1",
       "banana,        2"
    })
    void testWithCsvSource(String fruit, int rank) {
        assertNotNull(fruit);
        System.out.println("ranking test " +fruit +" rank:"+ + rank);
        assertNotEquals(0, rank);
    }

    @Test
    @ParameterizedTest
    @CsvFileSource(files = "/src/test/java/com/ef/mockitoJUnit/test.csv")
    void testWithCsvFileSourceFromFile(String name, int reference) {
        assertNotNull(name);
        assertNotEquals(0, reference);
    }

    @AfterEach
    public void tearDownEachTest() {

        System.out.println("After each...");
    }

    @AfterAll
    public static void tearDownTestClass() {

        System.out.println("After all...");
    }
}