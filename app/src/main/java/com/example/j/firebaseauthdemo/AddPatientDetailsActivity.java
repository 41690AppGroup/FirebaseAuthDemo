package com.example.j.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPatientDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonPatientSubmit;
    private Button buttonLogOut;
    private EditText editTextPatientFirstName;
    private EditText editTextPatientLastName;
    private EditText editTextPatientAge;
    private RadioGroup radioGroupPatientGender;
    private RadioButton radioButtonPatientMale;
    private RadioButton radioButtonPatientFemale;
    private EditText editTextSicknessType;
    private RadioButton radioButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_details);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            //closing the activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        buttonPatientSubmit = (Button) findViewById(R.id.buttonPatientSubmit);
        buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        editTextPatientFirstName = (EditText) findViewById(R.id.editTextPatientFirstName);
        editTextPatientLastName = (EditText) findViewById(R.id.editTextPatientLastName);
        editTextPatientAge = (EditText) findViewById(R.id.editTextPatientAge);
        radioGroupPatientGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        radioButtonPatientMale = (RadioButton) findViewById(R.id.radioButtonPatientMale);
        radioButtonPatientFemale = (RadioButton) findViewById(R.id.radioButtonPatientFemale);
        editTextSicknessType = (EditText) findViewById(R.id.editTextPatientSickness);
       // radioButtonPatientGender = (RadioButton) findViewById(R.id.)


        buttonPatientSubmit.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);

    }

    public void submitPatientDetails(){
        String strFirstName = editTextPatientFirstName.getText().toString().trim();
        String strLastName = editTextPatientLastName.getText().toString().trim();
        Integer iAge = Integer.valueOf(editTextPatientAge.getText().toString().trim());
        String strSickness = editTextSicknessType.getText().toString().trim();

        int selected_id = radioGroupPatientGender.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selected_id);


        if (TextUtils.isEmpty(strFirstName))
        {
            //email is empty
            Toast.makeText(this, "Please enter patient's first name", Toast.LENGTH_SHORT).show();
            //stopping the function execution further.
            return;
        }
        if (TextUtils.isEmpty(strLastName))
        {
            //password is empty
            Toast.makeText(this, "Please enter patient's last name", Toast.LENGTH_SHORT).show();
            //stopping the function execution further.
            return;
        }
        if (iAge == null)
        {
            //password is empty
            Toast.makeText(this, "Please enter patient's age", Toast.LENGTH_SHORT).show();
            //stopping the function execution further.
            return;
        }
        if (TextUtils.isEmpty(strSickness))
        {
            //password is empty
            Toast.makeText(this, "Please enter patient's sickness", Toast.LENGTH_SHORT).show();
            //stopping the function execution further.
            return;
        }
        if (radioButton == null)
        {
            //password is empty
            Toast.makeText(this, "Please select patient's gender", Toast.LENGTH_SHORT).show();
            //stopping the function execution further.
            return;
        }

        PatientInformation patientInformation = new PatientInformation(strFirstName, strLastName, iAge, strSickness, radioButton);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(patientInformation);
        Toast.makeText(this, "Information Saved...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == buttonPatientSubmit){
            submitPatientDetails();
            finish();
            startActivity(new Intent(getApplicationContext(), DoctorDetailsActivity.class));
        }
        if (view == buttonLogOut){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
