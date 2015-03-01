package ru.shww.wakeupproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by shww-big on 01.03.2015.
 */
public class NewAlarmReceiver extends WakefulBroadcastReceiver {

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private static int REPEAT_TIME = 1000 * 60;
    private static String LOG_TAG = NewAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, WakeUpService.class);
        startWakefulService(context, service);

        Log.d(LOG_TAG, "Alarm received");

    }

    public void setAlarm(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NewAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), REPEAT_TIME, alarmIntent);

        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Log.d(LOG_TAG, "Alarm set");
    }

    public void cancelAlarm(Context context){
        if (alarmManager != null){
            alarmManager.cancel(alarmIntent);

            Log.d(LOG_TAG, "Alarm cancel");
        }

        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);



    }
}
