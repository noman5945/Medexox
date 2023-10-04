package com.example.reminderapp.AlarmWorks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MedicineDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";

    private static final String TABLE_NAME = "Alarm";
    private static final String COL1 = "ID";
    private static final String COL2 = "Name";
    private static final String COL3 = "PrTime";
    private static final String COL4 = "RealTime";
    private static final String COL5 = "EndDate";



    public MedicineDBHelper(Context context) {
        super(context, TABLE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +    COL2 + " TEXT, "
                                                                                                       +    COL3 + " TEXT, "
                                                                                                       +    COL4 + " TEXT, "
                                                                                                       +    COL5 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String ID,String item,String progTime,String realTime,String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, ID);
        contentValues.put(COL2, item);
        contentValues.put(COL3, progTime);
        contentValues.put(COL4, realTime);
        contentValues.put(COL5, endDate);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        /*if(result == -1)
            return false;
        else return true;*/

        return result != -1;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor data=null;
        if(db!=null) {
            data = db.rawQuery(query, null);
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

    public Cursor getCurrentMed(String ID)
    {

        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+COL1+"= ' "+ID+" ' ";

        Cursor data=null;
        if(db!=null)
        {
            data=db.rawQuery(query,null);
            data.moveToFirst();
            db.close();
        }
        return  data;
    }
}
