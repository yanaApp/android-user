package com.icaboalo.yana.data.network;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author icaboalo on 07/08/16.
 */
public class RestApiImpl implements RestApi {

    public RestApiImpl() {
    }

    @Override
    public Observable<Object> dynamicGetObject(@Url String url) {
        return ApiConnection.dynamicGetObject(url);
    }

    @Override
    public Observable<List> dynamicGetList(@Url String url) {
        return ApiConnection.dynamicGetList(url);
    }

    @Override
    public Observable<Object> dynamicPostObject(@Url String url, @Body RequestBody body) {
        return ApiConnection.dynamicPostObject(url, body);
    }

    @Override
    public Observable<List> dynamicPostList(@Url String url, @Body RequestBody body) {
        return ApiConnection.dynamicPostList(url, body);
    }

    @Override
    public Observable<Object> dynamicPutObject(@Url String url, @Body RequestBody body) {
        return ApiConnection.dynamicPutObject(url, body);
    }

    @Override
    public Observable<List> dynamicPutList(@Url String url, @Body RequestBody body) {
        return ApiConnection.dynamicPutList(url, body);
    }

    @Override
    public Observable<Object> dynamicPatchObject(@Url String url, @Body RequestBody body) {
        return ApiConnection.dynamicPatchObject(url, body);
    }

    @Override
    public Observable<Object> dynamicDeleteObject(@Url String url, @Body RequestBody body) {
        return ApiConnection.dynamicDeleteObject(url, body);
    }
}
