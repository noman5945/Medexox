package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassWordActivity extends AppCompatActivity {
    TextView txt;
    EditText reset_email;
    Button rest_link;
    Button Canc;
    String resetEmail;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_word);

        txt=(TextView)findViewById(R.id.text_1);
        reset_email=(EditText)findViewById(R.id.mail);
        rest_link=(Button)findViewById(R.id.reset_pass);
        Canc=(Button)findViewById(R.id.canc);

        mAuth=FirebaseAuth.getInstance();
    }

    public void ResetPass(View view) {
        resetEmail=reset_email.getText().toString().trim();

        if(resetEmail.isEmpty())
        {
            reset_email.setError("Email is Required");
            reset_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(resetEmail).matches())
        {
            reset_email.setError("Valid Email is Required");
            reset_email.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(resetEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgotPassWordActivity.this,"A link has been sent to your Email.PLease check",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ForgotPassWordActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel(View view) {
        Intent intent=new Intent(this,DataBaseActivity.class);
        finish();
        startActivity(intent);
    }

}