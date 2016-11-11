package com.icaboalo.yana.data.network;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.icaboalo.yana.BuildConfig;
import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.data.executor.JobExecutor;
import com.icaboalo.yana.util.Constants;
import com.icaboalo.yana.util.PrefUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
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

    private static Interceptor provideDynamicHeaderInterceptor(){
        return chain -> {
            Request finalRequest = chain.request();
            if (finalRequest.toString().contains("login/") || finalRequest.toString().contains("register") ||
                    finalRequest.toString().contains("restore")){
                finalRequest = chain.request().newBuilder()
                        .header("Content-Type", "application/json")
                        .method(chain.request().method(), chain.request().body())
                        .build();

            } else {
                finalRequest = chain.request().newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Token " + PrefUtils.getToken(MyApplication.getInstance().getApplicationContext()))
                        .method(chain.request().method(), chain.request().body())
                        .build();
            }
            return chain.proceed(finalRequest);
        };
    }

    public static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> Log.d("NetworkInfo", message))
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }

    public static OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(provideDynamicHeaderInterceptor())
                .addInterceptor(provideHttpLoggingInterceptor())
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

    public static Observable<Object> dynamicPatchObject(String url, RequestBody body){
        if (mRetrofit == null)
            mRetrofit = createRetro2Client();
        return mRetrofit.create(RestApi.class).dynamicPatchObject(url, body);
    }

    public static Observable<Object> dynamicDeleteObject(String url, RequestBody body){
        if (mRetrofit == null)
            mRetrofit = createRetro2Client();
        return mRetrofit.create(RestApi.class).dynamicDeleteObject(url, body);
    }
}
