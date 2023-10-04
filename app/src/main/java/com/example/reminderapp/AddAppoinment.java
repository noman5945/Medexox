package com.example.reminderapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminderapp.AlarmWorks.AppoinAdapter;
import com.example.reminderapp.AlarmWorks.AppoinDBHelper;
import com.example.reminderapp.AlarmWorks.AppoinItemInfo;
import com.example.reminderapp.AlarmWorks.ManageAlarm;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import java.util.ArrayList;

public class AddAppoinment extends AppCompatActivity {

    RecyclerView rcl2;
    RecyclerView.LayoutManager mlayout;

    AppoinDBHelper myDB;
    Cursor cursor;

    AppoinAdapter appoinAdapter;
    ArrayList<AppoinItemInfo> appoinItemInfos=new ArrayList<>();
    ArrayList<Integer> appoinID=new ArrayList<>();
    String docName;
    String desc;
    String readableTime;
    String Date;
    String status;
    ManageAlarm manageAlarm=new ManageAlarm();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appoinment);

        rcl2=(RecyclerView)findViewById(R.id.rcl2);

        myDB=new AppoinDBHelper(AddAppoinment.this);
        storeDataInArrays(AddAppoinment.this);
    }

    public void storeDataInArrays(Context context)
    {

        cursor=myDB.getAllData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(AddAppoinment.this,"No data in database",Toast.LENGTH_LONG).show();
        }
        else
        {
            appoinItemInfos.clear();
            while (cursor.moveToNext())
            {
                appoinID.add(cursor.getInt(0));
                int id=cursor.getInt(0);
                docName=cursor.getString(1);
                desc=cursor.getString(2);
                readableTime=cursor.getString(4);
                Date=cursor.getString(5);
                status=cursor.getString(6);
                appoinItemInfos.add(new AppoinItemInfo(docName,desc,readableTime,Date,status,id,R.drawable.docface));
            }

            rcl2.setHasFixedSize(true);
            mlayout=new LinearLayoutManager(context);
            appoinAdapter=new AppoinAdapter(appoinItemInfos);
            rcl2.setLayoutManager(mlayout);
            rcl2.setAdapter(appoinAdapter);

            ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(rcl2);

        }
    }


    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos=viewHolder.getAdapterPosition();
            removeData(appoinItemInfos,AddAppoinment.this,appoinAdapter,pos);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(AddAppoinment.this,R.color.orenge_red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_remove_circle_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };




    public void removeData(ArrayList<AppoinItemInfo> itemInfos,Context context,AppoinAdapter adapter,int pos)
    {
        int id=appoinID.get(pos);
        myDB.deleteData(id,context);
        manageAlarm.CancelAlarm(id,context);
        itemInfos.remove(pos);
        adapter.notifyItemRemoved(pos);
        adapter.notifyItemRangeChanged(pos,adapter.getItemCount());

    }

    public void Appoin(View view) {
        finish();
        startActivity(new Intent(AddAppoinment.this,Appoinment.class));
    }

    public void back(View view) {
        finish();
        startActivity(new Intent(AddAppoinment.this,DashBoard.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddAppoinment.this,DashBoard.class);
        startActivity(intent);
        finish();
    }
}
