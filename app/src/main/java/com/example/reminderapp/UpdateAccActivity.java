package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.reminderapp.UserAuth.User;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateAccActivity extends AppCompatActivity {
    EditText name,email,age,gender,pass_old,pass_new;

    String user_name;
    String user_age;
    String user_type;
    String user_email;
    String user_gender;
    String user_password;
    User user1;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_acc);

        user= FirebaseAuth.getInstance().getCurrentUser();

        Intent intent=getIntent();
        user_name=intent.getStringExtra("name");
        user_age=intent.getStringExtra("age");
        user_email=intent.getStringExtra("email");
        user_gender=intent.getStringExtra("gender");
        user_password=intent.getStringExtra("pas");

        name=(EditText) findViewById(R.id.name);
        email=(EditText)findViewById(R.id.Email);
        age=(EditText)findViewById(R.id.age);
        gender=(EditText)findViewById(R.id.gender);
        pass_old=(EditText)findViewById(R.id.pass_old);
        pass_new=(EditText)findViewById(R.id.pass_new);

        name.setText(user_name);
        age.setText(user_age);
        email.setText(user_email);
        gender.setText(user_gender);

    }

    public void Back(View view) {
        finish();
        startActivity(new Intent(UpdateAccActivity.this,MyAccount.class));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateAccActivity.this,MyAccount.class);
        startActivity(intent);

        finish();
    }

    public void Updater(View view) {
        String Email=email.getText().toString();
        String newpas=pass_new.getText().toString();
        String newname=name.getText().toString();
        String newAge=age.getText().toString();
        String gen=gender.getText().toString();

        String oldpas=pass_old.getText().toString();

        if(!oldpas.equals(user_password))
        {
            pass_old.setError("Password mismatch");
            pass_old.requestFocus();
            return;
        }

        if(Email.isEmpty())
        {
            email.setError("Email Required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            email.setError("Valid Email Required");
            email.requestFocus();
            return;
        }
        if(newAge.isEmpty())
        {
            age.setError("Valid Age Required");
            age.requestFocus();
            return;
        }
        if(newname.isEmpty())
        {
            name.setError("Name Required");
            name.requestFocus();
            return;
        }

        if(newpas.isEmpty()|| newpas.length()<6)
        {
            pass_new.setError("Password Required and must be 6 characters");
            pass_new.requestFocus();
            return;
        }


        user1=new User(newname,Email,newAge,"Patient",gen,newpas);
        //updating authentication information for login
        AuthCredential credential= EmailAuthProvider.getCredential(Email,oldpas);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    user.updatePassword(newpas).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(user.getUid()).setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(UpdateAccActivity.this,"Credentials Updated!",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(UpdateAccActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(UpdateAccActivity.this,"Ekhane problem",Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}