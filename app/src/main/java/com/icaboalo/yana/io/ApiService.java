package com.icaboalo.yana.io;

import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.realm.DayModel;
import com.icaboalo.yana.realm.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by icaboalo on 01/06/16.
 */
public interface ApiService {

    @POST()
    Call<UserModel> login(@Body UserModel user);

    @GET("")
    Call<DayModel> getDays();

    @GET("")
    Call<ActivityModel> getActivities(@Header("Authorization") String token);
}
