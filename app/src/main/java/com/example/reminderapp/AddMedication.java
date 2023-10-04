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
import com.example.reminderapp.AlarmWorks.AppoinItemInfo;
import com.example.reminderapp.AlarmWorks.ManageAlarm;
import com.example.reminderapp.AlarmWorks.MedicineAdapter;
import com.example.reminderapp.AlarmWorks.MedicineDBHelper;
import com.example.reminderapp.AlarmWorks.MedicineInfo;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AddMedication extends AppCompatActivity {

    RecyclerView rcl;
    RecyclerView.LayoutManager mlayout;

    MedicineDBHelper medDBHelper;
    Cursor cursor;

    MedicineAdapter medicineAdapter;
    ArrayList<MedicineInfo> medicineInfos = new ArrayList<>();

    ArrayList<Integer> medID = new ArrayList<>();
    String medName;
    String readableTime;
    String date;
    ManageAlarm manageAlarm=new ManageAlarm();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        rcl=(RecyclerView)findViewById(R.id.rcl);

        medDBHelper = new MedicineDBHelper(AddMedication.this);
        storeDataInArrays(AddMedication.this);
    }

    public void storeDataInArrays(Context context)
    {

        cursor = medDBHelper.getAllData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(AddMedication.this,"No data in database",Toast.LENGTH_LONG).show();
        }
        else
        {
            medicineInfos.clear();
            while (cursor.moveToNext())
            {
                medID.add(cursor.getInt(0));
                int id=cursor.getInt(0);
                medName=cursor.getString(1);
                readableTime=cursor.getString(3);
                date=cursor.getString(4);
                medicineInfos.add(new MedicineInfo(medName,readableTime,date,id,R.drawable.med));
            }

            rcl.setHasFixedSize(true);
            mlayout=new LinearLayoutManager(context);
            medicineAdapter=new MedicineAdapter(medicineInfos);
            rcl.setLayoutManager(mlayout);
            rcl.setAdapter(medicineAdapter);

            ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(rcl);

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
            removeData(medicineInfos,AddMedication.this,medicineAdapter,pos);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(AddMedication.this,R.color.orenge_red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_remove_circle_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public void removeData(ArrayList<MedicineInfo> itemInfos,Context context,MedicineAdapter adapter,int pos)
    {
        int id=medID.get(pos);
        medDBHelper.deleteData(id,context);
        manageAlarm.CancelAlarm(id,context);
        itemInfos.remove(pos);
        adapter.notifyItemRemoved(pos);
        adapter.notifyItemRangeChanged(pos,adapter.getItemCount());

    }

    /*private static final String TAG = "AddMedication";

    MedicineDBHelper mMedicineDBHelper;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        mListView = findViewById(R.id.rcl);
        mMedicineDBHelper = new MedicineDBHelper(this);
        populateListView();
    }

    private void populateListView() {
        Cursor data = mMedicineDBHelper.getData();
        //ArrayList<HashMap<String,String>> listData = new ArrayList<>();
        ArrayList<String> listData = new ArrayList<>();
        String titleArray = null;
        String subItemArray = null;
        while(data.moveToNext()) {
            //HashMap<String,String> datum = new HashMap<>();
            //datum.put("Medicine Name", titleArray);
            //datum.put("Time", subItemArray);
            listData.add(data.getString(1));
            //listData.add(data.getString(2));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_remind_medicine_format, R.id.title, listData);

        //SimpleAdapter adapter = new SimpleAdapter(this, listData, android.R.layout.simple_list_item_2, new String[] {"Medicine Name", "Time"}, new int[] {R.id.pill_name, R.id.timePicker});
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = adapterView.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = mMedicineDBHelper.getItemID(name);
                int itemID = -1;
                while(data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if(itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(AddMedication.this, EditDataActivityMedicine.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    startActivity(editScreenIntent);
                }
                else
                    toastMessage("No ID associated with that name");
            }
        });

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }*/

    public void Medic(View view) {
        finish();
        startActivity(new Intent(AddMedication.this, Medication.class));
    }

    public void back(View view) {
        finish();
        startActivity(new Intent(AddMedication.this,DashBoard.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddMedication.this,DashBoard.class);
        startActivity(intent);
        finish();
    }
}
