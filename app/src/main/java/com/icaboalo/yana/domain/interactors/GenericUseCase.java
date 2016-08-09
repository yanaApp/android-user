package com.icaboalo.yana.domain.interactors;

import com.google.gson.Gson;
import com.icaboalo.yana.domain.executors.PostExecutionThread;
import com.icaboalo.yana.domain.executors.ThreadExecutor;
import com.icaboalo.yana.domain.models.mapper.ModelDataMapper;
import com.icaboalo.yana.domain.respository.Repository;

import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;

import io.realm.RealmQuery;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * @author icaboalo on 31/07/16.
 */
public class GenericUseCase {

    private final Repository mRepository;
    private final ModelDataMapper mModelDataMapper;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;
    private Subscription mSubscription;

    @Inject
    public GenericUseCase(Repository repository, PostExecutionThread postExecutionThread, ThreadExecutor threadExecutor) {
        mRepository = repository;
        mPostExecutionThread = postExecutionThread;
        mThreadExecutor = threadExecutor;
        mModelDataMapper = new ModelDataMapper(new Gson());
        mSubscription = Subscriptions.empty();
    }

    /**
     * Executes the current use case.
     *
     * @param UseCaseSubscriber The guy who will be listen to the observable build with .
     */
    @SuppressWarnings("unchecked")
    public void executeDynamicGetList(Subscriber UseCaseSubscriber, String url, Class domainClass, Class dataClass,
                                      Class presentationClass, boolean persist){
        mSubscription = mRepository.getListDynamically(url, domainClass, dataClass, persist)
                .map(collection -> mModelDataMapper.transformAllToPresentation(collection, presentationClass))
                .compose(applySchedulers())
                .compose(getLifecycle())
                .subscribe(UseCaseSubscriber);


    }


    @SuppressWarnings("unchecked")
    public void executeDynamicGetObject(Subscriber UseCaseSubscriber, String idColumnName, String url, int id, Class domainClass, Class dataClass,
                                        Class presentationClass, boolean persist){
        mSubscription = mRepository.getObjectDynamically(url, idColumnName, id, domainClass, dataClass, persist)
                .map(object -> mModelDataMapper.transformToPresentation(object, presentationClass))
                .compose(applySchedulers())
                .compose(getLifecycle())
                .subscribe(UseCaseSubscriber);
    }


    @SuppressWarnings("unchecked")
    public void executeDynamicPostObject(Subscriber UseCaseSubscriber, String url, HashMap<String, Object> keyValuePairs,
                                         Class domainClass, Class dataClass, Class presentationClass, boolean persist){
        mSubscription = mRepository.postObjectDynamically(url, keyValuePairs, domainClass, dataClass, persist)
                .map(object -> mModelDataMapper.transformToPresentation(object, presentationClass))
                .compose(applySchedulers())
                .compose(getLifecycle())
                .subscribe(UseCaseSubscriber);
    }


    @SuppressWarnings("unchecked")
    public void executeDynamicPostObject(Subscriber UseCaseSubscriber, String url, JSONObject keyValuePairs, Class domainClass,
                                         Class dataClass, Class presentationClass, boolean persist){
        mSubscription = mRepository.postObjectDynamically(url, keyValuePairs, domainClass, dataClass, persist)
                .map(object -> mModelDataMapper.transformToPresentation(object, presentationClass))
                .compose(applySchedulers())
                .compose(getLifecycle())
                .subscribe(UseCaseSubscriber);
    }


    @SuppressWarnings("unchecked")
    public void executeDynamicPostList(Subscriber UseCaseSubscriber, String url, HashMap<String, Object> keyValuePairs,
                                       Class domainClass, Class dataClass, Class presentationClass, boolean persist){
        mSubscription = mRepository.postListDynamically(url, keyValuePairs, domainClass, dataClass, persist)
                .map(object -> mModelDataMapper.transformToPresentation(object, presentationClass))
                .compose(applySchedulers())
                .compose(getLifecycle())
                .subscribe(UseCaseSubscriber);
    }


    @SuppressWarnings("unchecked")
    public void executeDynamicPutObject(Subscriber UseCaseSubscriber, String url, HashMap<String, Object> keyValuePairs,
                                        Class domainClass, Class dataClass, Class presentationClass, boolean persist){
        mSubscription = mRepository.putObjectDynamically(url, keyValuePairs, domainClass, dataClass, persist)
                .map(object -> mModelDataMapper.transformToPresentation(object, presentationClass))
                .compose(applySchedulers())
                .compose(getLifecycle())
                .subscribe(UseCaseSubscriber);
    }


    @SuppressWarnings("unchecked")
    public void executeSearchRealmList(Subscriber UseCaseSubscriber, RealmQuery query, Class domainClass,
                                       Class presentationClass){
        mSubscription = mRepository.searchRealmList(query, domainClass)
                .map(object -> mModelDataMapper.transformToPresentation(object, presentationClass))
                .compose(applySchedulers())
                .compose(getLifecycle())
                .subscribe(UseCaseSubscriber);
    }




    /**
     * Unsubscribes from current {@link Subscription}.
     */
    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }
    //--------------------------------------------------------------------------------//

    /**
     * Apply the default android schedulers to a observable
     *
     * @param <T> the current observable
     * @return the transformed observable
     */
    protected <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .unsubscribeOn(Schedulers.from(mThreadExecutor));
    }

    private Observable.Transformer mLifecycle;

    protected void setLifecycle(Observable.Transformer mLifecycle) {
        this.mLifecycle = mLifecycle;
    }

    protected <T> Observable.Transformer<T, T> getLifecycle() {
        return mLifecycle != null ? mLifecycle : o -> o;
    }
}
