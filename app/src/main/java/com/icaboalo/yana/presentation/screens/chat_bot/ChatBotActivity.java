package com.icaboalo.yana.presentation.screens.chat_bot;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.icaboalo.yana.R;
import com.icaboalo.yana.other.ManagerPreference;
import com.icaboalo.yana.other.YanaPreferences;
import com.icaboalo.yana.presentation.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.factories.SnackbarFactory;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.chat_bot.view_holder.ChatAnswerViewHolder;
import com.icaboalo.yana.presentation.screens.chat_bot.view_holder.ChatLeftViewHolder;
import com.icaboalo.yana.presentation.screens.main.MainActivity;
import com.icaboalo.yana.presentation.view_model.ChatbotMessageViewModel;
import com.icaboalo.yana.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.icaboalo.yana.presentation.component.adapter.ItemInfo.LOADING;
import static com.icaboalo.yana.presentation.view_model.ChatbotMessageViewModel.DATE;
import static com.icaboalo.yana.presentation.view_model.ChatbotMessageViewModel.FINISH;
import static com.icaboalo.yana.presentation.view_model.ChatbotMessageViewModel.HOUR;
import static com.icaboalo.yana.presentation.view_model.ChatbotMessageViewModel.WEEK_DAYS;

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
    @BindView(R.id.rl_text_answer)
    RelativeLayout rlTextAnswer;
    @BindView(R.id.et_response)
    EditText etResponse;

    GenericRecyclerViewAdapter<ChatAnswerViewHolder> chatAnswerAdapter;
    GenericRecyclerViewAdapter chatAdapter;

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
    }

    //    TODO remove hideLoading()
    @Override
    public void renderItemList(List<ChatbotMessageViewModel> itemList) {
        hideLoading();
        if (!itemList.isEmpty()) {
            Collections.sort(itemList, (lhs, rhs) -> lhs.getId() - (rhs.getId()));
            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i) != null) {
                    ChatbotMessageViewModel chatbotMessageViewModel = itemList.get(i);
                    if (!addMessageToChat(chatbotMessageViewModel))
                        break;
                }
            }
            chatAdapter.notifyDataSetChanged();
            rvChat.getLayoutManager().scrollToPosition(chatAdapter.getItemCount() - 1);
            mChatBotPresenter.getNextMessage(itemList.get(itemList.size() -1).getNextQuestion());
        }
    }

    @Override
    public void viewItemDetail(ChatbotMessageViewModel viewModel, ChatLeftViewHolder viewHolder) {
    }

    @Override
    public void showLoading() {
        chatAdapter.addLoading();
    }

    @Override
    public void hideLoading() {
        chatAdapter.removeLoading();
    }

    @Override
    public void showRetry() {
    }

    @Override
    public void hideRetry() {
    }

    @Override
    public void showError(String message) {
        showSnackbarMessage(SnackbarFactory.TYPE_ERROR, rvChat, message, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void saveInfoSuccessful() {
    }

    @Override
    public void sendNextMessage(ChatbotMessageViewModel chatbotMessageViewModel) {
        addMessageToChat(chatbotMessageViewModel);
    }

    @Override
    public void errorSaveResponse() {
        chatAdapter.removeLoading();
    }

    @OnClick(R.id.bt_send)
    void sendAnswer() {
        if (!etResponse.getText().toString().isEmpty()) {
            answerChat(etResponse.getText().toString());
            etResponse.setText("");
        } else
            showError(getString(R.string.error_empty_field));
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setupChatRecycler() {
        chatAdapter = new GenericRecyclerViewAdapter(this, new ArrayList<>()) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType) {
                    case R.layout.layout_chat_left:
                        return new ChatLeftViewHolder(mLayoutInflater.inflate(R.layout.layout_chat_left, parent, false));

                    case R.layout.layout_chat_right:
                        return new ChatLeftViewHolder(mLayoutInflater.inflate(R.layout.layout_chat_right, parent, false));

                    case LOADING:
                        return new ViewHolder(mLayoutInflater.inflate(R.layout.layout_chat_loading, parent, false));

                    default:
                        return null;
                }
            }
        };

        rvChat.setAdapter(chatAdapter);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
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
            answerChat(((String) viewModel.getData()));
        });

        rvAnswers.setAdapter(chatAnswerAdapter);
        rvAnswers.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setChatAnswerOptions(ChatbotMessageViewModel chatbotMessageViewModel) {
        ArrayList<ItemInfo> itemInfoList = new ArrayList<>();
        for (String answer : chatbotMessageViewModel.getAnswers()) {
            itemInfoList.add(new ItemInfo<>(answer, R.layout.layout_chat_answer));
        }
        switch (chatbotMessageViewModel.getQuestionType()) {
            case HOUR:
                chatAnswerAdapter.setOnItemClickListener((position, viewModel, holder) -> {
                    new TimePickerDialog(ChatBotActivity.this, (view, hourOfDay, minute) ->
                            answerChat(Utils.transformTo24Hours(hourOfDay, minute)), 12, 0, true).show();
                    rvAnswers.setVisibility(View.GONE);
                });
                break;


            case WEEK_DAYS:
                chatAnswerAdapter.setOnItemClickListener((position, viewModel, holder) -> SelectDaysDialog.newInstance(days -> {
                    String answer = "";
                    for (int i = 0; i < days.size(); i++) {
                        if (i == 0)
                            answer += days.get(i);
                        else
                            answer += ", " + days.get(i);
                    }
                    answerChat(answer);
                    rvAnswers.setVisibility(View.GONE);
                }).show(getSupportFragmentManager(), ""));
                break;

            case DATE:
                chatAnswerAdapter.setOnItemClickListener((position, viewModel, holder) -> {
                    new DatePickerDialog(ChatBotActivity.this, (view, year, month, dayOfMonth) ->
                            answerChat(Utils.transformDateToText(dayOfMonth + "-" + (month + 1) + "-" + year, "dd-MM-yyyy",
                                    "MMMM dd, yy")), 1990, 0, 1).show();
                    rvAnswers.setVisibility(View.GONE);
                });
                break;

            case FINISH:
                chatAnswerAdapter.setOnItemClickListener((position, viewModel, holder) -> {
                    rvAnswers.setVisibility(View.GONE);
                    ManagerPreference.getInstance().set(YanaPreferences.INTRODUCTION_CHAT_COMPLETE, true);
                    startActivity(MainActivity.getCallingIntent(getApplicationContext()));
                    finish();
                });

            default:
                chatAnswerAdapter.setOnItemClickListener((position, viewModel, holder) -> {
                    answerChat((String) viewModel.getData());
                    rvAnswers.setVisibility(View.GONE);
                });
                break;
        }
        chatAnswerAdapter.setDataList(itemInfoList);
    }

    private void answerChat(String answer) {
        ChatbotMessageViewModel chatbotMessageViewModel = (ChatbotMessageViewModel) chatAdapter.getItem(chatAdapter.getItemCount() - 1).getData();
        chatAdapter.addItem(chatAdapter.getItemCount(), new ItemInfo<>(answer, R.layout.layout_chat_right));
        rvChat.scrollToPosition(chatAdapter.getItemCount() - 1);
        chatAdapter.addLoading();
        mChatBotPresenter.attemptSaveResponse(chatbotMessageViewModel, answer);
    }

    private boolean addMessageToChat(ChatbotMessageViewModel chatbotMessageViewModel) {
        chatAdapter.addItem(chatAdapter.getItemCount(), new ItemInfo<>(chatbotMessageViewModel, R.layout.layout_chat_left));
        rvChat.scrollToPosition(chatAdapter.getItemCount() - 1);
        if (chatbotMessageViewModel.isNeedsAnswer()) {
            if (chatbotMessageViewModel.getAnswer() != null && !chatbotMessageViewModel.getAnswer().isEmpty()) {
                chatAdapter.addItem(chatAdapter.getItemCount(), new ItemInfo<>(chatbotMessageViewModel.getAnswer(),
                        R.layout.layout_chat_right));
                return true;
            } else {
                switch (chatbotMessageViewModel.getQuestionType()) {
                    case ChatbotMessageViewModel.KEYBOARD:
                        rvAnswers.setVisibility(View.GONE);
                        rlTextAnswer.setVisibility(View.VISIBLE);
                        break;

                    default:
                        rlTextAnswer.setVisibility(View.GONE);
                        rvAnswers.setVisibility(View.VISIBLE);
                        setChatAnswerOptions(chatbotMessageViewModel);
                        break;
                }
                return false;
            }
        } else {
            mChatBotPresenter.getNextMessage(chatbotMessageViewModel.getNextQuestion());
            return true;
        }
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ChatBotActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
