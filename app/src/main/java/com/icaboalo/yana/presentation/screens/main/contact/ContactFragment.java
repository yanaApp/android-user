package com.icaboalo.yana.presentation.screens.main.contact;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericListView;
import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.screens.main.contact.dialog.AddContactDialog;
import com.icaboalo.yana.presentation.screens.main.contact.dialog.AddContactDialog.OnDialogClickListener;
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
public class ContactFragment extends BaseFragment implements ContactView, OnDialogClickListener {

    @Inject
    ContactPresenter mContactPresenter;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Bind(R.id.rlRetry)
    RelativeLayout rlRetry;
    @Bind(R.id.rvContact)
    RecyclerView rvContact;
    @Bind(R.id.flNoContacts)
    FrameLayout flNoContacts;

    GenericRecyclerViewAdapter<GenericRecyclerViewAdapter.ViewHolder> mContactRecyclerAdapter;

    @Override
    public void initialize() {
        setHasOptionsMenu(true);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contacts, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_contact:
                checkForContactsPermission();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void renderItemList(List<ContactViewModel> itemList) {
        if (itemList != null && itemList.size() > 0){
            flNoContacts.setVisibility(View.GONE);
            ArrayList<ItemInfo> itemInfoList = new ArrayList<>();
            Collections.sort(itemList, (lhs, rhs) ->
                    lhs.getName().compareToIgnoreCase(rhs.getName()));
            for (ContactViewModel contact : itemList){
                Log.d("ITEM", contact.toString());
                itemInfoList.add(new ItemInfo<>(contact, ItemInfo.SECTION_ITEM));
            }
            mContactRecyclerAdapter.setDataList(itemInfoList);
        } else {
            flNoContacts.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void viewItemDetail(ContactViewModel viewModel, ContactViewHolder viewHolder) {
    }

    @Override
    public void saveContactSuccess(ContactViewModel contactViewModel) {
        mContactRecyclerAdapter.addItem(0, new ItemInfo<>(contactViewModel, ItemInfo.SECTION_ITEM));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == Activity.RESULT_OK){
                    Uri contactsData = data.getData();
                    Cursor c = getActivity().getContentResolver().query(contactsData, null, null, null, null);
                    if (c.moveToFirst()){
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        ArrayList<String> phoneList = new ArrayList<>();
                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getActivity().getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "+ id,
                                    null, null);
                            while (phones.moveToNext()){
                                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                                switch (type) {
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                        // do something with the Home number here...
                                        number += "  (HOME)";
                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                        // do something with the Mobile number here...
                                        number += "  (MOBILE)";

                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                        // do something with the Work number here...
                                        number += "  (WORK)";

                                        break;
                                }
                                phoneList.add(number);
                            }
                            phones.close();
                        } else {
                            Toast.makeText(getActivity(), "Selected contact doesn't has a saved phone number", Toast.LENGTH_SHORT).show();
                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        if (hasPhone.equals("1")){
                            showContactDialog(name, phoneList);
                        }
                    }
                    c.close();
                }
                break;
        }
    }

    @Override
    public void onPositiveClick(DialogInterface dialogInterface, String contactName, String phoneNumber, String relation, boolean liveTogether) {
        mContactPresenter.attemptSaveContact(contactName, phoneNumber, relation, liveTogether);
    }

    @Override
    public void onNegativeClick(DialogInterface dialogInterface) {
        dialogInterface.dismiss();
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

    private void checkForContactsPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }
        } else {
            Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(pickContact, 1);
        }
    }

    private void showContactDialog(String name, ArrayList<String> phoneNumber){
        AddContactDialog addContactDialog =  AddContactDialog.newInstance(name, phoneNumber);
        addContactDialog.setCancelable(false);
        addContactDialog.setDialogClickListener(this);
        addContactDialog.show(getActivity().getSupportFragmentManager(), "ADD_CONTACT");
    }
}
