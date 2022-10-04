package com.ef.mockitoJUnit;

import com.ef.mockitoJUnit.PatientAppointment.ClinicCalendar;
import com.ef.mockitoJUnit.PatientAppointment.Doctor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;

@SpringBootApplication
public class MockitoJUnitApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockitoJUnitApplication.class, args);
		System.out.println("Patient Appointment started");
	}

}
