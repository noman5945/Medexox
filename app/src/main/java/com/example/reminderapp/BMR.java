package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BMR extends AppCompatActivity {

    PhotoView photoview,photoview2;
    TextView bmr;
    FirebaseUser user;
    DatabaseReference databaseReference;
    String uid;
    double bmrval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_r);

        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        bmr=(TextView)findViewById(R.id.bmr);

        getData(databaseReference,uid);

        PhotoView photoView = (PhotoView) findViewById(R.id.imageView5);
        photoView.setImageResource(R.drawable.bmr2);
        PhotoView photoView2 = (PhotoView) findViewById(R.id.imageView4);
        photoView2.setImageResource(R.drawable.bmr3);
    }


    private void getData(DatabaseReference dbf,String id) {

        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bmrval=snapshot.child("HealthStatus").child(id).child("BMR").getValue(Double.class);

                String bmrv=String.valueOf(bmrval);
                bmr.setText(bmrv);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void goDocList(View view) {
        Intent intent=new Intent(BMR.this,DoctListActivity.class);
        intent.putExtra("typ","Nutritionist");
        finish();
        startActivity(intent);
    }

    public void back(View view) {
        finish();
        startActivity(new Intent(BMR.this,HealthStatus.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BMR.this,HealthStatus.class);
        startActivity(intent);

        finish();
    }
}