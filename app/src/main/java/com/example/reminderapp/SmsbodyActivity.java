package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.reminderapp.EmergencyCalling.SendSMS;

public class SmsbodyActivity extends AppCompatActivity {

    EditText rec,msg;
    String reci,mes;
    SendSMS sendSMS=new SendSMS();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsbody);

        Intent intent=getIntent();

        reci=intent.getStringExtra("Num");
        rec=(EditText)findViewById(R.id.to2);
        msg=(EditText)findViewById(R.id.message2);

        rec.setText(reci);
    }

    public void send(View view) {
        mes=msg.getText().toString();
        sendSMS.SMSsend(reci,mes,SmsbodyActivity.this);
    }

    public void back(View view) {
        finish();
        startActivity(new Intent(SmsbodyActivity.this, EmergencyContacts.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SmsbodyActivity.this, EmergencyContacts.class);
        startActivity(intent);

        finish();
    }
}