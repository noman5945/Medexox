package com.example.reminderapp.EmergencyCalling;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SendSMS extends Activity {
    String phnNo;
    String smsbody;

    public SendSMS()
    {

    }

    public SendSMS(String phnNo,String smsbody)
    {
        this.phnNo=phnNo;
        this.smsbody=smsbody;
    }

    public void SMSsend(String phnNo, String msg, Context cnt)
    {
        if(ContextCompat.checkSelfPermission(cnt, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) cnt, new String[]{Manifest.permission.SEND_SMS},10);
        }
        else
        {
            try{
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(phnNo,null,msg,null,null);
                Toast.makeText(cnt,"Messege sent",Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(cnt,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }
}
