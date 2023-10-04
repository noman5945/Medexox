package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

public class Diabates extends AppCompatActivity {
    TextView diabetesMete,condition;
    ImageView indicator;
    PhotoView photoview;

    String dia;
    double diam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabates);

        diabetesMete=(TextView)findViewById(R.id.diabetes);
        indicator=(ImageView)findViewById(R.id.indicator);
        condition=(TextView)findViewById(R.id.condi);

        Intent intent=getIntent();
        dia=intent.getStringExtra("dia");
        diam=Double.parseDouble(dia);

        diabetesMete.setText(dia);

        PhotoView photoView = (PhotoView) findViewById(R.id.imageView3);
        photoView.setImageResource(R.drawable.diacon);

        if(diam<=4.0)
        {
            indicator.setImageResource(R.drawable.btblue);
            condition.setText("Low Sugar Level");
        }
        else if(diam>4.0 && diam<=6.0)
        {
            indicator.setImageResource(R.drawable.btgreen);
            condition.setText("Normal Sugar Level");
        }
        else if(diam>6)
        {
            indicator.setImageResource(R.drawable.btred);
            condition.setText("High Sugar Level");
        }
    }

    public void goDocList(View view) {
        Intent intent=new Intent(Diabates.this,DoctListActivity.class);
        intent.putExtra("typ","Diabetes");
        finish();
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent=new Intent(Diabates.this,HealthStatus.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Diabates.this,HealthStatus.class);
        startActivity(intent);

        finish();
    }
}