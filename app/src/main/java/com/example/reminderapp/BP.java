package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BP extends AppCompatActivity {
    FirebaseUser user;
    DatabaseReference databaseReference;
    String uid;
    String systole,diastole,pulse;
    TextView syst,diast,Condition;
    ImageView highpress,lowpress,normpress;
    PhotoView photoview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_p);

        syst=(TextView)findViewById(R.id.sys);
        diast=(TextView)findViewById(R.id.dias);
        Condition=(TextView)findViewById(R.id.condition);

        highpress=(ImageView)findViewById(R.id.bphigh);
        lowpress=(ImageView)findViewById(R.id.bpnill);
        normpress=(ImageView)findViewById(R.id.bpnormal);


        Intent intent=getIntent();
        systole=intent.getStringExtra("sys");
        diastole=intent.getStringExtra("dias");
        pulse=intent.getStringExtra("Pulse");

        double sys=Double.parseDouble(systole);
        double dia=Double.parseDouble(diastole);

        setCondition(sys, dia);

        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();

        databaseReference= FirebaseDatabase.getInstance().getReference();

        syst.setText(systole);
        diast.setText(diastole);


        PhotoView photoView = (PhotoView) findViewById(R.id.catchart);
        photoView.setImageResource(R.drawable.bpstageii);

        

    }

    private void setCondition(double sys,double dia) {
        if(sys<120.0)
        {
            lowpress.setVisibility(View.VISIBLE);
            Condition.setText("Low Blood Pressure");
        }
        else if(sys>=120.0 && sys<129.0)
        {
            normpress.setVisibility(View.VISIBLE);
            Condition.setText("Normal Blood Pressure");
        }
        else if(sys>=130.0 && sys<139.0)
        {
            highpress.setVisibility(View.VISIBLE);
            Condition.setText("High Blood Pressure");
        }
        else if(sys>=140.0)
        {
            highpress.setVisibility(View.VISIBLE);
            Condition.setText("Very High Blood Pressure");
        }
    }

    public void goDocList(View view) {
        Intent intent=new Intent(BP.this,DoctListActivity.class);
        intent.putExtra("typ","Cardiac");
        finish();
        startActivity(intent);
    }

    public void back(View view) {
        finish();
        startActivity(new Intent(BP.this,HealthStatus.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BP.this,HealthStatus.class);
        startActivity(intent);

        finish();
    }
}