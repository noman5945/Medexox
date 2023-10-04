package com.example.reminderapp.AlarmWorks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.reminderapp.Alarm;

import java.util.Calendar;

public class ManageAlarm {
    int hour,min;
    int year,month,day;
    long miliTime;

    public ManageAlarm()
    {

    }

    public ManageAlarm(int hour,int min,int year,int month,int day)
    {
        this.hour=hour;
        this.min=min;
        this.day=day;
        this.month=month;
        this.year=year;
    }

    public void MedicineAlarm(int requestCode,long milisec ,Context context)
    {
        Intent intentAlarm = new Intent(context, Alarm.class);
        intentAlarm.putExtra("id",String.valueOf(requestCode));

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, requestCode, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,milisec,AlarmManager.INTERVAL_DAY,alarmIntent);

        Toast.makeText(context,"Medicine Reminder set!",Toast.LENGTH_SHORT).show();
    }

    public void AppoinmentAlarm(int requestCode,int hour, int min, int year, int month, int day, Context context)
    {
        Calendar setCal=Calendar.getInstance();
        setCal.set(year,month,day,hour,min,0);
        miliTime=setCal.getTimeInMillis();

        Intent alarmIntent=new Intent(context,BroadCaster.class);
        alarmIntent.putExtra("reqCode",requestCode);
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,requestCode,alarmIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,miliTime,pendingIntent);

        Toast.makeText(context,"Appointment Reminder set",Toast.LENGTH_SHORT).show();
    }

    public void CancelAlarm(int reqCode,Context context)
    {
        Intent alarmIntent=new Intent(context,BroadCaster.class);
        alarmIntent.putExtra("reqCode",reqCode);
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,reqCode,alarmIntent,PendingIntent.FLAG_NO_CREATE);

        if(pendingIntent !=null && alarmManager !=null)
        {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(context,"Alarm Removed!",Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelMedAlarm(int requestCode,Context context)
    {
        Intent intentAlarm = new Intent(context, Alarm.class);
        intentAlarm.putExtra("id",String.valueOf(requestCode));

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, requestCode, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(alarmIntent!=null && alarmManager!=null)
        {
            alarmManager.cancel(alarmIntent);
            Toast.makeText(context,"Alarm Removed!",Toast.LENGTH_SHORT).show();
        }
    }
}
