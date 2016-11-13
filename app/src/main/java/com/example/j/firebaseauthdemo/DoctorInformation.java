package com.example.j.firebaseauthdemo;

/**
 * Created by Pratibha on 04-11-2016.
 */

public class DoctorInformation {
    public String strDoctorName;
    public String strDoctorHospital;
    public Long lDoctorPhoneNumber;

    public DoctorInformation(String strDoctorName, String strDoctorHospital, Long lDoctorPhoneNumber) {
        this.strDoctorName = strDoctorName;
        this.strDoctorHospital = strDoctorHospital;
        this.lDoctorPhoneNumber = lDoctorPhoneNumber;
    }
}
