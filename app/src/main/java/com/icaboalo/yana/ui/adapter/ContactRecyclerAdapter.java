package com.icaboalo.yana.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.realm.ContactModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by icaboalo on 05/06/16.
 */
public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ContactViewHolder> {

    Context mContext;
    ArrayList<ContactModel> mContactList;
    LayoutInflater mInflater;

    public ContactRecyclerAdapter(Context context, ArrayList<ContactModel> contactList) {
        this.mContext = context;
        this.mContactList = contactList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_contacts_adapter, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        ContactModel contact = mContactList.get(position);
        holder.mContactName.setText(contact.getName());
        holder.setValidated(contact.isValidated());
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{

        ImageView mIsValidated;
        TextView mContactName;

        public ContactViewHolder(View itemView) {
            super(itemView);
            mIsValidated = (ImageView) itemView.findViewById(R.id.is_contact_validated);
            mContactName = (TextView) itemView.findViewById(R.id.contact_name);
        }

        void setValidated(boolean isValidated){
            if (isValidated){
                Picasso.with(mContext).load(R.mipmap.ic_launcher).into(mIsValidated);
            } else {
                Picasso.with(mContext).load(R.drawable.ic_done_white_24dp).into(mIsValidated);
            }
        }
    }
}
