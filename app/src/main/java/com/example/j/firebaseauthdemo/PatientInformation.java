package com.example.j.firebaseauthdemo;

import android.widget.RadioButton;

/**
 * Created by Pratibha on 03-11-2016.
 */

public class PatientInformation {
    public String fname;
    public String lname;
    public Integer age;
    public String sickness;
    public RadioButton gender;

    public PatientInformation(String fname, String lname, Integer age, String sickness, RadioButton gender) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.sickness = sickness;
        this.gender = gender;
    }
}
