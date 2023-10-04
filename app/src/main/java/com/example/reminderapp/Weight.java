package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reminderapp.calculators.IdealWeight;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Weight extends AppCompatActivity {
    PhotoView photoview;
    TextView cat,weight_,height_,age1,IdealWeight;
    FirebaseUser user;
    DatabaseReference databaseReference;
    String uid,age2,gender;
    double heightval,weightval,idealWt;
    ImageView setNotMale,setMale,setNotFemale,setFemale;

    IdealWeight idealWeight=new IdealWeight();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);


        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();

        databaseReference= FirebaseDatabase.getInstance().getReference();

        age1= (TextView) findViewById(R.id.Age);
        weight_=(TextView)findViewById(R.id.weight);
        height_=(TextView)findViewById(R.id.height);
        IdealWeight=(TextView)findViewById(R.id.idealWeight);

        setNotMale=(ImageView)findViewById(R.id.male2);
        setMale=(ImageView)findViewById(R.id.male);

        setNotFemale=(ImageView)findViewById(R.id.female2);
        setFemale=(ImageView)findViewById(R.id.female);

        getData(databaseReference,uid);

        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        photoView.setImageResource(R.drawable.weightchart);
    }



    private void getData(DatabaseReference dbf,String id) {

        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                weightval=snapshot.child("HealthStatus").child(id).child("weight").getValue(Double.class);
                age2 = snapshot.child("Users").child(id).child("age").getValue(String.class);
                heightval = snapshot.child("HealthStatus").child(id).child("hight").getValue(Double.class);
                gender = snapshot.child("Users").child(id).child("gender").getValue(String.class);
             
                idealWt=idealWeight.getIdealWeight(heightval,gender);

                String weight1 = String.valueOf(weightval);
                String height1 = String.valueOf(heightval);
                String idealWt1=String.valueOf(idealWt);

                weight_.setText(weight1);
                height_.setText(height1);
                IdealWeight.setText(idealWt1);
                age1.setText(age2);

                if(gender.equals("Male")||gender.equals("male"))
                {
                    setMale.setVisibility(View.VISIBLE);
                    setNotFemale.setVisibility(View.VISIBLE);
                }
                else if(gender.equals("Female")||gender.equals("female"))
                {
                    setNotMale.setVisibility(View.VISIBLE);
                    setFemale.setVisibility(View.VISIBLE);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Weight.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }



    public void back(View view) {
        finish();
        startActivity(new Intent(Weight.this,HealthStatus.class));
    }

    public void goDocList(View view) {
        Intent intent=new Intent(Weight.this,DoctListActivity.class);
        intent.putExtra("typ","Nutritionist");
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Weight.this,HealthStatus.class);
        startActivity(intent);

        finish();
    }
}