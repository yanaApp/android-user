package com.icaboalo.yana.presentation.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.main.MainActivity;

/**
 * Created by icaboalo on 05/10/16.
 */

public class LunchReceiver extends BroadcastReceiver {

    public static int id = 4;

    @Override
    public void onReceive(Context context, Intent intent) {
        getNotification(context);
    }

    void getNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeatingIntent = new Intent(context, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //        TODO -> add texts
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setTicker("Saludos de Yana! :)")
                .setSmallIcon(R.drawable.yana_green_notification)
                .setContentTitle("Hora de comer")
                .setContentText("")
                .setAutoCancel(true);

        notificationManager.notify(id, notificationBuilder.build());
    }
}
