package com.ef.mockitoJUnit.PatientAppointment;

import java.time.LocalDateTime;

public class PatientAppointment {

    private String patientFirstName;
    private String patientLastName;
    private LocalDateTime appointmentDateTime;
    private String doctor;

    public PatientAppointment(String patientFirstName, String patientLastName, LocalDateTime appointmentDateTime, String doctor) {

        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.appointmentDateTime = appointmentDateTime;
        this.doctor = doctor;
    }

    public String getPatientFirstName() {

        return patientFirstName;
    }

    public String getPatientLastName() {

        return patientLastName;
    }

    public LocalDateTime getAppointmentDateTime() {

        return appointmentDateTime;
    }

    public String getDoctor() {

        return doctor;
    }
}