package com.example.reminderapp.AlarmWorks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateStats extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AppoinDBHelper myDB=new AppoinDBHelper(context);

        int ID=intent.getExtras().getInt("ID");
        String stats="Checked";
        myDB.UpdateStatus(ID,stats,context);
    }
}
