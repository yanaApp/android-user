package com.icaboalo.yana.data.repository.general_store;

import android.content.Context;

import com.icaboalo.yana.data.db.DataBaseManager;
import com.icaboalo.yana.data.entities.mappers.EntityMapper;
import com.icaboalo.yana.data.network.RestApiImpl;
import com.icaboalo.yana.util.Utils;

/**
 * @author icaboalo on 07/08/16.
 */
public class DataStoreFactory {

    private final Context mContext;
    private final DataBaseManager mDataBaseManager;

    public DataStoreFactory(Context context, DataBaseManager dataBaseManager) {
        mContext = context;
        mDataBaseManager = dataBaseManager;
    }

    /**
     * Create {@link DataStore} .
     */
    public DataStore dynamically(String url, EntityMapper entityMapper, Class dataClass){
        if (!url.isEmpty())
            return new CloudDataStore(new RestApiImpl(), mDataBaseManager, entityMapper);
        else return null;
    }

    /**
     * Create {@link DataStore} from an id.
     */
    public DataStore dynamically(String url, String idColumnName, int id, EntityMapper entityDataMapper,
                                 Class dataClass) {
        if (url.isEmpty() && (mDataBaseManager.isItemValid(id, idColumnName, dataClass) || !Utils.isNetworkAvailable(mContext)))
            return new DiskDataStore(mDataBaseManager, entityDataMapper);
        else if (!url.isEmpty())
            return new CloudDataStore(new RestApiImpl(), mDataBaseManager, entityDataMapper);
        else return new DiskDataStore(mDataBaseManager, entityDataMapper);
    }

    /**
     * Creates a disk {@link DataStore}.
     */
    public DataStore disk(EntityMapper entityDataMapper) {
        return new DiskDataStore(mDataBaseManager, entityDataMapper);
    }

    /**
     * Creates a cloud {@link DataStore}.
     */
    public DataStore cloud(EntityMapper entityDataMapper) {
        return new CloudDataStore(new RestApiImpl(), mDataBaseManager, entityDataMapper);
    }


}
