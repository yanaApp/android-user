package com.icaboalo.yana.domain.models.mapper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author icaboalo on 31/07/16.
 */
public class ModelDataMapper {
    final private Gson gson;

    public ModelDataMapper(Gson gson) {
        this.gson = gson;
    }

    /**
     * Transform an Object into another Object.
     *
     * @param object Object to be transformed.
     * @return Object if valid, otherwise null.
     */
    public Object transformToPresentation(Object object, Class presentationClass){
        if (object != null)
            if (!(object instanceof Boolean))
                return presentationClass.cast(gson.fromJson(gson.toJson(object), presentationClass));
        return object;
    }

    /**
     * Transform a List into a List
     *
     * @param list Objects to be transformed.
     * @return {@link List} if valid {@link List} otherwise null.
     */
    public List<Object> transformAllToPresentation(List<Object> list, Class presentationClass){
        List<Object> transformedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            transformedList.add(transformToPresentation(list.get(i), presentationClass));
        }
        return transformedList;
    }
}
