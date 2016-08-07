package com.icaboalo.yana.ui.fragment;

import android.Manifest;
import android.app.Activity;
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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.icaboalo.yana.R;
import com.icaboalo.yana.io.ApiClient;
import com.icaboalo.yana.io.model.ContactApiModel;
import com.icaboalo.yana.realm.ContactModel;
import com.icaboalo.yana.ui.adapter.ContactRecyclerAdapter;
import com.icaboalo.yana.ui.fragment.dialog.AddContactDialog;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.RealmUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by icaboalo on 05/06/16.
 */
public class ContactsFragment extends Fragment implements AddContactDialog.OnDialogClickListener{

    RecyclerView mContactRecycler;
    private Realm mRealmInstance;

    public ContactsFragment() {
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContactRecycler = (RecyclerView) view.findViewById(R.id.contact_recycler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRealmInstance = Realm.getDefaultInstance();
        setupContactRecycler(RealmUtils.getContactsFromRealm(mRealmInstance));
    }

    @Override
    public void onStop() {
        super.onStop();
        mRealmInstance.close();
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
                            showDialog(name, phoneList);
                        }
                    }
                    c.close();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("REQUEST_CODE", "" + requestCode);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.d("INTENT", "pick contact");
                    Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(pickContact, 1);
                } else {

                }
                return;
            case 0:
                return;
        }
    }

    @Override
    public void onPositiveClick(DialogInterface dialogInterface, String contactName, String phoneNumber, String relation, boolean liveTogether) {
        ContactApiModel nContactModel = new ContactApiModel();
        nContactModel.setName(contactName);
        nContactModel.setPhoneNumber(phoneNumber);
        nContactModel.setRelation(relation);
        nContactModel.setLiveTogether(liveTogether);
        saveContactAPI(PrefUtils.getToken(getActivity()), nContactModel);
    }

    @Override
    public void onNegativeClick(DialogInterface dialogInterface) {

    }

    void setupContactRecycler(final ArrayList<ContactModel> contactList){
        final ContactRecyclerAdapter contactRecyclerAdapter = new ContactRecyclerAdapter(getActivity(), contactList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        mContactRecycler.setAdapter(contactRecyclerAdapter);
        mContactRecycler.setLayoutManager(linearLayoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(0, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction){
                    case ItemTouchHelper.END:
                        Toast.makeText(getActivity(), "Removed " + contactList.get(position).getName(), Toast.LENGTH_SHORT).show();
                        RealmUtils.removeContactFromRealm(mRealmInstance, contactList.get(position).getId());
                        contactRecyclerAdapter.notifyItemRemoved(position);
                        break;
                    case ItemTouchHelper.START:
                        Toast.makeText(getActivity(), "Notified " + contactList.get(position).getName(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        itemTouchHelper.attachToRecyclerView(mContactRecycler);
    }

    //TODO 7/08/2016 change to ContactApiModel to work
    void saveContactAPI(String token, ContactApiModel contact){
        Call<ContactModel> call = ApiClient.getApiService().saveContact(token, contact);
        call.enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {
                if (response.isSuccessful()){
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
                    setupContactRecycler(RealmUtils.getContactsFromRealm(mRealmInstance));
                    realm.close();
                } else {
                    try {
                        Log.d("RETROFIT_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactModel> call, Throwable t) {
                Log.d("RETROFIT_FAILURE", t.toString());
            }
        });
    }

    void checkForContactsPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Log.d("INTENT", "pick contact");
            Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(pickContact, 1);
        }
    }

    private void showDialog(String contactName, ArrayList<String> phoneNumber){
        AddContactDialog nAddContactDialog = AddContactDialog.newInstance(contactName, phoneNumber);
        nAddContactDialog.setCancelable(false);
        nAddContactDialog.show(getActivity().getSupportFragmentManager(), "Add new contact");
    }
}
