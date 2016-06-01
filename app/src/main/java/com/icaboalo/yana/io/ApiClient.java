package com.icaboalo.yana.io;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by icaboalo on 01/06/16.
 */
public class ApiClient {

    public static ApiService mApiService;
    public static ApiService getApiService(){
        if (mApiService == null){
            Retrofit nRetrofit = new Retrofit.Builder()
                    .baseUrl("http://yana-network.herokuapp.com/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApiService = nRetrofit.create(ApiService.class);
        }
        return mApiService;
    }
}
