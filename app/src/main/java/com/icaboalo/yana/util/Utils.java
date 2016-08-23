package com.icaboalo.yana.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import com.icaboalo.yana.data.entities.mappers.ActionPlanEntityMapper;
import com.icaboalo.yana.data.entities.mappers.ActivityEntityMapper;
import com.icaboalo.yana.data.entities.mappers.CategoryEntityMapper;
import com.icaboalo.yana.data.entities.mappers.DayEntityMapper;
import com.icaboalo.yana.data.entities.mappers.EntityDataMapper;
import com.icaboalo.yana.data.entities.mappers.EntityMapper;
import com.icaboalo.yana.data.entities.mappers.UserEntityMapper;

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
            default:
                return new EntityDataMapper();
        }
    }


    public static boolean hasLollipop(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager nConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (hasLollipop()){
            Network[] nNetworks = nConnectivityManager.getAllNetworks();
            NetworkInfo nNetworkInfo;
            for (Network nNetwork : nNetworks){
                nNetworkInfo = nConnectivityManager.getNetworkInfo(nNetwork);
                if (nNetworkInfo.isConnected())
                    return true;
            }
        }
        else {
            if (nConnectivityManager != null){
                NetworkInfo[] nNetworkInfos = nConnectivityManager.getAllNetworkInfo();
                if (nNetworkInfos != null){
                    for (NetworkInfo nNetworkInfo : nNetworkInfos){
                        if (nNetworkInfo.isConnected()){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static final String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
    }
}
