package com.icaboalo.yana.presentation.screens.chat_bot;

import com.icaboalo.yana.presentation.screens.GenericListView;
import com.icaboalo.yana.presentation.screens.chat_bot.view_holder.ChatLeftViewHolder;
import com.icaboalo.yana.presentation.screens.view_model.ChatBotViewModel;

/**
 * Created by icaboalo on 12/12/16.
 */

public interface ChatBotView extends GenericListView<ChatBotViewModel, ChatLeftViewHolder> {
    void saveResponseSuccessful();
}
