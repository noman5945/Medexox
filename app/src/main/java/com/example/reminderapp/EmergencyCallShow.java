package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmergencyCallShow extends AppCompatActivity {
    Button startbutt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_call_show);

        startbutt=(Button)findViewById(R.id.start);

    }

    public void Start(View view) {
        Intent sb = new Intent(this, EmergencyContacts.class);
        finish();
        startActivity(sb);

    }

}