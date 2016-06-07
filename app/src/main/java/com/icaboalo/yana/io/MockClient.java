package com.icaboalo.yana.io;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by icaboalo on 07/06/16.
 */
public class MockClient {

    public static MockService mMockService;
    public static MockService getApiService(){
        if (mMockService == null){
            Retrofit nRetrofit = new Retrofit.Builder()
                    .baseUrl("http://yana-mock.herokuapp.com/api/v1/")
                    .client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mMockService = nRetrofit.create(MockService.class);
        }
        return mMockService;
    }
}
