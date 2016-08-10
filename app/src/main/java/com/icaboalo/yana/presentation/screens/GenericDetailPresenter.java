package com.icaboalo.yana.presentation.screens;

import android.support.annotation.NonNull;

import com.icaboalo.yana.domain.exception.DefaultErrorBundle;
import com.icaboalo.yana.domain.exception.ErrorBundle;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.exceptions.ErrorMessageFactory;

/**
 * @author icaboalo on 31/07/16.
 */
public abstract class GenericDetailPresenter<M> extends BasePresenter{

    private GenericDetailView<M> mGenericDetailView;
    public int mItemId;

    public GenericDetailPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public void setView(@NonNull GenericDetailView<M> genericDetailView){
        this.mGenericDetailView = genericDetailView;
    }

    public void setItemId(int itemId) {
        mItemId = itemId;
    }

    public GenericDetailView<M> getGenericDetailView() {
        return mGenericDetailView;
    }

    public int getItemId() {
        return mItemId;
    }

    /**
     * Initializes the presenter by start retrieving item details.
     */
    public void initialize(int itemId){
        this.mItemId = itemId;
        loadItemDetails();
    }

    /**
     * Loads item details.
     */
    private void loadItemDetails(){
        hideViewRetry();
        showViewLoading();
        getItemDetails();
    }

    public void showViewLoading(){
        mGenericDetailView.showLoading();
    }

    public void hideViewLoading(){
        mGenericDetailView.hideLoading();
    }

    public void showViewRetry(){
        mGenericDetailView.showRetry();
    }

    public void hideViewRetry(){
        mGenericDetailView.hideRetry();
    }

    public void showErrorMessage(ErrorBundle errorBundle){
        mGenericDetailView.showError(ErrorMessageFactory.create(mGenericDetailView.getApplicationContext(),
                errorBundle.getException()));
    }

    public abstract void getItemDetails();

    public final class ItemDetailSubscriber extends DefaultSubscriber<M>{

        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
            showViewRetry();
            showErrorMessage(new DefaultErrorBundle((Exception) e));
            e.printStackTrace();
        }

        @Override
        public void onNext(M m) {
            mGenericDetailView.renderItem(m);
        }
    }

}
