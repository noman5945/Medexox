package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reminderapp.EmergencyCalling.CallerInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class AddCallerActivity extends AppCompatActivity {

    EditText name,num,mail;
    DatabaseReference databaseReference;
    FirebaseUser fUser;
    String id2,uid,nam,num1,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_caller);

        name=(EditText)findViewById(R.id.cnt_name);
        num=(EditText)findViewById(R.id.snt_num);
        mail=(EditText)findViewById(R.id.snt_email);

        fUser=FirebaseAuth.getInstance().getCurrentUser();
        uid=fUser.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("Contacts");

        Random rand=new Random();
        int id=rand.nextInt(100);
        id2=String.valueOf(id);
    }
    public void add(View view){
        nam=name.getText().toString();
        num1=num.getText().toString();
        email=mail.getText().toString();

        if(nam.isEmpty())
        {
            name.setError("Name Required");
            name.requestFocus();
            return;
        }
        if(num1.isEmpty() || num1.length()<11)
        {
            num.setError("Valid phone number required");
            num.requestFocus();
            return;
        }


        CallerInfo callerInfo=new CallerInfo(nam,num1,id2,email);
        databaseReference.child(uid).child(id2).setValue(callerInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(AddCallerActivity.this,"Done!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(AddCallerActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void back(View view) {
        finish();
        startActivity(new Intent(AddCallerActivity.this, EmergencyContacts.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddCallerActivity.this, EmergencyContacts.class);
        startActivity(intent);
        finish();
    }
}