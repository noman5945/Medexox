package com.example.reminderapp.EmergencyCalling;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Caller extends Activity{

    //this class do the call work
    private static final int REQUEST_PHONE_CALL = 1;
    String number;

    public Caller()
    {

    }

    public Caller(String number)
    {
        this.number=number;
    }

    public void calluser(String num, Context cnt)
    {
        Intent callIntent=new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+num));
        if (ContextCompat.checkSelfPermission(cnt, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) cnt, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else {
            cnt.startActivity(callIntent);
        }
    }
}
