package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reminderapp.AlarmWorks.AppoinDBHelper;
import com.example.reminderapp.AlarmWorks.ManageAlarm;
import com.example.reminderapp.AlarmWorks.ReadableTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Appoinment extends AppCompatActivity {

    Button set1,set2,cancel1,cancel2;
    TextView setTime,setDate,doctor,description;


    AppoinDBHelper myDB;

    Calendar cal;
    int year1,month1,day;
    int hour,min,sec;
    long milisec;

    DateFormat dateFormat;
    String time1,readable,date;

    ReadableTime readableTime=new ReadableTime();
    ManageAlarm manageAlarm=new ManageAlarm();

    Random rand=new Random();
    int requestCode;
    String Doctor,Description,stats;

    boolean isInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment);

        set1=(Button)findViewById(R.id.setButton1);
        set2=(Button)findViewById(R.id.setButton2);
        cancel1=(Button)findViewById(R.id.cancelButton1);
        cancel2=(Button)findViewById(R.id.cancelButton2);

        myDB=new AppoinDBHelper(Appoinment.this);

        doctor=(TextView)findViewById(R.id.doctorName);
        description=(TextView)findViewById(R.id.doctorDescription);
        setTime=(TextView)findViewById(R.id.time);
        setDate=(TextView)findViewById(R.id.date);
    }

    public void back(View view) {
        finish();
        startActivity(new Intent(Appoinment.this,AddAppoinment.class));
    }

    public void setTime(View view) {
        Calendar cal2=Calendar.getInstance();
        int h=cal2.get(Calendar.HOUR_OF_DAY);
        int m=cal2.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour=hourOfDay;
                min=minute;
                sec=0;
                Calendar newTime=Calendar.getInstance();
                newTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                newTime.set(Calendar.MINUTE,minute);
                newTime.set(Calendar.SECOND,0);
                milisec=newTime.getTimeInMillis();
                readable=readableTime.convertTime(hour,min); //this will be added at COL_5
                time1=hour+":"+min+":"+sec; //this will be added at COL_4
                setTime.setText(readable);
            }
        },h,m,false);
        timePickerDialog.show();
    }

    public void setDat(View view) {
        cal=Calendar.getInstance();
        dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        DatePickerDialog GetDate=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate=Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                date=dateFormat.format(newDate.getTime()); //this will add at database COL_6
                setDate.setText(dateFormat.format(newDate.getTime()));
                day=dayOfMonth;
                year1=year;
                month1=month;
            }
        },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        GetDate.show();
    }

    public void removeDate(View view) {
        setDate.setText("");
    }

    public void removeTime(View view) {
        setTime.setText("");
    }

    public void setAppointment(View view) {


        requestCode=rand.nextInt(100);              //this will be added at database COL_1
        stats="Pending";                                   //this will be added at database COL_7
        Doctor=doctor.getText().toString();                //this will be added at database COL_2
        Description=description.getText().toString();      //this will be added at database COL_3

        if(Doctor.isEmpty())
        {
            doctor.setError("Doctor field is empty.");
            doctor.requestFocus();
            return;
        }

        if(Description.isEmpty())
        {
            description.setText("");
        }

        if(setTime.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Time is empty!",Toast.LENGTH_LONG).show();
            return;
        }
        if(setDate.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Date is empty!",Toast.LENGTH_LONG).show();
            return;
        }

        String settle="Time="+time1+" "+day+" "+year1+" "+month1+" Date: "+date+" Readable: "+readable;
        //Toast.makeText(this,settle,Toast.LENGTH_LONG).show();

        isInserted=myDB.insertData(requestCode,Doctor,Description,time1,readable,date,stats);

        if(isInserted==true)
        {
            Toast.makeText(this,"Added to database",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Not added",Toast.LENGTH_LONG).show();
        }

        manageAlarm.AppoinmentAlarm(requestCode,hour - 3,min,year1,month1,day,Appoinment.this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Appoinment.this,AddAppoinment.class);
        startActivity(intent);
        finish();
    }

}
