package com.icaboalo.yana.presentation.screens;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.icaboalo.yana.domain.exception.DefaultErrorBundle;
import com.icaboalo.yana.domain.exception.ErrorBundle;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.exceptions.ErrorMessageFactory;

import java.util.List;

/**
 * @author icaboalo on 31/07/16.
 */
public abstract class GenericListPresenter<M, H extends RecyclerView.ViewHolder> extends BasePresenter{

    private GenericListView<M, H> mGenericListView;

    public GenericListPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public void setView(@NonNull GenericListView<M, H> genericListView){
        this.mGenericListView = genericListView;
    }

    public GenericListView<M, H> getGenericListView() {
        return mGenericListView;
    }

    /**
     * Initializes the presenter and loads items.
     */
    public void initialize(){
        loadItemList();
    }

    /**
     * Loads items.
     */
    public void loadItemList(){
        hideViewRetry();
        showViewLoading();
        getItemList();
    }

    public void onItemClicked(M model, H holder){
        mGenericListView.viewItemDetail(model, holder);
    }

    public void showViewLoading(){
        mGenericListView.showLoading();
    }

    public void hideViewLoading(){
     mGenericListView.hideLoading();
    }

    public void showViewRetry(){
        mGenericListView.showRetry();
    }

    public void hideViewRetry(){
        mGenericListView.hideRetry();
    }

    public void showErrorMessage(ErrorBundle errorBundle){
        mGenericListView.showError(ErrorMessageFactory.create(mGenericListView.getApplicationContext(), errorBundle.getException()));
    }

    public abstract void getItemList();

    public final class ItemListSubscriber extends DefaultSubscriber<List<M>> {
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
        public void onNext(List<M> m) {
            mGenericListView.renderItemList(m);
        }
    }


}
