package com.icaboalo.yana.presentation.screens.main.contact;

import com.icaboalo.yana.presentation.screens.GenericListView;
import com.icaboalo.yana.presentation.screens.main.contact.view_holder.ContactViewHolder;
import com.icaboalo.yana.presentation.view_model.ContactViewModel;

/**
 * @author icaboalo on 03/09/16.
 */
public interface ContactView extends GenericListView<ContactViewModel, ContactViewHolder> {

    void saveContactSuccess(ContactViewModel contactViewModel);
}
