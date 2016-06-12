package com.icaboalo.yana.io;

import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.io.model.UserApiModel;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.realm.ContactModel;
import com.icaboalo.yana.realm.DayModel;
import com.icaboalo.yana.realm.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by icaboalo on 01/06/16.
 */
public interface ApiService {

    @POST("login/")
    Call<UserModel> login(@Body UserModel user);

    @POST("user/register/")
    Call<UserApiModel> userRegister(@Body UserApiModel user);

    @GET("me/")
    Call<ArrayList<UserApiModel>> getMe(@Header("Authorization") String token);

    @GET("day/")
    Call<List<DayModel>> getDays();

    @GET("activity/")
    Call<List<ActivityModel>> getActivities(@Header("Authorization") String token);

    @PATCH("activity/{id}/")
    Call<ActivityApiModel> updateActivity(@Header("Authorization") String token, @Body HashMap<String, Integer> answer, @Path("id") int activityId);

    @POST("contact/")
    Call<ContactModel> saveContact(@Header("Authorization") String token, @Body ContactModel contactModel);
}
