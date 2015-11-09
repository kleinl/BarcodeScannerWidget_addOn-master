package com.ess.tudarmstadt.de.mwidgetexample.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ess.tudarmstadt.de.mwidgetexample.MainActivity;

/**
 * Gets called when the system reboots to reset the alarmSetter variable.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity.alarmForSurvey(context, false);
    }
}
