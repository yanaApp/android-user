package com.icaboalo.yana.presentation.screens.chat_bot;

import com.icaboalo.yana.data.entities.realm_models.ScheduleRealmModel;
import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.Schedule;
import com.icaboalo.yana.domain.models.action_plan.User;
import com.icaboalo.yana.presentation.screens.GenericListPresenter;
import com.icaboalo.yana.presentation.screens.chat_bot.view_holder.ChatLeftViewHolder;
import com.icaboalo.yana.presentation.view_model.ChatBotViewModel;
import com.icaboalo.yana.presentation.view_model.ScheduleViewModel;
import com.icaboalo.yana.presentation.view_model.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icaboalo on 12/12/16.
 */

public class ChatBotPresenter extends GenericListPresenter<ChatBotViewModel, ChatLeftViewHolder> {

    @Inject
    public ChatBotPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemList() {
        List<ChatBotViewModel> list = new ArrayList<>();
        ArrayList<ChatBotViewModel> subQuestions = new ArrayList<>();
        ArrayList<String> answers = new ArrayList<>();
        answers.add("1");
        answers.add("2");
        answers.add("3");

        ChatBotViewModel bot = new ChatBotViewModel();
        ChatBotViewModel bot2 = new ChatBotViewModel();
        ChatBotViewModel bot3 = new ChatBotViewModel();
        ChatBotViewModel bot4 = new ChatBotViewModel();

        bot3.setAnswerForSubQuestions("1");
        bot.setAnswers(answers);
        bot3.setAnswers(answers);
        bot.setQuestionType(ChatBotViewModel.OPTIONS);
        bot2.setQuestionType(ChatBotViewModel.TEXT);
        bot3.setQuestionType(ChatBotViewModel.OPTIONS);
        bot4.setQuestionType(ChatBotViewModel.KEYBOARD);
        bot.setQuestion("Esta es una pregunta...");
        bot2.setQuestion("Esto es solo texto");
        bot3.setQuestion("Las respuestas estan abajo");
        bot4.setQuestion("Debes de escribir la respuesta");
        bot.setAnswer("Esto es una respuesta");
        bot.setNeedsAnswer(true);
        bot2.setNeedsAnswer(false);
        bot3.setNeedsAnswer(true);
        bot4.setNeedsAnswer(true);

        for (int i = 0; i < 3; i++) {
            subQuestions.add(bot3);
        }

        bot3.setSubQuestions(subQuestions);

        list.add(bot);
        list.add(bot2);
        list.add(bot3);
        list.add(bot4);

        getGenericListView().renderItemList(list);
    }

    public void attemptSaveResponse(ChatBotViewModel chatBotViewModel) {
        HashMap<String, Object> saveBundle = new HashMap<>();

        switch (chatBotViewModel.getCategory()) {
            case ChatBotViewModel.CATEGORY_PROFILE:
                saveBundle.put(chatBotViewModel.getSaveFieldName(), chatBotViewModel.getAnswer());
                saveResponse(saveBundle, User.class, UserRealmModel.class, UserViewModel.class);
                break;

            case ChatBotViewModel.CATEGORY_SCHEDULE:
                saveBundle.put(chatBotViewModel.getSaveFieldName(), chatBotViewModel.getAnswer());
                saveResponse(saveBundle, Schedule.class, ScheduleRealmModel.class, ScheduleViewModel.class);
                break;
        }
    }

    private void saveResponse(HashMap<String, Object> saveBundle, Class domainClass, Class dataClass, Class presentationClass) {
        getGenericUseCase().executeDynamicPostObject(new SaveResponseSubscriber(), "", saveBundle, domainClass, dataClass,
                presentationClass, true);
    }

    private class SaveResponseSubscriber extends DefaultSubscriber {
        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }

        @Override
        public void onNext(Object o) {
            ((ChatBotView) getGenericListView()).saveResponseSuccessful();
        }
    }
}
