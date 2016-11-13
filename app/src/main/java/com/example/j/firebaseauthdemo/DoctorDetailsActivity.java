package com.example.j.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextDoctorName;
    private EditText editTextDoctorPhoneNumber;
    private EditText editTextDoctorHospital;
    private Button buttonDoctorSubmit;
    private Button buttonDoctorLogout;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //closing the activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        editTextDoctorName = (EditText) findViewById(R.id.editTextDoctorName);
        editTextDoctorHospital = (EditText) findViewById(R.id.editTextDoctorHospital);
        editTextDoctorPhoneNumber = (EditText) findViewById(R.id.editTextDoctorPhoneNumber);
        buttonDoctorSubmit = (Button) findViewById(R.id.buttonDoctorSubmit);
        buttonDoctorLogout = (Button) findViewById(R.id.buttonDoctorLogout);

        buttonDoctorSubmit.setOnClickListener(this);
        buttonDoctorLogout.setOnClickListener(this);


    }

    public  void submitDoctorDetails(){
        String strDoctorName = editTextDoctorName.getText().toString().trim();
        String strDoctorHospital = editTextDoctorHospital.getText().toString().trim();
        Long lDoctorPhoneNumber = Long.valueOf(editTextDoctorPhoneNumber.getText().toString().trim());

        if (TextUtils.isEmpty(strDoctorHospital)){
            //email is empty
            Toast.makeText(this, "Please enter hospital name where doctor works", Toast.LENGTH_SHORT).show();
            //stopping the function execution further.
            return;
        }
        if (TextUtils.isEmpty(strDoctorName)){
            //email is empty
            Toast.makeText(this, "Please enter doctor's name", Toast.LENGTH_SHORT).show();
            //stopping the function execution further.
            return;
        }
        if (lDoctorPhoneNumber == null){
            //email is empty
            Toast.makeText(this, "Please enter doctor's phone number", Toast.LENGTH_SHORT).show();
            //stopping the function execution further.
            return;
        }

        DoctorInformation doctorInformation = new DoctorInformation(strDoctorName, strDoctorHospital, lDoctorPhoneNumber);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(doctorInformation);
        Toast.makeText(this, "Information Saved...", Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onClick(View view) {
        if (view == buttonDoctorSubmit){
            submitDoctorDetails();
            finish();
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
        }
        if (view == buttonDoctorLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
