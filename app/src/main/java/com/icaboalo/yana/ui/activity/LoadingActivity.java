package com.icaboalo.yana.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.icaboalo.yana.R;
import com.icaboalo.yana.io.ApiClient;
import com.icaboalo.yana.io.model.ActionPlanApiModel;
import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.io.model.CategoryApiModel;
import com.icaboalo.yana.io.model.ContactApiModel;
import com.icaboalo.yana.io.model.DayApiModel;
import com.icaboalo.yana.io.model.UserApiModel;
import com.icaboalo.yana.realm.ActionPlanModel;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.realm.CategoryModel;
import com.icaboalo.yana.realm.ContactModel;
import com.icaboalo.yana.realm.DayModel;
import com.icaboalo.yana.realm.UserModel;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.VUtil;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import me.wangyuwei.loadingview.LoadingView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends AppCompatActivity {

    private static final String TAG = "LoadingActivity";
    @Bind(R.id.rlLoading)
    RelativeLayout rlLoading;
    @Bind(R.id.rlLoadComplete)
    RelativeLayout rlLoadComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);

        getMe(PrefUtils.getToken(this));
    }

    @OnClick(R.id.btContinue)
    void goToMain(){
        Intent goToMain = new Intent(LoadingActivity.this, MainActivity.class);
        startActivity(goToMain);
        finish();
    }

    void getMe(String token){
        Call<ArrayList<UserApiModel>> call = ApiClient.getApiService().getMe(token);
        call.enqueue(new Callback<ArrayList<UserApiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserApiModel>> call, Response<ArrayList<UserApiModel>> response) {
                if (response.isSuccessful()){

                    UserApiModel responseUser = response.body().get(0);


                    final UserModel user = new UserModel();
                    user.setId(responseUser.getId());
                    user.setUserName(responseUser.getUserName());
                    user.setEmail(responseUser.getEmail());
                    user.setPhoneNumber(responseUser.getPhoneNumber());

                    Log.d(TAG, "onResponse: " + user.toString());

                    final ArrayList<ActionPlanModel> actionPlanList = new ArrayList<ActionPlanModel>();
                    final ArrayList<DayModel> dayList = new ArrayList<DayModel>();
                    final ArrayList<ActivityModel> activityList = new ArrayList<ActivityModel>();
                    final ArrayList<CategoryModel> categoryList = new ArrayList<CategoryModel>();
                    final ArrayList<ContactModel> contactList = new ArrayList<ContactModel>();

                    for (ActionPlanApiModel responseActionPlan: responseUser.getActionPlanList()){

                        ActionPlanModel actionPlan = new ActionPlanModel();
                        actionPlan.setId(responseActionPlan.getId());
                        actionPlan.setCategory(responseActionPlan.getCategory());
                        actionPlan.setInitialDate(responseActionPlan.getInitialDate());
                        actionPlan.setFinalDate(responseActionPlan.getFinalDate());
                        actionPlan.setUser(user);
                        actionPlan.setActive(responseActionPlan.isActive());

                        Log.d(TAG, "onResponse: " + actionPlan.toString());
                        actionPlanList.add(actionPlan);

                        for (DayApiModel responseDay: responseActionPlan.getDayList()){

                            DayModel day = new DayModel();
                            day.setId(responseDay.getId());
                            day.setActionPlan(actionPlan);
                            day.setAnswer(responseDay.getAnswer());
                            day.setNumber(responseDay.getNumber());
                            day.setDate(responseDay.getDate());

                            Log.d(TAG, "onResponse: " + day.toString());
                            dayList.add(day);

                            for (ActivityApiModel responseActivity: responseDay.getActivityList()){

                                CategoryModel category = new CategoryModel();
                                category.setId(responseActivity.getCategory().getId());
                                category.setName(responseActivity.getCategory().getName());
                                category.setColor(responseActivity.getCategory().getColor());

                                Log.d(TAG, "onResponse: " + category.getName() + category.getColor());

                                categoryList.add(category);

                                ActivityModel activity = new ActivityModel();
                                activity.setId(responseActivity.getId());
                                activity.setTitle(responseActivity.getTitle());
                                activity.setDescription(responseActivity.getDescription());
                                activity.setAnswer(responseActivity.getAnswer());
                                activity.setDay(day);
                                activity.setCategory(category);

                                Log.d(TAG, "onResponse: " + activity.toString());
                                activityList.add(activity);
                            }
                        }
                    }

                    for (ContactApiModel responseContact: responseUser.getContactList()){
                        ContactModel contact = new ContactModel();
                        Log.d(TAG, "onResponse: " + responseContact.getName());
                        contact.setId(responseContact.getId());
                        contact.setPhoneNumber(responseContact.getPhoneNumber());
                        contact.setName(responseContact.getName());
                        contact.setValidated(responseContact.isValidated());
                        contactList.add(contact);
                    }

                    Log.d(TAG, "onResponse: " + categoryList.size());

                    Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealmOrUpdate(user);
                            realm.copyToRealmOrUpdate(actionPlanList);
                            realm.copyToRealmOrUpdate(dayList);
                            realm.copyToRealmOrUpdate(categoryList);
                            realm.copyToRealmOrUpdate(activityList);
                            realm.copyToRealmOrUpdate(contactList);
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showLoadingComplete();
                                }
                            }, 8000);
                        }
                    });
                } else{
                    try {
                        Log.d("RETROFIT_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserApiModel>> call, Throwable t) {
                Log.d("RETROFIT_FAILURE", t.toString());
            }
        });
    }

    void showLoadingComplete(){
        rlLoading.setVisibility(View.GONE);
        rlLoadComplete.setVisibility(View.VISIBLE);
    }


}
