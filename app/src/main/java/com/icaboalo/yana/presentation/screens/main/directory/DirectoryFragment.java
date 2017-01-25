package com.icaboalo.yana.presentation.screens.main.directory;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;
import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.component.adapter.GenericSwipeRecyclerAdapter;
import com.icaboalo.yana.presentation.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericListView;
import com.icaboalo.yana.presentation.screens.main.directory.view_holder.DirectoryViewHolder;
import com.icaboalo.yana.presentation.screens.view_model.PsychologistViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by icaboalo on 21/01/17.
 */

public class DirectoryFragment extends BaseFragment implements GenericListView<PsychologistViewModel, DirectoryViewHolder> {

    @Inject
    DirectoryPresenter mDirectoryPresenter;
    RecyclerView rvDirectory;
    private GenericSwipeRecyclerAdapter<DirectoryViewHolder> mDirectoryAdapter;
    String phoneNumber;

    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
        mDirectoryPresenter.setView(this);
        setupRecycler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rvDirectory = new RecyclerView(getActivity());
        return rvDirectory;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void renderItemList(List<PsychologistViewModel> itemList) {
        if (!itemList.isEmpty()) {
            ArrayList<ItemInfo> itemInfoList = new ArrayList<>();
            for (PsychologistViewModel psychologist : itemList) {
                itemInfoList.add(new ItemInfo<>(psychologist, R.layout.view_holder_directory));
            }
            mDirectoryAdapter.setDataList(itemInfoList);
        }
    }

    @Override
    public void viewItemDetail(PsychologistViewModel viewModel, DirectoryViewHolder viewHolder) {
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    @Override
    public Context getApplicationContext() {
        return MyApplication.getInstance().getApplicationContext();
    }

    private void setupRecycler() {
        mDirectoryAdapter = new GenericSwipeRecyclerAdapter<DirectoryViewHolder>(getActivity(), new ArrayList<>()) {
            @Override
            public DirectoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new DirectoryViewHolder(mLayoutInflater.inflate(R.layout.view_holder_directory, parent, false));
            }

            @Override
            public void onBindViewHolder(DirectoryViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
                holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.itemView.findViewById(R.id.bottom_layout));

                holder.btCall.setOnClickListener(v -> {
                    closeItem(position);
                    showError("Calling... " + ((PsychologistViewModel) getItem(position).getData()).getPhoneNumber());
                    phoneNumber = ((PsychologistViewModel) getItem(position).getData()).getPhoneNumber();
                    attemptCall();
                });

                holder.btLocation.setOnClickListener(v -> {
                    closeItem(position);
                    showError("Opening maps... " + ((PsychologistViewModel) getItem(position).getData()).getLocation());
                });

                mItemManger.bindView(holder.itemView, position);
            }

            @Override
            public int getSwipeLayoutResourceId(int position) {
                return R.id.swipeLayout;
            }
        };

        rvDirectory.setAdapter(mDirectoryAdapter);
        rvDirectory.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<PsychologistViewModel> bla = new ArrayList<>();
        bla.add(new PsychologistViewModel());
        bla.add(new PsychologistViewModel());
        bla.add(new PsychologistViewModel());
        bla.add(new PsychologistViewModel());
        bla.add(new PsychologistViewModel());
        bla.add(new PsychologistViewModel());
        bla.add(new PsychologistViewModel());
        bla.add(new PsychologistViewModel());
        bla.add(new PsychologistViewModel());
        bla.add(new PsychologistViewModel());

        renderItemList(bla);
    }

    private void attemptCall() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CALL_PHONE)) {
            } else {
                new AlertDialog.Builder(getActivity())
                        .setMessage("We need your authorization to make phone calls")
                        .setPositiveButton("Continue", (dialog, which) -> ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CALL_PHONE}, 99))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            }
        } else if (phoneNumber != null)
            callPhone(this.phoneNumber);
        else
            showError("Ocurrio un error en la operación");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 99:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (phoneNumber != null)
                        callPhone(this.phoneNumber);
                    else
                        showError("Ocurrio un error en la operación");
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void attemptMaps(String location) {

    }

    private void callPhone(String phoneNumber) {
        String uri = "tel: " + phoneNumber;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
        this.phoneNumber = null;
    }
}
