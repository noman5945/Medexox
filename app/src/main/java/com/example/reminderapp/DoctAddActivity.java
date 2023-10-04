package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reminderapp.DoctorsList.DoctorInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class DoctAddActivity extends AppCompatActivity {

    EditText DocNam,Hos,academic,type,phoneNo,Docmail;
    DatabaseReference databaseReference;
    Random rand=new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doct_add);

        databaseReference= FirebaseDatabase.getInstance().getReference("DoctorList/Nutritionist");

        DocNam=(EditText)findViewById(R.id.name);
        Hos=(EditText)findViewById(R.id.hos);
        academic=(EditText)findViewById(R.id.aca);
        type=(EditText)findViewById(R.id.type);
        phoneNo=(EditText)findViewById(R.id.cont);
        Docmail=(EditText)findViewById(R.id.docmail);
    }

    public void add(View view) {
        int id1=rand.nextInt(100);
        String id=String.valueOf(id1);

        String name=DocNam.getText().toString();
        String HS=Hos.getText().toString();
        String aca=academic.getText().toString();
        String typ=type.getText().toString();
        String PNno=phoneNo.getText().toString();
        String domail=Docmail.getText().toString();

        DoctorInfo doctorInfo=new DoctorInfo(name,HS,aca,typ,PNno,domail,id);
        databaseReference.child(id).setValue(doctorInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(DoctAddActivity.this,"Added",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(DoctAddActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void back(View view) {
            finish();
            startActivity(new Intent(DoctAddActivity.this,DashBoard.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DoctAddActivity.this,DashBoard.class);
        startActivity(intent);

        finish();
    }


}