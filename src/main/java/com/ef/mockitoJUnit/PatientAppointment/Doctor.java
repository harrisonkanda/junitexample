package com.ef.mockitoJUnit.PatientAppointment;

import java.util.HashMap;
import java.util.Map;

public class Doctor {

    private Map<String, String> doctorNames = new HashMap<>();

    public Doctor(){
        doctorNames.put("0001", "DR. BEN MWANGI");
        doctorNames.put("0002", "DR. HILLARY BII");
        doctorNames.put("0003", "DR. MASIT SHAH");
    }

    public Map<String, String> getDoctorNameList(){
        return doctorNames;
    }

    public String getDoctorByID(String id) {

        if(doctorNames.containsKey(id)){
            return doctorNames.get(id);
        } else {
            throw new IllegalArgumentException("Invalid doctor Identifier: " + id);
        }
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorNames=" + doctorNames +
                '}';
    }
}
