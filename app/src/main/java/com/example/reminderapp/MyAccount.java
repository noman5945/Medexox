package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.MediaRouteActionProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.reminderapp.UserAuth.User;

public class MyAccount extends AppCompatActivity {
    TextView name,email,age,gender,utype,nameShow;
    ImageView Beck;
    Button update,del;
    FirebaseAuth mAth;
    FirebaseUser fUser;
    DatabaseReference fref;

    String user_name;
    String user_name_show;
    String user_age;
    String user_type;
    String user_email;
    String user_gender;
    String user_password;

    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        fUser=mAth.getInstance().getCurrentUser();
        uid=fUser.getUid();
        fref= FirebaseDatabase.getInstance().getReference();


        name=(TextView)findViewById(R.id.name);
        nameShow=(TextView)findViewById(R.id.name1);
        email=(TextView)findViewById(R.id.Email);
        age=(TextView)findViewById(R.id.age);
        gender=(TextView)findViewById(R.id.gender);
        utype=(TextView)findViewById(R.id.type);

        update=(Button)findViewById(R.id.updateAcc);
        del=(Button)findViewById(R.id.delAcc);

        getData(fUser,uid,fref);
    }

    private void getData(FirebaseUser user,String id,DatabaseReference databaseReference) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_name=snapshot.child("Users").child(id).child("username").getValue(String.class);
                user_name_show=snapshot.child("Users").child(id).child("username").getValue(String.class);
                user_age=snapshot.child("Users").child(id).child("age").getValue(String.class);
                user_type=snapshot.child("Users").child(id).child("Usertype").getValue(String.class);
                user_email=snapshot.child("Users").child(id).child("email").getValue(String.class);
                user_gender=snapshot.child("Users").child(id).child("gender").getValue(String.class);
                user_password=snapshot.child("Users").child(id).child("password").getValue(String.class);

                name.setText(user_name);
                nameShow.setText(user_name_show);
                age.setText(user_age);
                utype.setText(user_type);
                email.setText(user_email);
                gender.setText(user_gender);
                Toast.makeText(MyAccount.this,"Success.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyAccount.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Update(View view) {
            Intent updateIntent=new Intent(MyAccount.this,UpdateAccActivity.class);
            updateIntent.putExtra("name",user_name);
            updateIntent.putExtra("age",user_age);
            updateIntent.putExtra("email",user_email);
            updateIntent.putExtra("gender",user_gender);
            updateIntent.putExtra("pas",user_password);
            finish();
            startActivity(updateIntent);
    }

    public void Delete(View view) {
        DatabaseReference UserRef=FirebaseDatabase.getInstance().getReference("Users").child(uid);
        DatabaseReference HealthRef=FirebaseDatabase.getInstance().getReference("HealthStatus").child(uid);

        UserRef.removeValue();
        HealthRef.removeValue();

        fUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MyAccount.this,"The account has been deleted.",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MyAccount.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        finish();
        startActivity(new Intent(MyAccount.this,DataBaseActivity.class));
    }

    public void GoBack(View view) {
        finish();
        startActivity(new Intent(MyAccount.this,DashBoard.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyAccount.this,DashBoard.class);
        startActivity(intent);

        finish();
    }
}