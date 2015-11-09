package com.ess.tudarmstadt.de.mwidgetexample.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.ess.tudarmstadt.de.mwidgetexample.MainActivity;
import com.ess.tudarmstadt.de.mwidgetexample.R;

/**
 * Second alarm receiver. Gets the Alarm from the first one. Generates the notification. Counts for the notification to only appear 3 times.
 */
// second receiver to be called 3 times after the first one was.
public class AlarmReceiver2 extends BroadcastReceiver {
    public static int counter = 3;
    @Override
    public void onReceive(Context context, Intent intent) {
        counter--;
        NotificationManager mNM;
        mNM = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int time = intent.getIntExtra("time", -1);
        if (counter != 0) {
            Intent openSurvey = new Intent(context, MainActivity.class);
            openSurvey.setAction("com.ess.tudarmstadt.de.mwidgetexample.openSurvey");
            Bundle extras = new Bundle();
            extras.putInt("time", 0);
            extras.putString("usage", Constants.Survey_Intent);
            openSurvey.putExtras(extras);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, openSurvey, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            NotificationCompat.Builder builder2 = new NotificationCompat.Builder(context);


            Notification notification;
            notification = builder.setContentIntent(pIntent)
                    .setSmallIcon(R.drawable.survey)
                    .setAutoCancel(true)
                    .setContentTitle(context.getResources().getString(R.string.survey) + String.valueOf(time + 1))
                    .setVibrate(new long[] { 1000, 1000 })
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .build();
            notification.contentIntent = pIntent;

            if (time == 0 && counter == 3) {
                Notification notification1 = builder2
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("BarcodeScannerAddon")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getResources().getString(R.string.acc)))
                        .build();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    notification1.priority = Notification.PRIORITY_MAX;
                }
                mNM.notify(2, notification1);
            }

            mNM.notify(1, notification);
        } else {
            mNM.cancelAll();
            Intent intentNew = new Intent(context, AlarmReceiver.class);
            intentNew.putExtra("time", time);
            intentNew.putExtra("usage", "delete");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intentNew, PendingIntent.FLAG_UPDATE_CURRENT);
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }

    }
}
