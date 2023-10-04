package com.example.reminderapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.reminderapp.AlarmWorks.ManageAlarm;
import com.example.reminderapp.AlarmWorks.MedicineDBHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Alarm extends BroadcastReceiver {


    /*@Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Image clicked", Toast.LENGTH_SHORT).show();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(1);
    }*/

    private static final String CHANNEL_ID = "Sample Channel";


    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("notificationId", 0);
        ManageAlarm manageAlarm=new ManageAlarm();

        DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        MedicineDBHelper data=new MedicineDBHelper(context);

        Cursor cursor;
        String ID=intent.getStringExtra("id");
        String medname="",lastDay="";
        String message = intent.getStringExtra("message");

        Calendar calendar=Calendar.getInstance();
        int h=calendar.get(Calendar.HOUR_OF_DAY);
        int m=calendar.get(Calendar.MINUTE);

        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year,month,day);
        String date=dateFormat.format(calendar.getTime());

        cursor=data.getCurrentMed(ID);
        int medIndex=cursor.getColumnIndex("Name");
        int medLastday=cursor.getColumnIndex("EndDate");
        Intent mainIntent = new Intent(context, Medication.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if(cursor != null && cursor.moveToFirst())
        {
            message=cursor.getString(medIndex);
            lastDay=cursor.getString(medLastday);
            cursor.close();
        }

        if(lastDay.equals(date))
        {
            message = message + "\n" + " doze days are ended";
            manageAlarm.cancelMedAlarm(Integer.parseInt(ID),context);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channel_name = "My Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channel_name, importance);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("It's time to take your medicines")
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        notificationManager.notify(notificationId, builder.build());

    }
}
