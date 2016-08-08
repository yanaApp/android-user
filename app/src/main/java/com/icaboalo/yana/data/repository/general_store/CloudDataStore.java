package com.icaboalo.yana.data.repository.general_store;

import android.content.Context;

import com.google.gson.Gson;
import com.icaboalo.yana.data.db.DataBaseManager;
import com.icaboalo.yana.data.entities.mappers.EntityMapper;
import com.icaboalo.yana.data.network.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author icaboalo on 07/08/16.
 */
public class CloudDataStore implements DataStore {

    private Context mContext;
    private RestApi mRestApi;
    private DataBaseManager mRealmManager;
    private EntityMapper mEntityDataMapper;
    private Class mDataClass;

    public CloudDataStore(RestApi restApi, DataBaseManager dataBaseManager,EntityMapper entityDataMapper) {
        mRestApi = restApi;
        mEntityDataMapper = entityDataMapper;
        mRealmManager = dataBaseManager;
        mContext = dataBaseManager.getContext().getApplicationContext();
    }

    private final Action1<Object> saveGenericToCache = object -> {
        Object mappedObject = mEntityDataMapper.transformToRealm(object, mDataClass);
        Observable<?> nObservable;
        if (mappedObject instanceof RealmObject)
            nObservable = mRealmManager.put((RealmObject) mappedObject, mDataClass);
        else if (mappedObject instanceof RealmModel)
            nObservable = mRealmManager.put((RealmModel) mappedObject, mDataClass);
        else
            try {
                nObservable = mRealmManager.put(new JSONObject(new Gson().toJson(object, mDataClass)), mDataClass);
            } catch (JSONException e) {
                e.printStackTrace();
                nObservable = Observable.error(e);
            }
        nObservable.subscribeOn(Schedulers.io()).subscribe(
                new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                }
        );

    };

    private final Action1<List> saveAllGenericToCache = list -> {
        List<RealmObject> realmObjectCollection = new ArrayList<>();
        realmObjectCollection.addAll(mEntityDataMapper.transformAllToRealm(list, mDataClass));
        mRealmManager.putAll(realmObjectCollection, mDataClass);
    };

    @Override
    public Observable<List> dynamicGetList(String url, Class domainClass, Class dataClass, boolean persist) {
        mDataClass = dataClass;
        return mRestApi.dynamicGetList(url)
                .doOnNext(list -> {
                    if (persist)
                        saveAllGenericToCache.call(list);
                })
                .map(entities -> mEntityDataMapper.transformAllToDomain(entities, domainClass));
    }

    @Override
    public Observable<?> dynamicGetObject(String url, String idColumnName, int itemId, Class domainClass, Class dataClass, boolean persist) {
        mDataClass = dataClass;
        return mRestApi.dynamicGetObject(url)
                .doOnNext(object -> {
                    if (persist)
                        saveGenericToCache.call(object);
                })
                .map(object -> mEntityDataMapper.transformToDomain(object, domainClass));
    }

    @Override
    public Observable<?> dynamicPostObject(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass, boolean persist) {
        return Observable.defer(() -> {
            return mRestApi.dynamicPostObject(url, RequestBody.create(MediaType.parse("application/json"),
                    new JSONObject(keyValuePairs).toString()))
                    .doOnNext(object -> {
                        if (persist)
                            saveGenericToCache.call(object);
                    })
                    .map(object -> mEntityDataMapper.transformToDomain(object, domainClass));
        });
    }

    @Override
    public Observable<?> dynamicPostObject(String url, JSONObject keyValuePairs, Class domainClass, Class dataClass, boolean persist) {
        return Observable.defer(() -> {
            return mRestApi.dynamicPostObject(url, RequestBody.create(MediaType.parse("application/json"),
                    keyValuePairs.toString()))
                    .doOnNext(object -> {
                        if (persist)
                            saveGenericToCache.call(object);
                    })
                    .map(object -> mEntityDataMapper.transformToDomain(object, domainClass));
        });
    }

    @Override
    public Observable<List> dynamicPostList(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass, boolean persist) {
        return Observable.defer(() -> {
            return mRestApi.dynamicPostList(url, RequestBody.create(MediaType.parse("application/json"),
                    new JSONObject(keyValuePairs).toString()))
                    .doOnNext(object -> {
                        if (persist)
                            saveAllGenericToCache.call(object);
                    })
                    .map(list -> mEntityDataMapper.transformAllToDomain(list, domainClass));
        });
    }

    @Override
    public Observable<?> dynamicPutObject(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass, boolean persist) {
        return Observable.defer(() -> {
            return mRestApi.dynamicPutObject(url, RequestBody.create(MediaType.parse("application/json"),
                    new JSONObject(keyValuePairs).toString()))
                    .doOnNext(object -> {
                        if (persist)
                            saveGenericToCache.call(object);
                    })
                    .map(object -> mEntityDataMapper.transformToDomain(object, domainClass));
        });
    }

    @Override
    public Observable<List> dynamicPutList(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass, boolean persist) {
        return Observable.defer(() -> {
            return mRestApi.dynamicPutList(url, RequestBody.create(MediaType.parse("application/json"),
                    new JSONObject(keyValuePairs).toString()))
                    .doOnNext(object -> {
                        if (persist)
                            saveAllGenericToCache.call(object);
                    })
                    .map(list -> mEntityDataMapper.transformAllToDomain(list, domainClass));
        });
    }

    @Override
    public Observable<?> dynamicDeleteAll(String url, Class dataClass, boolean persist) {
        return Observable.error(new Exception("cant delete all from cloud data store"));
    }

    @Override
    public Observable<List> searchDisk(String query, String column, Class domainClass, Class dataClass) {
        return Observable.error(new Exception("cant search disk in cloud data store"));
    }

    @Override
    public Observable<List> searchDisk(RealmQuery query, Class domainClass) {
        return Observable.error(new Exception("cant search disk in cloud data store"));
    }
}
