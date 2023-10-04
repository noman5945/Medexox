package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reminderapp.UserAuth.Health;
import com.example.reminderapp.calculators.BMICalc;
import com.example.reminderapp.calculators.BMRCalc;
import com.example.reminderapp.calculators.HeightConvert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class HealthStatusUpdateActivity extends AppCompatActivity {
    EditText sys,diastole,heightFeet,heightInch,pulse,weight,diabetes;
    TextView bmi,bmr,heightMeter;
    double weigh,bmi1,bmr1,height1,sys1,dias,pul,diab1,height2,heightMe;
    String age,gender;

    FirebaseUser user;
    DatabaseReference dbf;
    String uid;
    HeightConvert heightConvert;
    BMICalc bmiCalc;
    BMRCalc bmrCalc;
    Health health;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_status_update);

        Intent intent=getIntent();

        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();


        age=intent.getStringExtra("Age");
        gender=intent.getStringExtra("sex");

        dbf= FirebaseDatabase.getInstance().getReference("HealthStatus");

        sys=(EditText)findViewById(R.id.systol);
        diastole=(EditText)findViewById(R.id.diastol);
        heightFeet=(EditText)findViewById(R.id.heightFeet);
        heightInch=(EditText)findViewById(R.id.heightInch);
        pulse=(EditText)findViewById(R.id.pulse);
        diabetes=(EditText)findViewById(R.id.diabetes);
        weight=(EditText)findViewById(R.id.weight);

        heightMeter=(TextView)findViewById(R.id.inmete);
        bmi=(TextView)findViewById(R.id.bmi);
        bmr=(TextView)findViewById(R.id.bmr);

        heightConvert=new HeightConvert();
        bmiCalc=new BMICalc();
        bmrCalc=new BMRCalc();
    }

    public void back(View view) {
        finish();
        startActivity(new Intent(HealthStatusUpdateActivity.this,HealthStatus.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HealthStatusUpdateActivity.this,HealthStatus.class);
        startActivity(intent);

        finish();
    }

    public void update(View view) {

            if(sys.getText().toString().isEmpty())
            {
                sys.setError("Systole Required");
                sys.requestFocus();
                return;
            }

            if(diastole.getText().toString().isEmpty())
            {
                diastole.setError("Diastole Required");
                diastole.requestFocus();
                return;
            }

            if(heightFeet.getText().toString().isEmpty())
            {
                heightFeet.setError("Height Required");
                heightFeet.requestFocus();
                return;
            }

            if(heightInch.getText().toString().isEmpty())
            {
                heightInch.setError("Height Required");
                heightInch.requestFocus();
                return;
            }

            if(pulse.getText().toString().isEmpty())
            {
                pulse.setError("Heart Pulse required.Your pulse rate can be found on your prescription");
                pulse.requestFocus();
                return;
            }

            if(diabetes.getText().toString().isEmpty())
            {
                diabetes.setError("Diabetes required");
                diabetes.requestFocus();
                return;
            }

            if(weight.getText().toString().isEmpty())
            {
                weight.setError("Weight required");
                weight.requestFocus();
                return;
            }

            try {
                weigh=Double.parseDouble(weight.getText().toString());
                sys1=Double.parseDouble(sys.getText().toString());
                dias=Double.parseDouble(diastole.getText().toString());
                pul=Double.parseDouble(pulse.getText().toString());
                diab1=Double.parseDouble(diabetes.getText().toString());
                height1=Double.parseDouble(heightFeet.getText().toString());
                height2=Double.parseDouble(heightInch.getText().toString());


                heightMe=heightConvert.convertToMeter(height2,height1);
                bmi1=bmiCalc.CalculateBMI(heightMe,weigh);
                bmr1=bmrCalc.CalculateBMR(gender,age,weigh,height1,height2);

            }catch (NumberFormatException e)
            {
                Toast.makeText(HealthStatusUpdateActivity.this,"Inputs must be numbers",Toast.LENGTH_LONG).show();
            }
            heightMeter.setText(String.valueOf(heightMe));

            bmi.setText(String.valueOf(bmi1));
            bmr.setText(String.valueOf(bmr1));

            health=new Health(weigh,heightMe,bmi1,bmr1,diab1,pul,sys1,dias);

            dbf.child(uid).setValue(health).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(HealthStatusUpdateActivity.this,"Successfully updated",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(HealthStatusUpdateActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });

            //Toast.makeText(HealthStatusUpdateActivity.this,age+gender,Toast.LENGTH_LONG).show();
    }


}