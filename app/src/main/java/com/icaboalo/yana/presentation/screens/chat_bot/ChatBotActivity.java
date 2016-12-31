package com.icaboalo.yana.presentation.screens.chat_bot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.chat_bot.view_holder.ChatAnswerViewHolder;
import com.icaboalo.yana.presentation.screens.chat_bot.view_holder.ChatViewHolder;
import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.screens.view_model.ChatBotAnswerViewModel;
import com.icaboalo.yana.presentation.screens.view_model.ChatBotViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatBotActivity extends BaseActivity implements ChatBotView {

    @Inject
    ChatBotPresenter mChatBotPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rv_chat)
    RecyclerView rvChat;
    @BindView(R.id.rv_answers)
    RecyclerView rvAnswers;

    GenericRecyclerViewAdapter<ChatAnswerViewHolder> chatAnswerAdapter;
    GenericRecyclerViewAdapter chatAdapter;
    LinearLayoutManager chatLayoutManager;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mChatBotPresenter.setView(this);
        mChatBotPresenter.initialize();
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_chat_bot);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupChatRecycler();
        setupAnswerRecycler();
        ArrayList<ItemInfo> bla = new ArrayList<>();
        ChatBotAnswerViewModel blaa = new ChatBotAnswerViewModel();
        blaa.setAnswer("blaa");

        for (int i = 0; i < 2; i++) {
            bla.add(new ItemInfo<>(blaa, R.layout.layout_chat_answer));
        }
        chatAnswerAdapter.setDataList(bla);
    }

    //    TODO remove hideLoading()
    @Override
    public void renderItemList(List<Object> itemList) {
        hideLoading();
        if (!itemList.isEmpty()) {
            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i) instanceof ChatBotViewModel) {
                    ChatBotViewModel chatBotViewModel = (ChatBotViewModel) itemList.get(i);
                    chatAdapter.addItem(chatAdapter.getItemCount(), new ItemInfo<>(chatBotViewModel, R.layout.layout_chat_left));
                    if (chatBotViewModel.getAnswer() == null || chatBotViewModel.getAnswer().isEmpty()) {
                        switch (chatBotViewModel.getType()) {
                            case ChatBotViewModel.KEYBOARD:
                                break;

                            case ChatBotViewModel.OPTIONS:
                                setChatAnswerOptions(chatBotViewModel.getAnswers());
                                break;
                        }
                        break;
                    }
                }
                else if (itemList.get(i) instanceof ChatBotAnswerViewModel) {
                    ChatBotAnswerViewModel chatBotAnswerViewModel = (ChatBotAnswerViewModel) itemList.get(i);
                    chatAdapter.addItem(chatAdapter.getItemCount(), new ItemInfo<>(chatBotAnswerViewModel, R.layout.layout_chat_right));
                }
            }
//            chatBotList = itemList;
            chatAdapter.notifyDataSetChanged();
            chatLayoutManager.scrollToPosition(chatAdapter.getItemCount() - 1);
        }
    }

    @Override
    public void viewItemDetail(Object viewModel, ChatViewHolder viewHolder) {
    }

    @Override
    public void showLoading() {
        if (rlProgress != null)
            rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (rlProgress != null)
            rlProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
    }

    @Override
    public void hideRetry() {
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    private void setupChatRecycler() {
        chatAdapter = new GenericRecyclerViewAdapter(this, new ArrayList<>()) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType) {
                    case R.layout.layout_chat_left:
                        return new ChatViewHolder(mLayoutInflater.inflate(R.layout.layout_chat_left, parent, false));

                    case R.layout.layout_chat_right:
                        return new ChatViewHolder(mLayoutInflater.inflate(R.layout.layout_chat_right, parent, false));

                    default:
                        return null;
                }
            }
        };

        rvChat.setAdapter(chatAdapter);
        chatLayoutManager = new LinearLayoutManager(this);
        rvChat.setLayoutManager(chatLayoutManager);
    }

    private void setupAnswerRecycler() {
        chatAnswerAdapter = new GenericRecyclerViewAdapter<ChatAnswerViewHolder>(getApplicationContext(), new ArrayList<>()) {
            @Override
            public ChatAnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ChatAnswerViewHolder(mLayoutInflater.inflate(R.layout.layout_chat_answer, parent, false));
            }
        };

        chatAnswerAdapter.setAreItemsClickable(true);
        chatAnswerAdapter.setOnItemClickListener((position, viewModel, holder) -> {
            answerChat(((ChatBotAnswerViewModel) viewModel.getData()).getAnswer());
        });

        rvAnswers.setAdapter(chatAnswerAdapter);
        rvAnswers.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setChatAnswerOptions(ArrayList<ChatBotAnswerViewModel> answerOptions) {
        ArrayList<ItemInfo> itemInfoList = new ArrayList<>();
        for (ChatBotAnswerViewModel answer: answerOptions) {
            itemInfoList.add(new ItemInfo<>(answer, R.layout.layout_chat_answer));
            showError(answer.getAnswer());
        }
        chatAnswerAdapter.setDataList(itemInfoList);
    }

    private void answerChat(String answer) {

    }

    private void showNext() {

    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ChatBotActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
