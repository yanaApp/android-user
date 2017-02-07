package com.icaboalo.yana.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.icaboalo.yana.data.entities.mappers.ActionPlanEntityMapper;
import com.icaboalo.yana.data.entities.mappers.ActivityEntityMapper;
import com.icaboalo.yana.data.entities.mappers.CategoryEntityMapper;
import com.icaboalo.yana.data.entities.mappers.ChatBotEntityMapper;
import com.icaboalo.yana.data.entities.mappers.ContactEntityMapper;
import com.icaboalo.yana.data.entities.mappers.DayEntityMapper;
import com.icaboalo.yana.data.entities.mappers.EntityDataMapper;
import com.icaboalo.yana.data.entities.mappers.EntityMapper;
import com.icaboalo.yana.data.entities.mappers.UserEntityMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author icaboalo on 08/08/16.
 */
public class Utils {

    public static EntityMapper getDataMapper(Class dataClass) {
        switch (dataClass.getName()) {
            case "com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel":
                return new UserEntityMapper();
            case "com.icaboalo.yana.data.entities.realm_models.action_plan.ActionPlanRealmModel":
                return new ActionPlanEntityMapper();
            case "com.icaboalo.yana.data.entities.realm_models.action_plan.ActivityRealmModel":
                return new ActivityEntityMapper();
            case "com.icaboalo.yana.data.entities.realm_models.action_plan.CategoryRealmModel":
                return new CategoryEntityMapper();
            case "com.icaboalo.yana.data.entities.realm_models.action_plan.DayRealmModel":
                return new DayEntityMapper();
            case "com.icaboalo.yana.data.entities.realm_models.action_plan.ContactRealmModel":
                return new ContactEntityMapper();
            case "com.icaboalo.yana.data.entities.realm_models.ChatBotRealmModel":
                return new ChatBotEntityMapper();
            default:
                return new EntityDataMapper();
        }
    }


    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager nConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (hasLollipop()) {
            Network[] nNetworks = nConnectivityManager.getAllNetworks();
            NetworkInfo nNetworkInfo;
            for (Network nNetwork : nNetworks) {
                nNetworkInfo = nConnectivityManager.getNetworkInfo(nNetwork);
                if (nNetworkInfo.isConnected())
                    return true;
            }
        } else {
            if (nConnectivityManager != null) {
                NetworkInfo[] nNetworkInfos = nConnectivityManager.getAllNetworkInfo();
                if (nNetworkInfos != null) {
                    for (NetworkInfo nNetworkInfo : nNetworkInfos) {
                        if (nNetworkInfo.isConnected()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static final String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
    }

    public static String transformTo24Hours(int hour, int minutes) {
        if (hour >= 12) {
            if (minutes == 0) {
                if (hour == 12)
                    return String.format("%d:0%d PM", hour, minutes);
                else
                    return String.format("%d:0%d PM", hour - 12, minutes);
            } else {
                if (hour == 12)
                    return String.format("%d:%d PM", hour, minutes);
                else
                    return String.format("%d:%d PM", hour - 12, minutes);
            }
        } else {
            if (minutes == 0)
                return String.format("%d:0%d AM", hour, minutes);
            else
                return String.format("%d:%d AM", hour, minutes);

        }
    }

    public static void createNotification(Context context, String time, Class broadcastReceiverClass, int id, long interval){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

        try {
            Log.d("TIME", date24Format.format(date12Format.parse(time)));
            String hour = date24Format.format(date12Format.parse(time));

            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour.substring(0, 2)));
            calendar.set(Calendar.MINUTE, Integer.valueOf(hour.substring(3, 5)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("CALENDAR", calendar.getTime().getHours() + ":" + calendar.getTime().getMinutes());

        Intent intent = new Intent(context, broadcastReceiverClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
    }

    public static void deleteNotification(Context context, Class broadcastReceiverClass, int id){
        Intent intent = new Intent(context, broadcastReceiverClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public static String transformDateToText(String date, String beginFormat, String endFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(endFormat);
        try {
            return simpleDateFormat.format(new SimpleDateFormat(beginFormat).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
