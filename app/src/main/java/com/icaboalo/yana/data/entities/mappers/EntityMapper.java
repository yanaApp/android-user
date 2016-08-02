package com.icaboalo.yana.data.entities.mappers;

import java.util.List;

/**
 * @author icaboalo on 31/07/16.
 */
public interface EntityMapper<D, R> {

    R transformToRealm(D item, Class dataClass);

    List<R> transformAllToRealm(List<D> list, Class dataClass);

    D tranformToDomain(R realmModel);

    List<D> transformAllToDomain(List<R> realmModelList);

    D tranformToDomain(R realmModel, Class dataClass);

    List<D> tranformAllToDomain(List<R> realmModelList, Class dataClass);
}
