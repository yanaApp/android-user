package com.icaboalo.yana.presentation.screens.chat_bot;

import android.util.Log;

import com.icaboalo.yana.R;
import com.icaboalo.yana.data.entities.realm_models.ChatbotMessageRealmModel;
import com.icaboalo.yana.data.entities.realm_models.ScheduleRealmModel;
import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.exception.DefaultErrorBundle;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.ChatbotMessage;
import com.icaboalo.yana.domain.models.Schedule;
import com.icaboalo.yana.domain.models.action_plan.User;
import com.icaboalo.yana.other.ManagerPreference;
import com.icaboalo.yana.other.YanaPreferences;
import com.icaboalo.yana.presentation.screens.GenericListPresenter;
import com.icaboalo.yana.presentation.screens.chat_bot.view_holder.ChatLeftViewHolder;
import com.icaboalo.yana.presentation.view_model.ChatbotMessageViewModel;
import com.icaboalo.yana.presentation.view_model.ScheduleViewModel;
import com.icaboalo.yana.presentation.view_model.UserViewModel;
import com.icaboalo.yana.util.Constants;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by icaboalo on 12/12/16.
 */

public class ChatBotPresenter extends GenericListPresenter<ChatbotMessageViewModel, ChatLeftViewHolder> {

    @Inject
    public ChatBotPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemList() {
        getGenericUseCase().executeDynamicGetList(new ItemListSubscriber(), Constants.API_BASE_URL + "chat_bot/",
                ChatbotMessage.class, ChatbotMessageRealmModel.class, ChatbotMessageViewModel.class, false);
    }

    public void attemptSaveResponse(ChatbotMessageViewModel chatbotMessageViewModel, String answer) {
        HashMap<String, Object> responseBundle = new HashMap<>();
        responseBundle.put("answer", answer);
        responseBundle.put("message_id", chatbotMessageViewModel.getId());

        //TODO Change persist for true
        getGenericUseCase().executeDynamicPostObject(new SaveResponseSubscriber(), Constants.API_BASE_URL + "chat_bot/answer/",
                responseBundle, ChatbotMessage.class, ChatbotMessageRealmModel.class, ChatbotMessageViewModel.class, true);

        if (chatbotMessageViewModel.isNeedsSave()) {
            HashMap<String, Object> saveBundle = new HashMap<>();
            switch (chatbotMessageViewModel.getCategory()) {
                case ChatbotMessageViewModel.CATEGORY_PROFILE:
                    saveBundle.put(chatbotMessageViewModel.getSaveFieldName(), answer);
                    saveInfo(saveBundle, User.class, UserRealmModel.class, UserViewModel.class, Constants.API_BASE_URL + "user/" +
                            ManagerPreference.getInstance().getInt(YanaPreferences.USER_ID) + "/");
                    break;

                case ChatbotMessageViewModel.CATEGORY_SCHEDULE:
                    if (answer.contentEquals("Si"))
                        saveBundle.put(chatbotMessageViewModel.getSaveFieldName(), true);
                    else if (answer.contentEquals("No"))
                        saveBundle.put(chatbotMessageViewModel.getSaveFieldName(), false);
                    else if (chatbotMessageViewModel.getSaveFieldName().contentEquals("work_days")) {
                        String[] days = answer.split(", ");
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
                    } else if (chatbotMessageViewModel.getSaveFieldName().contentEquals("study_days")) {
                        String[] days = answer.split(", ");
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
                        saveBundle.put(chatbotMessageViewModel.getSaveFieldName(), answer);
                    saveInfo(saveBundle, Schedule.class, ScheduleRealmModel.class, ScheduleViewModel.class, "");
                    break;
            }
        }
    }

    private void saveInfo(HashMap<String, Object> saveBundle, Class domainClass, Class dataClass, Class presentationClass, String url) {
        getGenericUseCase().executeDynamicPatchObject(new SaveInfoSubscriber(), url, saveBundle, domainClass, dataClass,
                presentationClass, true);
    }

    public void getNextMessage(int id) {
        getGenericUseCase().executeDynamicGetObject(new NextMessageSubscriber(), "", Constants.API_BASE_URL + "chat_bot/" + id + "/", "",
                ChatbotMessage.class, ChatbotMessageRealmModel.class, ChatbotMessageViewModel.class, true);
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

    private class NextMessageSubscriber extends DefaultSubscriber<ChatbotMessageViewModel> {
        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
//            showViewRetry();
            showErrorMessage(new DefaultErrorBundle((Exception) e));
            e.printStackTrace();
        }

        @Override
        public void onNext(ChatbotMessageViewModel chatbotMessageViewModel) {
            ((ChatBotView) getGenericListView()).sendNextMessage(chatbotMessageViewModel);
        }
    }

    private class SaveResponseSubscriber extends DefaultSubscriber<ChatbotMessageViewModel> {
        @Override
        public void onCompleted() {
            hideViewLoading();
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
        public void onNext(ChatbotMessageViewModel chatbotMessageViewModel) {
            if (chatbotMessageViewModel.getSubQuestion() != null) {
                if (chatbotMessageViewModel.getAnswer().contentEquals(chatbotMessageViewModel.getAnswerForSubQuestions()))
                    getNextMessage(chatbotMessageViewModel.getSubQuestion());
                else if (chatbotMessageViewModel.getNextQuestion() != null)
                    getNextMessage(chatbotMessageViewModel.getNextQuestion());
                else if (chatbotMessageViewModel.getParentQuestion() != null)
                    getNextMessage(chatbotMessageViewModel.getParentQuestion());
            } else if (chatbotMessageViewModel.getNextQuestion() != null)
                getNextMessage(chatbotMessageViewModel.getNextQuestion());
            else if (chatbotMessageViewModel.getParentQuestion() != null)
                getNextMessage(chatbotMessageViewModel.getParentQuestion());

//            getNextMessage(chatbotMessageViewModel.getNextQuestion());
            Log.d("next", chatbotMessageViewModel.getNextQuestion() + "");
//            ((ChatBotView) getGenericListView()).sendNextMessage(chatbotMessageViewModel);
        }
    }
}
