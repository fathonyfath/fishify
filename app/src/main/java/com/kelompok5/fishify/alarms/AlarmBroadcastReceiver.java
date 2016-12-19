package com.kelompok5.fishify.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by bradhawk on 12/20/2016.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    public static void setAlarms(Context context) {
        cancelAlarms(context);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar now = Calendar.getInstance();
        Calendar later = Calendar.getInstance();
        later.set(Calendar.HOUR_OF_DAY, 15);
        if(now.getTimeInMillis() > later.getTimeInMillis()) later.add(Calendar.DATE, 1);

        PendingIntent pendingIntent = createPendingIntent(context);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, later.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void cancelAlarms(Context context) {
        PendingIntent pendingIntent = createPendingIntent(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public static PendingIntent createPendingIntent(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra("Id", 0);

        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
