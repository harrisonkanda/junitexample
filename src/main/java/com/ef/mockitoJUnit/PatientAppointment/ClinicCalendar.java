package com.ef.mockitoJUnit.PatientAppointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClinicCalendar {

    private List<PatientAppointment> appointments;
    private LocalDate today;
    private Doctor doctor;
    public ClinicCalendar(LocalDate today) {

        this.today = today;
        this.appointments = new ArrayList<PatientAppointment>();
        doctor = new Doctor();
    }
    public void addAppointment(String patientFirstName, String patientLastName, String doctorId, String dateTime) {

        String doctorName = doctor.getDoctorByID(doctorId);
        LocalDateTime localDateTime = DateTimeConverter.convertStringToDateTime(dateTime, today);
        PatientAppointment appointment = new PatientAppointment(patientFirstName, patientLastName, localDateTime, doctorName);

        appointments.add(appointment);
    }

    public List<PatientAppointment> getAppointments() {

        return appointments;
    }

    public List<PatientAppointment> getTodayAppointments() {

        return appointments.stream().filter(appt -> appt.getAppointmentDateTime().toLocalDate().equals(today)).collect(Collectors.toList());
    }

    public boolean hasAppointment(LocalDate date) {

        return appointments.stream().anyMatch(appt -> appt.getAppointmentDateTime().toLocalDate().equals(date));
    }

    public void printAppointments(){
        int i = 0;
        for( PatientAppointment appointment : appointments){
            System.out.println(String.format("============================== Patient %d details: ================================================", i));
            System.out.println(String.format("First name: %s, Last name: %s, Date of appointment %s",
                    appointment.getPatientFirstName(), appointment.getPatientLastName(), appointment.getAppointmentDateTime(),
                    appointment.getDoctor()));
            i++;
        }
    }

}