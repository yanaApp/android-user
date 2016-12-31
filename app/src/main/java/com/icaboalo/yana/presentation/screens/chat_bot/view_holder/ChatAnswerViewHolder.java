package com.icaboalo.yana.presentation.screens.chat_bot.view_holder;

import android.view.View;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.screens.view_model.ChatBotAnswerViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 12/12/16.
 */

public class ChatAnswerViewHolder extends GenericRecyclerViewAdapter.ViewHolder {

    @BindView(R.id.tv_response)
    TextView tvAnswer;

    public ChatAnswerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Object data, int position, boolean isEnabled) {
        if (data instanceof ChatBotAnswerViewModel) {
            ChatBotAnswerViewModel chatAnswer = (ChatBotAnswerViewModel) data;

            tvAnswer.setText(chatAnswer.getAnswer());
        }
    }
}
