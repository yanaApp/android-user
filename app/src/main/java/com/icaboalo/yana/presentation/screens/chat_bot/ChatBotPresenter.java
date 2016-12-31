package com.icaboalo.yana.presentation.screens.chat_bot;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericListPresenter;
import com.icaboalo.yana.presentation.screens.chat_bot.view_holder.ChatViewHolder;
import com.icaboalo.yana.presentation.screens.view_model.ChatBotAnswerViewModel;
import com.icaboalo.yana.presentation.screens.view_model.ChatBotViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icaboalo on 12/12/16.
 */

public class ChatBotPresenter extends GenericListPresenter<Object, ChatViewHolder> {

    @Inject
    public ChatBotPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemList() {
        List<Object> list = new ArrayList<>();
        ChatBotViewModel bot = new ChatBotViewModel();

        ArrayList<ChatBotAnswerViewModel> answerOptions = new ArrayList<>();
        ChatBotAnswerViewModel answerOption = new ChatBotAnswerViewModel();
        answerOption.setAnswer("bla bla bla");
        ChatBotAnswerViewModel answerOption2 = new ChatBotAnswerViewModel();
        answerOption2.setAnswer("pspsps");
        ChatBotAnswerViewModel answerOption3 = new ChatBotAnswerViewModel();
        answerOption3.setAnswer("asdada");
        answerOptions.add(answerOption);
        answerOptions.add(answerOption2);
        answerOptions.add(answerOption3);


        bot.setSender(ChatBotViewModel.BOT);
        bot.setType(ChatBotViewModel.OPTIONS);
        bot.setQuestion("bla bla bla bla");
        bot.setAnswers(answerOptions);
        bot.setAnswerPositionForSubQuestions(1);


        list.add(bot);
        list.add(bot);
        list.add(bot);
        getGenericListView().renderItemList(list);
    }


}
