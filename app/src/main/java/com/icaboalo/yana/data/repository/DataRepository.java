package com.icaboalo.yana.data.repository;

import com.icaboalo.yana.data.repository.general_store.DataStoreFactory;
import com.icaboalo.yana.domain.respository.Repository;
import com.icaboalo.yana.util.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import io.realm.RealmQuery;
import rx.Observable;

/**
 * @author icaboalo on 07/08/16.
 */
public class DataRepository implements Repository {

    private final DataStoreFactory mDataStoreFactory;

    public DataRepository(DataStoreFactory dataStoreFactory) {
        mDataStoreFactory = dataStoreFactory;
    }

    @Override
    public Observable<List> getListDynamically(String url, Class domainClass, Class dataClass, boolean persist) {
        return mDataStoreFactory.dynamically(url, Utils.getDataMapper(dataClass), dataClass)
                .dynamicGetList(url, domainClass, dataClass, persist);
    }

    @Override
    public Observable<?> getObjectDynamically(String url, String idColumnName, int id, Class domainClass, Class dataClass,
                                              boolean persist) {
        return mDataStoreFactory.dynamically(url, idColumnName, id, Utils.getDataMapper(dataClass), dataClass)
                .dynamicGetObject(url, idColumnName, id, domainClass, dataClass, persist);
    }

    @Override
    public Observable<?> postObjectDynamically(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass,
                                               boolean persist) {
        return mDataStoreFactory.dynamically(url, Utils.getDataMapper(dataClass), dataClass)
                .dynamicPostObject(url, keyValuePairs, domainClass, dataClass, persist);
    }

    @Override
    public Observable<?> postObjectDynamically(String url, JSONObject keyValuePairs, Class domainClass, Class dataClass,
                                               boolean persist) {
        return mDataStoreFactory.dynamically(url, Utils.getDataMapper(dataClass), dataClass)
                .dynamicPostObject(url, keyValuePairs, domainClass, dataClass, persist);
    }

    @Override
    public Observable<?> postListDynamically(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass,
                                             boolean persist) {
        return mDataStoreFactory.dynamically(url, Utils.getDataMapper(dataClass), dataClass)
                .dynamicPostList(url, keyValuePairs, domainClass, dataClass, persist);
    }

    @Override
    public Observable<?> putObjectDynamically(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass,
                                              boolean persist) {
        return mDataStoreFactory.dynamically(url, Utils.getDataMapper(dataClass), dataClass)
                .dynamicPutObject(url, keyValuePairs, domainClass, dataClass, persist);
    }

    @Override
    public Observable<List> putListDynamically(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass,
                                               boolean persist) {
        return mDataStoreFactory.dynamically(url, Utils.getDataMapper(dataClass), dataClass)
                .dynamicPutList(url, keyValuePairs, domainClass, dataClass, persist);
    }

    @Override
    public Observable<?> deleteAllDynamically(String url, Class dataClass, boolean persist) {
        return mDataStoreFactory.dynamically(url, Utils.getDataMapper(dataClass), dataClass)
                .dynamicDeleteAll(url, dataClass, persist);
    }

    @Override
    public Observable<List> searchDisk(String query, String column, Class domainClass, Class dataClass) {
        return mDataStoreFactory.disk(Utils.getDataMapper(dataClass))
                .searchDisk(query, column, domainClass, dataClass);
    }

    @Override
    public Observable<List> searchRealmList(RealmQuery query, Class domainClass) {
        return mDataStoreFactory.disk(Utils.getDataMapper(domainClass)).searchDisk(query, domainClass);
    }
}
