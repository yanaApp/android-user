package com.icaboalo.yana.presentation.screens.chat_bot.view_holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.view_model.ChatbotMessageViewModel;

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
        if (data instanceof ChatbotMessageViewModel) {
            ChatbotMessageViewModel chatbotMessageViewModel = (ChatbotMessageViewModel) data;
            tvMessage.setText(chatbotMessageViewModel.getQuestion());
            switch (chatbotMessageViewModel.getQuestionType()) {
                case ChatbotMessageViewModel.OPTIONS:
                    etResponse.setVisibility(View.GONE);
                    break;

                case ChatbotMessageViewModel.KEYBOARD:
//                        etResponse.setVisibility(View.VISIBLE);
//                        etResponse.setHint("Respuesta");
                    break;

                case ChatbotMessageViewModel.TEXT:
                    etResponse.setVisibility(View.GONE);
                    break;
            }

        } else if (data instanceof String) {
            tvMessage.setText((String) data);
        }
    }
}
