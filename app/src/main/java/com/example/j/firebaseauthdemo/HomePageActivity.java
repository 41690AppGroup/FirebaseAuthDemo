package com.example.j.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class HomePageActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_setting){

        }
        if (item.getItemId() == R.id.action_edit_patient_details){
            finish();
            startActivity(new Intent(getApplicationContext(), EditPatientDetailsActivity.class));
        }
        if (item.getItemId() == R.id.action_edit_doctor_details){
            finish();
            startActivity(new Intent(getApplicationContext(), EditDoctorDetailsActivity.class));
        }
        if (item.getItemId() == R.id.action_change_tracking_radius){
            finish();
            startActivity(new Intent(getApplicationContext(), ChangeTrackingRadiusActivity.class));
        }
        if (item.getItemId() == R.id.logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
