package com.icaboalo.yana.presentation.screens.main.contact;

import com.icaboalo.yana.data.entities.realm_models.action_plan.ContactRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.Contact;
import com.icaboalo.yana.presentation.screens.GenericListPresenter;
import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.screens.main.view_model.ContactViewModel;

import javax.inject.Inject;

/**
 * @author icaboalo on 31/08/16.
 */
public class ContactPresenter extends GenericListPresenter<ContactViewModel, GenericRecyclerViewAdapter.ViewHolder> {

    @Inject
    public ContactPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemList() {
        getGenericUseCase().executeDynamicGetList(new ItemListSubscriber(), "", Contact.class, ContactRealmModel.class,
                ContactViewModel.class, false);
    }
}
