package com.icaboalo.yana.data.db;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author icaboalo on 07/08/16.
 */
public class GeneralRealmManagerImpl implements DataBaseManager {

    private Realm mRealm;
    private Context mContext;

    public GeneralRealmManagerImpl(Context context) {
        mContext = context;
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<?> getById(String idColumnName, int userId, Class clazz) {
        return Observable.defer(() -> Observable.just(mRealm.where(clazz).equalTo(idColumnName, userId).findFirst()));
    }

    @Override
    public Observable<List> getAll(Class clazz) {
        return Observable.defer(() -> Observable.just(mRealm.where(clazz).findAll()));
    }

    @Override
    public Observable<?> put(RealmObject realmModel, Class dataClass) {
        if (realmModel != null)
            return Observable.defer(() -> {
                if (mRealm.isInTransaction())
                    mRealm.cancelTransaction();
                mRealm.beginTransaction();
                RealmObject result = mRealm.copyToRealmOrUpdate(realmModel);
                mRealm.commitTransaction();
                if (RealmObject.isValid(result))
                    return Observable.just(true);
                else
                    return Observable.error(new Exception("realmModel is invalid"));
            });
        return Observable.error(new Exception("realmModel cant be null"));
    }

    @Override
    public Observable<?> put(RealmModel realmModel, Class dataClass) {
        if (realmModel != null)
            return Observable.defer(() -> {
                if (mRealm.isInTransaction())
                    mRealm.cancelTransaction();
                mRealm.beginTransaction();
                RealmModel result = mRealm.copyToRealmOrUpdate(realmModel);
                mRealm.commitTransaction();
                if (RealmObject.isValid(result))
                    return Observable.just(true);
                else
                    return Observable.error(new Exception("realmModel is invalid"));
            });
        return Observable.error(new Exception("realmModel cant be null"));
    }

    @Override
    public Observable<?> put(JSONObject realmObject, Class dataClass) {
//        if (realmObject != null)
//            return Observable.defer(() -> {
//
//            });
        return Observable.error(new Exception("json cant be null"));
    }

    @Override
    public void putAll(List<RealmObject> realmModels, Class dataClass) {
        Observable.defer(() -> {
            if (mRealm.isInTransaction())
                mRealm.cancelTransaction();
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(realmModels);
            mRealm.commitTransaction();
            return Observable.from(realmModels);
        });
    }

    @Override
    public boolean isCached(int itemId, String columnId, Class clazz) {
        return false;
    }

    @Override
    public boolean isItemValid(int itemId, String columnId, Class clazz) {
        if (columnId.isEmpty())
            return false;
        Object realmObject = mRealm.where(clazz).equalTo(columnId, itemId).findFirst();
        return realmObject != null;
    }

    @Override
    public boolean areItemsValid(String destination) {
        return false;
    }

    @Override
    public Observable<Boolean> evictAll(Class clazz) {
        return Observable.defer(() -> {
            RealmResults nRealmResults = mRealm.where(clazz).findAll();
            boolean areDeleted = true;
            if (!nRealmResults.isEmpty()){
                if (mRealm.isInTransaction())
                    mRealm.cancelTransaction();
                mRealm.beginTransaction();
                areDeleted = nRealmResults.deleteAllFromRealm();
                mRealm.commitTransaction();
            }
            return Observable.just(areDeleted);
        });
    }

    @Override
    public void evict(RealmObject realmModel, Class clazz) {
        Observable.defer(() -> {
            if (mRealm.isInTransaction())
                mRealm.cancelTransaction();
            mRealm.beginTransaction();
            realmModel.deleteFromRealm();
            mRealm.commitTransaction();
            boolean isDeleted = !realmModel.isValid();
            return Observable.just(isDeleted);
        });
    }

    @Override
    public boolean evictById(long itemId, Class clazz) {
        return false;
    }

    @Override
    public Observable<?> evictCollection(List<Long> list, Class dataClass) {
        return null;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public Observable<List> getWhere(Class clazz, String query, String filterKey) {
        return Observable.defer(() -> Observable.just(mRealm.where(clazz).beginsWith(filterKey, query, Case.INSENSITIVE).findAll()));
    }

    @Override
    public Observable<List> getWhere(RealmQuery realmQuery) {
        return Observable.just(realmQuery.findAll());
    }
}
