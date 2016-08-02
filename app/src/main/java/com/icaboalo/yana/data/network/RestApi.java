package com.icaboalo.yana.data.network;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author icaboalo on 01/08/16.
 */
public interface RestApi {

    @GET
    Observable<Object> dynamicGetObject(@Url String url);

    @GET
    Observable<List> dynamicGetList(@Url String url);

    @POST
    Observable<Object> dynamicPostObject(@Url String url, @Body RequestBody body);

    @POST
    Observable<List> dynamicPostList(@Url String url, @Body RequestBody body);

    @PUT
    Observable<Object> dynamicPutObject(@Url String url, @Body RequestBody body);

    @DELETE
    Observable<Object> dynamicDeleteObject(@Url String url, @Body RequestBody body);
}
