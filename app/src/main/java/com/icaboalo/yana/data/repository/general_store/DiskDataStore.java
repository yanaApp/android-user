package com.icaboalo.yana.data.repository.general_store;

import com.icaboalo.yana.data.db.DataBaseManager;
import com.icaboalo.yana.data.entities.mappers.EntityMapper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import io.realm.RealmQuery;
import rx.Observable;

/**
 * @author icaboalo on 08/08/16.
 */
public class DiskDataStore implements DataStore {

    private DataBaseManager mDataBaseManager;
    private EntityMapper mEntityMapper;

    /**
     * Construct a {@link DataStore} based file system data store.
     *
     * @param dataBaseManager A {@link DataBaseManager} to cache data retrieved from the api.
     */
    public DiskDataStore(DataBaseManager dataBaseManager, EntityMapper entityMapper) {
        mDataBaseManager = dataBaseManager;
        mEntityMapper = entityMapper;
    }

    @Override
    public Observable<List> dynamicGetList(String url, Class domainClass, Class dataClass, boolean persist) {
        return mDataBaseManager.getAll(dataClass)
                .map(list -> mEntityMapper.transformAllToDomain(list));
    }

    @Override
    public Observable<?> dynamicGetObject(String url, String idColumnName, int itemId, Class domainClass, Class dataClass, boolean persist) {
        return mDataBaseManager.getById(idColumnName, itemId, dataClass)
                .map(object -> mEntityMapper.transformToDomain(object));
    }

    @Override
    public Observable<?> dynamicPostObject(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass,
                                           boolean persist) {
        return Observable.defer(() -> mDataBaseManager.put(new JSONObject(keyValuePairs), dataClass)
                .map(object -> mEntityMapper.transformToDomain(object)));
    }

    @Override
    public Observable<?> dynamicPostObject(String url, JSONObject keyValuePairs, Class domainClass, Class dataClass,
                                           boolean persist) {
        return Observable.defer(() -> mDataBaseManager.put(keyValuePairs, dataClass)
                .map(object -> mEntityMapper.transformToDomain(object)));
    }

    @Override
    public Observable<List> dynamicPostList(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass, boolean persist) {
        return Observable.error(new Exception("cannot post list to cloud on disk data store"));
    }

    @Override
    public Observable<?> dynamicPutObject(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass, boolean persist) {
        return Observable.defer(() -> mDataBaseManager.put(new JSONObject(keyValuePairs), dataClass)
                .map(object -> mEntityMapper.transformToDomain(object)));
    }

    @Override
    public Observable<List> dynamicPutList(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass, boolean persist) {
        return Observable.error(new Exception("cannot put list to cloud on disk data store"));
    }

    @Override
    public Observable<?> dynamicDeleteAll(String url, Class dataClass, boolean persist) {
        return mDataBaseManager.evictAll(dataClass);
    }

    @Override
    public Observable<List> searchDisk(String query, String column, Class domainClass, Class dataClass) {
        return mDataBaseManager.getWhere(dataClass, query, column)
                .map(list -> mEntityMapper.transformAllToDomain(list));
    }

    @Override
    public Observable<List> searchDisk(RealmQuery query, Class domainClass) {
        return mDataBaseManager.getWhere(query)
                .map(list -> mEntityMapper.transformAllToDomain(list));
    }
}
