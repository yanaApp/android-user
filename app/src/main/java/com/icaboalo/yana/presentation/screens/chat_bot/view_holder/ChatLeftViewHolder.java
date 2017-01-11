package com.icaboalo.yana.presentation.screens.chat_bot.view_holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.screens.view_model.ChatBotAnswerViewModel;
import com.icaboalo.yana.presentation.screens.view_model.ChatBotViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 12/12/16.
 */

public class ChatLeftViewHolder extends GenericRecyclerViewAdapter.ViewHolder {

    @BindView(R.id.tv_message)
    TextView tvMessage;
    @Nullable
    @BindView(R.id.et_response)
    EditText etResponse;

    public ChatLeftViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Object data, int position, boolean isEnabled) {
        if (data instanceof ChatBotViewModel) {
            ChatBotViewModel chatBotViewModel = (ChatBotViewModel) data;
            if (chatBotViewModel.getSender() == 0)
                switch (chatBotViewModel.getAnswerType()) {
                    case ChatBotViewModel.OPTIONS:
                        etResponse.setVisibility(View.GONE);
                        tvMessage.setText(chatBotViewModel.getQuestion());
                        break;

                    case ChatBotViewModel.KEYBOARD:
                        tvMessage.setVisibility(View.GONE);
                        etResponse.setVisibility(View.VISIBLE);
                        etResponse.setHint("Respuesta");
                        break;
                }
        }
        else if (data instanceof ChatBotAnswerViewModel) {
            ChatBotAnswerViewModel chatBotAnswerViewModel = (ChatBotAnswerViewModel) data;
            tvMessage.setText(chatBotAnswerViewModel.getAnswer());
        }
    }
}
