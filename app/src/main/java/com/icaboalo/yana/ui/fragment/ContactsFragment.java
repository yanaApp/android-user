package com.icaboalo.yana.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.icaboalo.yana.io.MockClient;
import com.icaboalo.yana.io.model.ContactMockModel;
import com.icaboalo.yana.realm.ContactModel;
import com.icaboalo.yana.ui.adapter.ContactRecyclerAdapter;
import com.icaboalo.yana.util.VUtil;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by icaboalo on 05/06/16.
 */
public class ContactsFragment extends Fragment {

    RecyclerView mContactRecycler;

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
                Log.d("INTENT", "pick contact");
                Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(pickContact, 1);
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
                    Uri contatsData = data.getData();
                    Cursor c = getActivity().getContentResolver().query(contatsData, null, null, null, null);
                    if (c.moveToFirst()){
                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String cNumber = "";
                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getActivity().getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            cNumber = phones.getString(phones.getColumnIndex("data1"));
                            System.out.println("number is:"+cNumber);
                            phones.close();
                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        if (hasPhone.equals("1")){
                            ContactModel contact = new ContactModel();

                            contact.setName(name);
                            contact.setPhoneNumber(cNumber);

                            saveContactAPI(VUtil.getToken(getActivity()), contact);
                            Toast.makeText(getActivity(), name + " " + cNumber, Toast.LENGTH_SHORT).show();
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(contact);
                            realm.commitTransaction();
                        }
                    }
                    c.close();
                }
                break;
        }
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
                        removeContactFromRealm(contactList.get(position).getId());
                        //contactRecyclerAdapter.notifyItemRemoved(position);
                        break;
                    case ItemTouchHelper.START:
                        Toast.makeText(getActivity(), "Notified " + contactList.get(position).getName(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        itemTouchHelper.attachToRecyclerView(mContactRecycler);
    }

    ArrayList<ContactModel> getContactsFromRealm(){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ContactModel> query = realm.where(ContactModel.class);
        RealmResults<ContactModel> results =  query.findAll();

        Log.d("REALM_RESULTS", results.toString());

        ArrayList<ContactModel> contactList = new ArrayList<>();
        for (ContactModel contact: results){
            contactList.add(contact);
        }

        return contactList;
    }

    void removeContactFromRealm(int contactId){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ContactModel contact = realm.where(ContactModel.class).equalTo("id", contactId).findFirst();
        contact.deleteFromRealm();
        realm.commitTransaction();
    }

    void saveContactAPI(String token, ContactModel contact){
        Call<ContactModel> call = ApiClient.getApiService().saveContact(token, contact);
        call.enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {
                if (response.isSuccessful()){
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();
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

    void getContactsMock(){
        Call<ArrayList<ContactMockModel>> call = MockClient.getApiService().getContacts();
        call.enqueue(new Callback<ArrayList<ContactMockModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ContactMockModel>> call, Response<ArrayList<ContactMockModel>> response) {
                if (response.isSuccessful()){
                    ArrayList<ContactModel> contactList = new ArrayList<ContactModel>();
                    for (ContactMockModel contact: response.body()){
                        ContactModel model = new ContactModel();
                        model.setId(contact.getId());
                        model.setName(contact.getName());
                        model.setPhoneNumber(contact.getPhoneNumber());
                        model.setValidated(contact.isValidated());
                        contactList.add(model);
                    }
                    setupContactRecycler(contactList);
                } else {
                    try {
                        Log.d("RETROFIT_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ContactMockModel>> call, Throwable t) {

            }
        });
    }

}
