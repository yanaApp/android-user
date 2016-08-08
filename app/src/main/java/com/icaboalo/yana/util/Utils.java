package com.icaboalo.yana.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import com.icaboalo.yana.data.entities.mappers.EntityDataMapper;
import com.icaboalo.yana.data.entities.mappers.EntityMapper;

/**
 * @author icaboalo on 08/08/16.
 */
public class Utils {

    public static EntityMapper getDataMapper(Class dataClass) {
        switch (dataClass.getName()) {
//            case "com.grability.rappitendero.data.entities.CityEntity":
//                return new CityEntityMapper();
//            case "com.grability.rappitendero.data.entities.realm_models.ProfileRealmModel":
//                return new ProfileEntityMapper();
//            case "com.grability.rappitendero.data.entities.realm_models.OrderHistoryResponseRealmModel":
//                return new OrderHistoryEntityMapper();
//            case "com.grability.rappitendero.data.entities.realm_models.incoming.IncomingRealmModel":
//                return new IncomingEntityMapper();
//            case "com.grability.rappitendero.data.entities.realm_models.incoming.OrdersRealmModel":
//                return new OrderEntityMapper();
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
}
