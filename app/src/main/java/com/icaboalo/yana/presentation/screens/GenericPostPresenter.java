package com.icaboalo.yana.presentation.screens;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.icaboalo.yana.domain.exception.DefaultErrorBundle;
import com.icaboalo.yana.domain.exception.ErrorBundle;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.exceptions.ErrorMessageFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.adapter.rxjava.HttpException;

/**
 * @author icaboalo on 31/07/16.
 */
public abstract class GenericPostPresenter<M> extends BasePresenter{

    GenericPostView<M> mGenericPostView;

    public GenericPostPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    public void setView(@NonNull GenericPostView<M> genericPostView){
        this.mGenericPostView = genericPostView;
    }

    public GenericPostView<M> getGenericPostView() {
        return mGenericPostView;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public void showViewLoading(){
        mGenericPostView.showLoading();
    }

    public void hideViewLoading(){
        mGenericPostView.hideLoading();
    }

    public void showViewRetry(){
        mGenericPostView.showRetry();
    }

    public void hideViewRetry(){
        mGenericPostView.hideRetry();
    }

    public void showErrorMessage(ErrorBundle errorBundle){
        mGenericPostView.showError(ErrorMessageFactory.create(mGenericPostView.getApplicationContext(), errorBundle.getException()));
    }

    /**
     * Call showViewLoading() then execute Post Call.
     *
     * @param postBundle data to be posted.
     */
    public abstract void post(HashMap<String, Object> postBundle);

    public void postSuccess(M model){
        mGenericPostView.postSuccessful(model);
    }

    public final class PostSubscriber extends DefaultSubscriber<M> {

        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
            showViewRetry();
            if (TextUtils.isEmpty(getErrorMessage(e)))
                showErrorMessage(new DefaultErrorBundle((Exception) e));
            else
                mGenericPostView.showError(getErrorMessage(e));
            e.printStackTrace();
        }

        @Override
        public void onNext(M m) {
            postSuccess(m);
        }
    }

    private String getErrorMessage(Throwable e){
        String message = "";
        try {
            JSONObject json = new JSONObject(((HttpException) e).response().errorBody().string());
            message = (String) json.getJSONObject("error").get("message");
        } catch (JSONException | IOException e1) {
            e1.printStackTrace();
        }

        return message;
    }

}
