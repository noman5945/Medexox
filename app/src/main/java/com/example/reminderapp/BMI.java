package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BMI extends AppCompatActivity {
    TextView cat,b_m_i,weight_,height_,age1;
    FirebaseUser user;
    PhotoView photoview;
    DatabaseReference databaseReference;
    String uid,age2,gender;
    double bmival,heightval,weightval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_i);

        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();

        databaseReference= FirebaseDatabase.getInstance().getReference();

        b_m_i=(TextView)findViewById(R.id.bmi);
        cat =(TextView) findViewById(R.id.catagory);
        age1= (TextView) findViewById(R.id.Age);
        weight_=(TextView)findViewById(R.id.weight);
        height_=(TextView)findViewById(R.id.height);

        getData(databaseReference,uid);

        PhotoView photoView = (PhotoView) findViewById(R.id.catchart);
        photoView.setImageResource(R.drawable.bmichartint);

    }
    private void getData(DatabaseReference dbf,String id) {

        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bmival = snapshot.child("HealthStatus").child(id).child("BMI").getValue(Double.class);
                weightval=snapshot.child("HealthStatus").child(id).child("weight").getValue(Double.class);
                age2 = snapshot.child("Users").child(id).child("age").getValue(String.class);
                heightval = snapshot.child("HealthStatus").child(id).child("hight").getValue(Double.class);
                gender = snapshot.child("Users").child(id).child("gender").getValue(String.class);

                String bmi1 = String.valueOf(bmival);
                String weight1 = String.valueOf(weightval);
                String height1 = String.valueOf(heightval);

                if (bmi1.equals("0.0")) {

                    //BmiCard.setCardBackgroundColor(Color.parseColor("#00bfff"));
                    //BmrCard.setCardBackgroundColor(Color.parseColor("#00bfff"));
                    //WeightCard.setCardBackgroundColor(Color.parseColor("#00bfff"));
                    //DiabetesCard.setCardBackgroundColor(Color.parseColor("#00bfff"));
                    cat.setText("Null");


                }

                if (bmival > 0.0 && bmival <= 18.5) {
                    //underweight
                    Toast.makeText(BMI.this,bmi1,Toast.LENGTH_LONG).show();
                    cat.setText("Underweight");

                }
                if (bmival > 18.5 && bmival <= 24.5) {
                    //normal weight
                    Toast.makeText(BMI.this,bmi1,Toast.LENGTH_LONG).show();
                    cat.setText("Normal");
                }
                if (bmival > 24.5 && bmival <= 29.9) {
                    //over weight
                    //BmiCard.setCardBackgroundColor(Color.parseColor("#ffff00"));
                    Toast.makeText(BMI.this,bmi1,Toast.LENGTH_LONG).show();
                    cat.setText("Overweight");
                }
                if (bmival > 29.9) {
                    //obese
                    Toast.makeText(BMI.this,bmi1,Toast.LENGTH_LONG).show();
                    cat.setText("Obese");
                }
                b_m_i.setText(bmi1);
                weight_.setText(weight1);
                height_.setText(height1);
                age1.setText(age2);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BMI.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

    public void goDocList(View view) {
        Intent intent=new Intent(BMI.this,DoctListActivity.class);
        intent.putExtra("typ","Nutritionist");
        finish();
        startActivity(intent);
    }

    public void back(View view) {
        finish();
        startActivity(new Intent(BMI.this,HealthStatus.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BMI.this,HealthStatus.class);
        startActivity(intent);

        finish();
    }
}