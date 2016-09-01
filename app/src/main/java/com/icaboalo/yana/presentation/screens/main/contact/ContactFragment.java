package com.icaboalo.yana.presentation.screens.main.contact;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericListView;
import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.screens.main.contact.view_holder.ContactViewHolder;
import com.icaboalo.yana.presentation.screens.main.view_model.ContactViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 31/08/16.
 */
public class ContactFragment extends BaseFragment implements GenericListView<ContactViewModel, GenericRecyclerViewAdapter.ViewHolder> {

    @Inject
    ContactPresenter mContactPresenter;
//    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
//    @Bind(R.id.rlRetry)
    RelativeLayout rlRetry;
    @Bind(R.id.rvContact)
    RecyclerView rvContact;

    GenericRecyclerViewAdapter<GenericRecyclerViewAdapter.ViewHolder> mContactRecyclerAdapter;

    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
        mContactPresenter.setView(this);
        mContactPresenter.initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupContactRecycler();
    }

    @Override
    public void renderItemList(List<ContactViewModel> itemList) {
        ArrayList<ItemInfo> itemInfoList = new ArrayList<>();
        Collections.sort(itemList, (lhs, rhs) ->
                lhs.getName().compareToIgnoreCase(rhs.getName()));
        for (ContactViewModel contact : itemList){
            Log.d("ITEM", contact.toString());
            itemInfoList.add(new ItemInfo<>(contact, ItemInfo.SECTION_ITEM));
        }
        mContactRecyclerAdapter.setDataList(itemInfoList);
    }

    @Override
    public void viewItemDetail(ContactViewModel viewModel, GenericRecyclerViewAdapter.ViewHolder viewHolder) {
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
        if (rlRetry != null)
            rlRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        if (rlRetry != null)
            rlRetry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @Override
    public Context getApplicationContext() {
        return MyApplication.getInstance().getApplicationContext();
    }

    private void setupContactRecycler(){
        mContactRecyclerAdapter = new GenericRecyclerViewAdapter<GenericRecyclerViewAdapter.ViewHolder>(getApplicationContext(), new ArrayList<>()) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType){
                    default:
                        return new ContactViewHolder(mLayoutInflater.inflate(R.layout.item_contacts_adapter, parent, false));
                }
            }
        };
        mContactRecyclerAdapter.setAreItemsClickable(false);
        rvContact.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvContact.setAdapter(mContactRecyclerAdapter);
    }

}
