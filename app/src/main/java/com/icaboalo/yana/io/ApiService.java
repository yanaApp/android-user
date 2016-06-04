package com.icaboalo.yana.io;

import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.realm.DayModel;
import com.icaboalo.yana.realm.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by icaboalo on 01/06/16.
 */
public interface ApiService {

    @POST("login/")
    Call<UserModel> login(@Body UserModel user);

    @GET("day/")
    Call<List<DayModel>> getDays();

    @GET("activity/")
    Call<List<ActivityModel>> getActivities(@Header("Authorization") String token);

    @PUT("activity/{id}/")
    Call<ActivityModel> putActivity(@Header("Authorization") String token, @Body ActivityApiModel activity, @Path("id") int activityId);
}
