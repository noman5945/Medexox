package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.core.app.NotificationManagerCompat;

import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reminderapp.AlarmWorks.ManageAlarm;
import com.example.reminderapp.AlarmWorks.MedicineDBHelper;
import com.example.reminderapp.AlarmWorks.ReadableTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
public class Medication extends AppCompatActivity {

    MedicineDBHelper mDatabaseHelper;
    private int notiId = 1;
    private Button setBtn, cancelBtn;
    private EditText medicineName;
    private TimePicker timePicker;
    Calendar calendar;
    int reqCode;
    private NotificationManagerCompat notificationManager;

    int day,year1,month1;
    Calendar cal;
    DateFormat dateFormat;
    String date;

    long alarmStartTime;
    String prTime,readable;

    TextView EndDate;
    ManageAlarm manageAlarm=new ManageAlarm();
    Random rand = new Random();
    ReadableTime readableTime=new ReadableTime();

    String newEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        setBtn = findViewById(R.id.setButton);
        cancelBtn = findViewById(R.id.cancelButton);
        medicineName = findViewById(R.id.pill_name);
        timePicker = findViewById(R.id.timePicker);
        EndDate=findViewById(R.id.endDate);

        mDatabaseHelper = new MedicineDBHelper(this);
        notificationManager = NotificationManagerCompat.from(this);

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEntry = medicineName.getText().toString();
                reqCode = rand.nextInt(100);
                //Intent intentAlarm = new Intent(Medication.this, Alarm.class);
                //intentAlarm.putExtra("ID",reqCode);

                //PendingIntent alarmIntent = PendingIntent.getBroadcast(Medication.this, reqCode, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
                //AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                if (medicineName.length() != 0) {
                    int hour = timePicker.getCurrentHour();
                    int min = timePicker.getCurrentMinute();

                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, min);
                    calendar.set(Calendar.SECOND, 0);
                    alarmStartTime = calendar.getTimeInMillis();

                    prTime=hour+":"+min; //program time
                    readable=readableTime.convertTime(hour,min); //human readable time

                    //alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                    //newEntry = newEntry+" "+readableTime.convertTime(hour,min);
                    //AddData(newEntry);

                    //addTime(time);
                    //medicineName.setText("");
                    //finish();
                    //Intent intent = new Intent(Medication.this, AddMedication.class);
                    //startActivity(intent);

                } else
                    toastMessage("You must put something in the text field!");
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Medication.this, AddMedication.class);
                startActivity(intent);
            }
        });
    }

    private void AddData(String ID,String newEntry,String progTime,String realTime,String endDate) {
        boolean insertData = mDatabaseHelper.addData(ID,newEntry,progTime,realTime,endDate);
        if(insertData)
            toastMessage("Data Successfully Inserted!");
        else toastMessage("Something went wrong");
    }

    /*
    private void addTime(String time) {
        boolean insertData = mDatabaseHelper.addData(time);
        if(insertData)
            toastMessage("Data Successfully Inserted!");
        else toastMessage("Something went wrong");
    }
    */

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void back(View view) {
        finish();
        startActivity(new Intent(Medication.this,AddMedication.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Medication.this,AddMedication.class);
        startActivity(intent);

        finish();
    }

    public void SetDate(View view) {
        cal=Calendar.getInstance();
        dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        DatePickerDialog GetDate=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate=Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);

                date=dateFormat.format(newDate.getTime());      //readable doze end date
                EndDate.setText(dateFormat.format(newDate.getTime()));

                day=dayOfMonth;
                year1=year;
                month1=month;
            }
        },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        GetDate.show();
    }

    public void SetAll(View view) {

        if(date.isEmpty())
        {
            EndDate.setError("Please set the doze ending date");
            return;
        }
        if(medicineName.getText().toString().isEmpty())
        {
            medicineName.setError("Please enter a medicine name.");
            medicineName.requestFocus();
            return;
        }

        AddData(String.valueOf(reqCode),newEntry,prTime,readable,date);
        manageAlarm.MedicineAlarm(reqCode,alarmStartTime,Medication.this);
    }
}