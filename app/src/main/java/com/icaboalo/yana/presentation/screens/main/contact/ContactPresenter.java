package com.icaboalo.yana.presentation.screens.main.contact;

import com.icaboalo.yana.data.entities.realm_models.action_plan.ContactRealmModel;
import com.icaboalo.yana.domain.exception.DefaultErrorBundle;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.Contact;
import com.icaboalo.yana.presentation.screens.GenericListPresenter;
import com.icaboalo.yana.presentation.screens.main.contact.view_holder.ContactViewHolder;
import com.icaboalo.yana.presentation.screens.view_model.ContactViewModel;
import com.icaboalo.yana.util.Constants;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * @author icaboalo on 31/08/16.
 */
public class ContactPresenter extends GenericListPresenter<ContactViewModel, ContactViewHolder> {

    @Inject
    public ContactPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemList() {
        getGenericUseCase().executeDynamicGetList(new ItemListSubscriber(), "", Contact.class, ContactRealmModel.class,
                ContactViewModel.class, false);
    }

    public void attemptSaveContact(String name, String phoneNumber, String relation, boolean liveTogether){
        showViewLoading();
        if (phoneNumber.contains("WORK")){
            phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 8);
        } else if (phoneNumber.contains("HOME")){
            phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 8);
        } else if (phoneNumber.contains("MOBILE")){
            phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 10);
        }
        HashMap<String, Object> contactBundle = new HashMap<>(4);
        contactBundle.put("name", name);
        contactBundle.put("phone_number", phoneNumber);
        contactBundle.put("relationship", relation);
        contactBundle.put("live_together", liveTogether);
        saveContact(contactBundle);
    }

    private void saveContact(HashMap<String, Object> contactBundle){
        getGenericUseCase().executeDynamicPostObject(new SaveContactSubscriber(), Constants.API_BASE_URL + "contact/",
                contactBundle, Contact.class, ContactRealmModel.class, ContactViewModel.class, true);
    }

    private class SaveContactSubscriber extends DefaultSubscriber<ContactViewModel>{
        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
            showViewRetry();
            showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override
        public void onNext(ContactViewModel contactViewModel) {
            ((ContactView) getGenericListView()).saveContactSuccess(contactViewModel);
        }
    }
}
