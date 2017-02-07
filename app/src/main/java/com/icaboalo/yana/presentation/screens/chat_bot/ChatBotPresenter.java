package com.icaboalo.yana.presentation.screens.chat_bot;

import com.icaboalo.yana.R;
import com.icaboalo.yana.data.entities.realm_models.ChatBotRealmModel;
import com.icaboalo.yana.data.entities.realm_models.ScheduleRealmModel;
import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.exception.DefaultErrorBundle;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.ChatBot;
import com.icaboalo.yana.domain.models.Schedule;
import com.icaboalo.yana.domain.models.action_plan.User;
import com.icaboalo.yana.other.ManagerPreference;
import com.icaboalo.yana.other.YanaPreferences;
import com.icaboalo.yana.presentation.screens.GenericListPresenter;
import com.icaboalo.yana.presentation.screens.chat_bot.view_holder.ChatLeftViewHolder;
import com.icaboalo.yana.presentation.view_model.ChatBotViewModel;
import com.icaboalo.yana.presentation.view_model.ScheduleViewModel;
import com.icaboalo.yana.presentation.view_model.UserViewModel;
import com.icaboalo.yana.util.Constants;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by icaboalo on 12/12/16.
 */

public class ChatBotPresenter extends GenericListPresenter<ChatBotViewModel, ChatLeftViewHolder> {

    @Inject
    public ChatBotPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    //TODO Change persist for true
    @Override
    public void getItemList() {
        getGenericUseCase().executeDynamicGetList(new ItemListSubscriber(), Constants.API_BASE_URL + "chat/",
                ChatBot.class, ChatBotRealmModel.class, ChatBotViewModel.class, false);
    }

    public void attemptSaveResponse(ChatBotViewModel chatBotViewModel) {
        HashMap<String, Object> responseBundle = new HashMap<>();
        responseBundle.put("answer", chatBotViewModel.getAnswer());
        //TODO Change persist for true
        getGenericUseCase().executeDynamicPutObject(new SaveResponseSubscriber(), Constants.API_BASE_URL + "chatbot/",
                responseBundle, ChatBot.class, ChatBotRealmModel.class, ChatBotViewModel.class, false);

        if (chatBotViewModel.isNeedsSave()) {
            HashMap<String, Object> saveBundle = new HashMap<>();
            switch (chatBotViewModel.getCategory()) {
                case ChatBotViewModel.CATEGORY_PROFILE:
                    saveBundle.put(chatBotViewModel.getSaveFieldName(), chatBotViewModel.getAnswer());
                    saveInfo(saveBundle, User.class, UserRealmModel.class, UserViewModel.class, Constants.API_BASE_URL + "user/" +
                            ManagerPreference.getInstance().getInt(YanaPreferences.USER_ID) + "/");
                    break;

                case ChatBotViewModel.CATEGORY_SCHEDULE:
                    if (chatBotViewModel.getAnswer().contentEquals("Si"))
                        saveBundle.put(chatBotViewModel.getSaveFieldName(), true);
                    else if (chatBotViewModel.getAnswer().contentEquals("No"))
                        saveBundle.put(chatBotViewModel.getSaveFieldName(), false);
                    else if (chatBotViewModel.getSaveFieldName().contentEquals("work_days")) {
                        String[] days = chatBotViewModel.getAnswer().split(", ");
                        for (String day : days) {
                            if (getGenericListView().getApplicationContext().getString(R.string.monday).contentEquals(day))
                                saveBundle.put("work_monday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.tuesday).contentEquals(day))
                                saveBundle.put("work_tuesday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.wednesday).contentEquals(day))
                                saveBundle.put("work_wednesday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.thursday).contentEquals(day))
                                saveBundle.put("work_thursday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.friday).contentEquals(day))
                                saveBundle.put("work_friday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.saturday).contentEquals(day))
                                saveBundle.put("work_saturday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.sunday).contentEquals(day))
                                saveBundle.put("work_sunday", true);
                        }
                    } else if (chatBotViewModel.getSaveFieldName().contentEquals("study_days")) {
                        String[] days = chatBotViewModel.getAnswer().split(", ");
                        for (String day : days) {
                            if (getGenericListView().getApplicationContext().getString(R.string.monday).contentEquals(day))
                                saveBundle.put("study_monday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.tuesday).contentEquals(day))
                                saveBundle.put("study_tuesday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.wednesday).contentEquals(day))
                                saveBundle.put("study_wednesday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.thursday).contentEquals(day))
                                saveBundle.put("study_thursday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.friday).contentEquals(day))
                                saveBundle.put("study_friday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.saturday).contentEquals(day))
                                saveBundle.put("study_saturday", true);
                            else if (getGenericListView().getApplicationContext().getString(R.string.sunday).contentEquals(day))
                                saveBundle.put("study_sunday", true);
                        }
                    } else
                        saveBundle.put(chatBotViewModel.getSaveFieldName(), chatBotViewModel.getAnswer());
                    saveInfo(saveBundle, Schedule.class, ScheduleRealmModel.class, ScheduleViewModel.class, "");
                    break;
            }
        }
    }

    private void saveInfo(HashMap<String, Object> saveBundle, Class domainClass, Class dataClass, Class presentationClass, String url) {
        getGenericUseCase().executeDynamicPatchObject(new SaveInfoSubscriber(), url, saveBundle, domainClass, dataClass,
                presentationClass, true);
    }

    private class SaveInfoSubscriber extends DefaultSubscriber {
        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
//            showViewRetry();
//            showErrorMessage(new DefaultErrorBundle((Exception) e));
            e.printStackTrace();
        }

        @Override
        public void onNext(Object o) {
            ((ChatBotView) getGenericListView()).saveInfoSuccessful();
        }
    }

    private class SaveResponseSubscriber extends DefaultSubscriber<ChatBotViewModel> {
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
            showViewRetry();
            showErrorMessage(new DefaultErrorBundle((Exception) e));
            e.printStackTrace();
            ((ChatBotView) getGenericListView()).errorSaveResponse();
        }

        @Override
        public void onNext(ChatBotViewModel chatBotViewModel) {
            ((ChatBotView) getGenericListView()).sendNextMessage(chatBotViewModel);
        }
    }
}
