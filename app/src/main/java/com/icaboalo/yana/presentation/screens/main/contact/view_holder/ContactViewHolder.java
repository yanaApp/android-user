package com.icaboalo.yana.presentation.screens.main.contact.view_holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.screens.view_model.ContactViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 31/08/16.
 */
public class ContactViewHolder extends GenericRecyclerViewAdapter.ViewHolder {

    @BindView(R.id.tvContactName)
    TextView tvContactName;
    @BindView(R.id.ivContactValidated)
    ImageView ivContactValidated;

    public ContactViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Object data, int position, boolean isEnabled) {
        super.bindData(data, position, isEnabled);
        Context context = MyApplication.getInstance().getApplicationContext();
        if (((ItemInfo) data).getData() instanceof ContactViewModel){
            ContactViewModel contact = (ContactViewModel) ((ItemInfo) data).getData();
            tvContactName.setText(contact.getName());
            Picasso.with(context).load(contact.isValidated() ? R.drawable.check_validated_64 : R.drawable.check_64)
                    .into(ivContactValidated);
        }
    }


}
