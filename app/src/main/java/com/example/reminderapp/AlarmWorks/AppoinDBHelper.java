package com.example.reminderapp.AlarmWorks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AppoinDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Appointment";
    public static final String TABLE_NAME="AppointmentTable";
    public static final String COL_1="ID";
    public static final String COL_2="DocName";
    public static final String COL_3="Description";
    public static final String COL_4="ProgTime";
    public static final String COL_5="ReadableTime";
    public static final String COL_6="Date";
    public static final String COL_7="Status";

    public AppoinDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDatabase="CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DocName TEXT, Description TEXT, ProgTime TEXT,ReadableTime TEXT,Date TEXT,Status TEXT)";
        db.execSQL(createDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int ID,String DocName,String desc,String ProgTime,String ReadableTime,String Date,String status)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,ID);
        contentValues.put(COL_2,DocName);
        contentValues.put(COL_3,desc);
        contentValues.put(COL_4,ProgTime);
        contentValues.put(COL_5,ReadableTime);
        contentValues.put(COL_6,Date);
        contentValues.put(COL_7,status);

        long res=database.insert(TABLE_NAME,null,contentValues);

        if(res==-1)
        {
            return false;
        }
        else
            return true;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String quary="SELECT * FROM "+TABLE_NAME;
        Cursor data=null;
        if(db!=null) {
            data = db.rawQuery(quary, null);
        }
        return  data;
    }

    public void deleteData(int row_id,Context context)
    {
        String id=String.valueOf(row_id);
        SQLiteDatabase database=this.getWritableDatabase();
        long result=database.delete(TABLE_NAME,"ID=?",new String[]{id});

        if(result==-1)
        {
            Toast.makeText(context,"Failed to delete",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show();
        }
    }

    public Cursor getCurrentAppointment(int ID)
    {

        SQLiteDatabase db=this.getReadableDatabase();
        String quary="SELECT * FROM "+TABLE_NAME+" WHERE "+COL_1+"= ' "+ID+" ' ";

        Cursor data=null;
        if(db!=null)
        {
            data=db.rawQuery(quary,null);
            data.moveToFirst();
            db.close();
        }
        return  data;
    }

    public void UpdateStatus(int ID,String stat,Context context)
    {
        String id=String.valueOf(ID);
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_7,stat);
        long res=database.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});

        if(res==-1)
        {
            Toast.makeText(context,"Not Checked",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(context,"Checked",Toast.LENGTH_LONG).show();
        }
    }

}
