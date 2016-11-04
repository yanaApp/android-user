package com.icaboalo.yana.data.db;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author icaboalo on 07/08/16.
 */
@Singleton
public class GeneralRealmManagerImpl implements DataBaseManager {

    private Realm mRealm;
    private Context mContext;

    @Inject
    public GeneralRealmManagerImpl(Context context) {
        mContext = context;
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<?> getById(String idColumnName, String userId, Class clazz) {
        return Observable.defer(() -> {
            mRealm = Realm.getDefaultInstance();
            if (idColumnName.equals("id")){
//                Log.d("REALM_RESULT", mRealm.where(clazz).equalTo(idColumnName, Integer.valueOf(userId)).findFirst().toString());
                return Observable.just(mRealm.where(clazz).equalTo(idColumnName, Integer.valueOf(userId)).findFirst());
            }
            else
                return Observable.just(mRealm.where(clazz).equalTo(idColumnName, userId).findFirst());
        });
    }

    @Override
    public Observable<List> getAll(Class clazz) {
        return Observable.defer(() -> {
            mRealm = Realm.getDefaultInstance();
            return Observable.just(mRealm.where(clazz).findAll());
        });
    }

    @Override
    public Observable<?> put(RealmObject realmModel, Class dataClass) {
        if (realmModel != null) {
//            return Observable.defer(() -> {
            mRealm = Realm.getDefaultInstance();
            RealmObject result = executeWriteOperationInRealm(mRealm, () -> mRealm.copyToRealmOrUpdate(realmModel));
            if (RealmObject.isValid(result))
                return Observable.just(true);
            else
                return Observable.error(new Exception("realmModel is invalid"));
//            });
        }
        return Observable.error(new Exception("realmModel cant be null"));
    }

    @Override
    public Observable<?> put(RealmModel realmModel, Class dataClass) {
        if (realmModel != null) {
//            return Observable.defer(() -> {
                mRealm = Realm.getDefaultInstance();
                RealmModel result = executeWriteOperationInRealm(mRealm, () -> mRealm.copyToRealmOrUpdate(realmModel));
                if (RealmObject.isValid(result))
                    return Observable.just(true);
                else
                    return Observable.error(new Exception("realmModel is invalid"));
//            });
        }
        return Observable.error(new Exception("realmModel cant be null"));
    }

    @Override
    public Observable<?> put(JSONObject realmObject, String idColumnName, Class dataClass) {
        if (realmObject != null) {
//            return Observable.defer(() -> {
                if (idColumnName == null || idColumnName == null)
                    return Observable.error(new Exception("could not find id!"));
                mRealm = Realm.getDefaultInstance();
                RealmModel result = executeWriteOperationInRealm(mRealm, () -> mRealm.createOrUpdateObjectFromJson(dataClass, realmObject));
                if (RealmObject.isValid(result)) {
                    return Observable.just(true);
                } else
                    return Observable.error(new Exception("RealmModel is invalid"));

//            });
        }
        return Observable.error(new Exception("json cant be null"));
    }

    @Override
    public void putAll(List<RealmObject> realmModels, Class dataClass) {
        Observable.defer(() -> {
            mRealm = Realm.getDefaultInstance();
            executeWriteOperationInRealm(mRealm, () -> mRealm.copyToRealmOrUpdate(realmModels));
            return Observable.from(realmModels);
        }).subscribeOn(Schedulers.immediate())
                //we need to use immediate instead of io,
                // since if we have created UI thread realm objects,
                // and we try to invoke this method.
                // Then this method would not use if IO is used,
                // since io creates a new thread for each subscriber.
                .subscribe(new PutAllSubscriberClass(realmModels));
    }

    @Override
    public boolean isCached(int itemId, String columnId, Class clazz) {
        if (columnId.isEmpty())
            return false;
        Object realmObject = Realm.getDefaultInstance().where(clazz).equalTo(columnId, itemId).findFirst();
        return realmObject != null;
    }

    @Override
    public boolean isItemValid(String itemId, String columnId, Class clazz) {
        if (columnId.isEmpty())
            return false;
        Object realmObject = null;
        if (columnId.equals("id"))
            realmObject = Realm.getDefaultInstance().where(clazz).equalTo(columnId, Integer.valueOf(itemId)).findFirst();
        else
            realmObject = Realm.getDefaultInstance().where(clazz).equalTo(columnId, itemId).findFirst();
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

    private void executeWriteOperationInRealm(Realm realm, Executor executor) {
        if (realm.isInTransaction()) {
            realm.cancelTransaction();
        }
        realm.beginTransaction();
        executor.run();
        realm.commitTransaction();
    }

    private <T> T executeWriteOperationInRealm(Realm realm, ExecuteAndReturn<T> executor) {
        T toReturnValue;
        if (realm.isInTransaction())
            realm.cancelTransaction();
        realm.beginTransaction();
        toReturnValue = executor.run();
        realm.commitTransaction();
        return toReturnValue;
    }

    private interface Executor {
        void run();
    }

    private interface ExecuteAndReturn<T> {
        T run();
    }

    private class PutAllSubscriberClass extends Subscriber<Object> {
        private final List<RealmObject> mRealmModels;

        public PutAllSubscriberClass(List<RealmObject> realmModels) {
            mRealmModels = realmModels;
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onNext(Object o) {
            Log.d("TAG", "all " + mRealmModels.getClass().getName() + "s added!");
        }
    }
}
