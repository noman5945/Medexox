package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;




public class MainActivity extends AppCompatActivity {

   // Animation logo_up_var;
    private static int SPLASH_SCREEN =5000 ;

    ImageView logo_image ;
    TextView logo_text ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //animation
        Animation logo_up_var = AnimationUtils.loadAnimation(this, R.anim.logo_up);

        //hooks
        logo_text = findViewById(R.id.logo_text);
        logo_image = findViewById(R.id.logo_image);


        logo_image.setAnimation(logo_up_var);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,DataBaseActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }

}