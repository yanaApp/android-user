package com.icaboalo.yana.data.network;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.icaboalo.yana.data.executor.JobExecutor;
import com.icaboalo.yana.util.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * @author icaboalo on 01/08/16.
 */
public class ApiConnection {

    private static Retrofit mRetrofit;
    private static final int TIME_OUT = 30;

    public static OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        return builder.build();
    }

    public static Retrofit createRetro2Client(){
        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .client(provideOkHttpClient())
                .callbackExecutor(new JobExecutor())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaredClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    }).create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static Observable<Object> dynamicGetObject(String url){
        if (mRetrofit == null)
            mRetrofit = createRetro2Client();
        return mRetrofit.create(RestApi.class).dynamicGetObject(url);
    }

    public static Observable<List> dynamicGetList(String url){
        if (mRetrofit == null)
            mRetrofit = createRetro2Client();
        return mRetrofit.create(RestApi.class).dynamicGetList(url);
    }

    public static Observable<Object> dynamicPostObject(String url, RequestBody body){
        if (mRetrofit == null)
            mRetrofit = createRetro2Client();
        return mRetrofit.create(RestApi.class).dynamicPostObject(url, body);
    }

    public static Observable<List> dynamicPostList(String url, RequestBody body){
        if (mRetrofit == null)
            mRetrofit = createRetro2Client();
        return mRetrofit.create(RestApi.class).dynamicPostList(url, body);
    }

    public static Observable<Object> dynamicPutObject(String url, RequestBody body){
        if (mRetrofit == null)
            mRetrofit = createRetro2Client();
        return mRetrofit.create(RestApi.class).dynamicPutObject(url, body);
    }

    public static Observable<List> dynamicPutList(String url, RequestBody body){
        if (mRetrofit == null)
            mRetrofit = createRetro2Client();
        return mRetrofit.create(RestApi.class).dynamicPutList(url, body);
    }

    public static Observable<Object> dynamicDeleteObject(String url, RequestBody body){
        if (mRetrofit == null)
            mRetrofit = createRetro2Client();
        return mRetrofit.create(RestApi.class).dynamicDeleteObject(url, body);
    }
}
