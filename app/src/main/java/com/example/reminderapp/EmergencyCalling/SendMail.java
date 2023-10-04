package com.example.reminderapp.EmergencyCalling;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SendMail extends Activity {
    String email;
    public SendMail()
    {

    }

    public SendMail(String email)
    {
        this.email=email;
    }

    public void Send(String emailList, String subject,String msg,Context cnt)
    {
        String[] recipents=emailList.split(",");

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipents);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,msg);

        intent.setType("message/rfc822");

        try {
            cnt.startActivity(Intent.createChooser(intent, "Choose an email client"));
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(cnt,"No email clients were installed",Toast.LENGTH_LONG).show();
        }
    }
}
