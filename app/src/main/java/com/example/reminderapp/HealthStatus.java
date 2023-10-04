package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.health.HealthStats;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.reminderapp.UserAuth.Health;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HealthStatus extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference databaseReference;
    String uid;
    String age,gender;

    ImageView BloodPressure,BmiCard,BmrCard,WeightCard,DiabetesCard;
    TextView bmi,bmr,bloodPress,weight,diabetes;
    ImageView back,pop,ques;
    String bloodP;
    Health health;
    String sys1;
    String dias1;
    String pul;
    String dia1;

    double bmival,bmrval,heightval,weightval,systol,diastol,puls,diab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_status);

        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();

        databaseReference= FirebaseDatabase.getInstance().getReference();

        BloodPressure= findViewById(R.id.BloodPressure);
        BmiCard= findViewById(R.id.BMIcard);
        BmrCard= findViewById(R.id.Bmrcard);
        WeightCard= findViewById(R.id.WeightCard);
        DiabetesCard= findViewById(R.id.DiabetesCard);



        bmi=(TextView)findViewById(R.id.bmi);
        bmr=(TextView)findViewById(R.id.bmr);
        weight=(TextView)findViewById(R.id.weight);
        diabetes=(TextView)findViewById(R.id.diabetes);
        bloodPress=(TextView)findViewById(R.id.bloodPressur);



        back=(ImageView)findViewById(R.id.back_) ;
        pop=(ImageView)findViewById(R.id.healthBar);
        ques=(ImageView)findViewById(R.id.ques);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                pop.setVisibility(View.GONE);
            }
        },5000);


        getData(databaseReference,uid);



    }

    private void getData(DatabaseReference dbf,String id) {

        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                bmival=snapshot.child("HealthStatus").child(id).child("BMI").getValue(Double.class);
                bmrval=snapshot.child("HealthStatus").child(id).child("BMR").getValue(Double.class);
                weightval=snapshot.child("HealthStatus").child(id).child("weight").getValue(Double.class);
                diab=snapshot.child("HealthStatus").child(id).child("diabetes").getValue(Double.class);
                heightval=snapshot.child("HealthStatus").child(id).child("hight").getValue(Double.class);
                systol=snapshot.child("HealthStatus").child(id).child("bloodSystol").getValue(Double.class);
                diastol=snapshot.child("HealthStatus").child(id).child("bloodDiastol").getValue(Double.class);
                puls=snapshot.child("HealthStatus").child(id).child("pulse").getValue(Double.class);
                age=snapshot.child("Users").child(id).child("age").getValue(String.class);
                gender=snapshot.child("Users").child(id).child("gender").getValue(String.class);

                String bmi1=String.valueOf(bmival);
                String bmr1=String.valueOf(bmrval);
                String weight1=String.valueOf(weightval);
                dia1=String.valueOf(diab);
                sys1=String.valueOf(systol);
                dias1=String.valueOf(diastol);
                pul=String.valueOf(puls);


                if(bmi1.equals("0.0") && bmr1.equals("0.0") && weight1.equals("0.0") && dia1.equals("0.0"))
                {

                    //BmiCard.setCardBackgroundColor(Color.parseColor("#00bfff"));
                    //BmrCard.setCardBackgroundColor(Color.parseColor("#00bfff"));
                    //WeightCard.setCardBackgroundColor(Color.parseColor("#00bfff"));
                    //DiabetesCard.setCardBackgroundColor(Color.parseColor("#00bfff"));

                    bmi.setTextColor(Color.parseColor("#00bfff"));
                    bmr.setTextColor(Color.parseColor("#00bfff"));
                    weight.setTextColor(Color.parseColor("#00bfff"));
                    diabetes.setTextColor(Color.parseColor("#00bfff"));
                    bloodPress.setTextColor(Color.parseColor("#00bfff"));

                }

                if(bmival>0.0 && bmival<=18.5)
                {
                    //underweight
                    //BmiCard.setCardBackgroundColor(Color.parseColor("#00bfff"));
                    bmi.setTextColor(Color.parseColor("#00bfff"));
                    weight.setTextColor(Color.parseColor("#00bfff"));
                }
                if(bmival>18.5 && bmival<=24.5)
                {
                    //normal weight
                    //BmiCard.setCardBackgroundColor(Color.parseColor("#00FF7F"));
                    bmi.setTextColor(Color.parseColor("#14C56C"));
                    weight.setTextColor(Color.parseColor("#14C56C"));
                }
                if(bmival>24.5 && bmival<=29.9)
                {
                    //over weight
                    //BmiCard.setCardBackgroundColor(Color.parseColor("#ffff00"));
                    bmi.setTextColor(Color.parseColor("#E8E827"));
                    weight.setTextColor(Color.parseColor("#E8E827"));
                }
                if(bmival>29.9)
                {
                    //obese
                    //BmiCard.setCardBackgroundColor(Color.parseColor("#FF4500"));
                    bmi.setTextColor(Color.parseColor("#FF4500"));
                    weight.setTextColor(Color.parseColor("#FF4500"));
                }

                if(diab<=4.0)
                {
                    //DiabetesCard.setCardBackgroundColor(Color.parseColor("#00bfff"));
                    diabetes.setTextColor(Color.parseColor("#00bfff"));
                }

                if(diab>4.0 && diab<=6.0)
                {
                    //DiabetesCard.setCardBackgroundColor(Color.parseColor("#00FF7F"));
                    diabetes.setTextColor(Color.parseColor("#14C56C"));

                }
                if(diab>6.0)
                {
                    //DiabetesCard.setCardBackgroundColor(Color.parseColor("#FF4500"));
                    diabetes.setTextColor(Color.parseColor("#DF3104"));
                }
                if(systol<120.0)
                {
                    //BloodPressure.setCardBackgroundColor(Color.parseColor("#00FF7F"));
                    bloodPress.setTextColor(Color.parseColor("#00FF7F"));
                }

                if(systol>=120.0 && systol<=129.0)
                {
                    //BloodPressure.setCardBackgroundColor(Color.parseColor("#ffff00"));
                    bloodPress.setTextColor(Color.parseColor("#ffff00"));
                }
                if(systol>=130.0 && systol<=139.0)
                {
                    //BloodPressure.setCardBackgroundColor(Color.parseColor("#FF9903"));
                    bloodPress.setTextColor(Color.parseColor("#FF9903"));
                }
                if(systol>=140.0)
                {
                    //BloodPressure.setCardBackgroundColor(Color.parseColor("#FF4500"));
                    bloodPress.setTextColor(Color.parseColor("#FF4500"));
                }

                bloodP=String.valueOf(systol)+"/"+String.valueOf(diastol)+"(Sys/Dia)";
                bmi.setText(bmi1);
                bmr.setText(bmr1);
                weight.setText(weight1);
                diabetes.setText(dia1);
                bloodPress.setText(bloodP);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void backToDash(View view) {
        Intent intent=new Intent(HealthStatus.this,DashBoard.class);
        finish();
        startActivity(intent);

    }
    public void goBP(View view) {
        Intent intent=new Intent(HealthStatus.this,BP.class);
        intent.putExtra("sys",sys1);
        intent.putExtra("dias",dias1);
        intent.putExtra("Pulse",pul);
        finish();
        startActivity(intent);

    }
    public void goBMI(View view) {
        Intent intent=new Intent(HealthStatus.this,BMI.class);
        finish();
        startActivity(intent);

    }
    public void goBMR(View view) {
        Intent intent=new Intent(HealthStatus.this,BMR.class);
        finish();
        startActivity(intent);

    }
    public void goWeight(View view) {
        Intent intent=new Intent(HealthStatus.this,Weight.class);
        finish();
        startActivity(intent);

    }
    public void goDiabates(View view) {
        Intent intent=new Intent(HealthStatus.this,Diabates.class);
        intent.putExtra("dia",dia1);
        finish();
        startActivity(intent);

    }
    public void back(View view) {
        Intent intent=new Intent(HealthStatus.this,HealthStatusUpdateActivity.class);
        intent.putExtra("Age",age);
        intent.putExtra("sex",gender);
        finish();
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HealthStatus.this,DashBoard.class);
        startActivity(intent);

        finish();
    }

    public void popUp(View view) {
        pop.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pop.setVisibility(View.GONE);
            }
        },5000);
    }
}