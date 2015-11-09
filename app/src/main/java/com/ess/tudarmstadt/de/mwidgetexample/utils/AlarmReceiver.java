package com.ess.tudarmstadt.de.mwidgetexample.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ess.tudarmstadt.de.mwidgetexample.MainActivity;

import java.util.Calendar;
import java.util.Random;


/*
 * Receives the alarm from the MainActivity and sets up a notification. passes it on to the second
 * alarm receiver.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static int counter = (MainActivity.prefs.getInt("RUNTIME", 0)) * 7;
    @Override
    public void onReceive(Context context, Intent intent) {
        counter--;
        Log.e("test", String.valueOf(counter));
        if (counter >= 0) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intentNew = new Intent(context, com.ess.tudarmstadt.de.mwidgetexample.utils.AlarmReceiver2.class);
            int time = intent.getIntExtra("time", -1);
            int hour = 0;
            int minute = 0;
            Random r = new Random();
            minute = r.nextInt(60);
            switch (time) {
                case 0:
                    hour = 9;
                    minute = r.nextInt(60);
                    break;
                case 1:
                    hour = r.nextInt(2) + 10;
                    if (hour == 10) {
                        minute = r.nextInt(31) + 30;
                    } else if (hour == 11) {
                        minute = r.nextInt(31);
                    }
                    break;
                case 2:
                    hour = 12;
                    minute = r.nextInt(60);
                    break;
                case 3:
                    hour = r.nextInt(2) + 13;
                    if (hour == 13) {
                        minute = r.nextInt(31) + 30;
                    } else if (hour == 14) {
                        minute = r.nextInt(31);
                    }
                    break;
                case 4:
                    hour = 15;
                    minute = r.nextInt(60);
                    break;
                case 5:
                    hour = r.nextInt(2) + 16;
                    if (hour == 16) {
                        minute = r.nextInt(31) + 30;
                    } else if (hour == 17) {
                        minute = r.nextInt(31);
                    }
                    break;
                case 6:
                    hour = 18;
                    minute = r.nextInt(60);
            }
            intentNew.putExtra("time", time);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, time, intentNew, PendingIntent.FLAG_UPDATE_CURRENT);
            String usage = intent.getStringExtra("usage");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MINUTE, minute);
            if (usage.equals("create")) {
                if (cal.get(Calendar.HOUR_OF_DAY) <= hour) {
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                            cal.getTimeInMillis(),
                            600000, pendingIntent);
                } else {
                    alarmManager.cancel(pendingIntent);
                }
            } else if (usage.equals("delete")) {
                AlarmReceiver2.counter = 3;
                alarmManager.cancel(pendingIntent);
            }
        }
    }
}