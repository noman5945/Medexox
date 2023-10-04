package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.reminderapp.EmergencyCalling.SendMail;

public class EmailBodyActivity extends AppCompatActivity {
    EditText reciver;
    EditText subj;
    EditText messagebody;
    Class context;
    String prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_body);

        Intent intent=getIntent();

        reciver=(EditText)findViewById(R.id.to);
        subj=(EditText)findViewById(R.id.subject);
        messagebody=(EditText)findViewById(R.id.message);

        String re=intent.getStringExtra("mail");
        prev=intent.getStringExtra("prev");
        reciver.setText(re);
    }

    public void setContext(Class cnt)
    {
        context=cnt;
    }

    public void back(View view) {
        finish();

        if(prev.equals("1"))
        {
            startActivity(new Intent(EmailBodyActivity.this, DashBoard.class));
        }
        else if(prev.equals("2"))
        {
            startActivity(new Intent(EmailBodyActivity.this, EmergencyContacts.class));
        }

        //startActivity(new Intent(EmailBodyActivity.this, context.getClass()));
    }

    public void send(View view) {
        SendMail sendMail=new SendMail();

        String rec=reciver.getText().toString();
        String sub=subj.getText().toString();
        String mes=messagebody.getText().toString();

        if(rec.isEmpty())
        {
            reciver.setError("Receiver email address required");
            reciver.requestFocus();
            return;
        }

        sendMail.Send(rec,sub,mes,EmailBodyActivity.this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EmailBodyActivity.this, EmergencyContacts.class);
        startActivity(intent);

        finish();
    }
}