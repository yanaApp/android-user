package com.icaboalo.yana.old.io;


import com.icaboalo.yana.old.io.model.ActivityApiModel;
import com.icaboalo.yana.old.io.model.ContactApiModel;
import com.icaboalo.yana.old.io.model.UserApiModel;
import com.icaboalo.yana.old.realm.ActivityModel;
import com.icaboalo.yana.old.realm.ContactModel;
import com.icaboalo.yana.old.realm.DayModel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by icaboalo on 01/06/16.
 */
public interface ApiService {

    @POST("login/")
    Call<UserApiModel> login(@Body UserApiModel user);

    @POST("user/register")
    Call<UserApiModel> userRegister(@Body UserApiModel user);

    @PATCH("user/{id}/")
    Call<UserApiModel> updateUserInfo(@Header("Authorization") String token, @Body UserApiModel user, @Path("id") int userId);

    @GET("me/")
    Call<ArrayList<UserApiModel>> getMe(@Header("Authorization") String token);

    @GET("day/")
    Call<List<DayModel>> getDays();

    @GET("activity/")
    Call<List<ActivityModel>> getActivities(@Header("Authorization") String token);

    @PATCH("activity/{id}/")
    Call<ActivityApiModel> updateActivity(@Header("Authorization") String token, @Body HashMap<String, Integer> answer, @Path("id") int activityId);

    @POST("contact/")
    Call<ContactModel> saveContact(@Header("Authorization") String token, @Body ContactApiModel contact);
}
