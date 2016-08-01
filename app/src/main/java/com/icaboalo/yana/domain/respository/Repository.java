package com.icaboalo.yana.domain.respository;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import io.realm.RealmQuery;
import rx.Observable;

/**
 * Interface that represents a Repository for getting Objects from the data layer.
 */
public interface Repository {

    Observable<List> getListDynamically(String url, Class domainClass, Class dataClass, boolean persist);

    Observable<?> getObjectDynamically(String url, int id, Class domainClass, Class dataClass, boolean persist);

    Observable<?> postObjectDynamically(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass,
                                        boolean persist);

    Observable<?> postObjectDynamically(String url, JSONObject keyValuePairs, Class domainClass, Class dataClass, boolean persist);

    Observable<?> postListDynamically(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass,
                                      boolean persist);

    Observable<?> deleteObjectDynamically(String url, Class domainClass, Class dataClass, boolean persist);

    Observable<?> putObjectDynamically(String url, HashMap<String, Object> keyValuePairs, Class domainClass, Class dataClass,
                                       boolean persist);

    Observable<List> searchRealmList(RealmQuery query, Class domainClass);
}
