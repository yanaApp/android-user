package com.icaboalo.yana.domain.model.mapper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icaboalo on 22/07/16.
 */
public class ModelDataMapper {
    private final Gson mGson;

    public ModelDataMapper(Gson gson) {
        mGson = gson;
    }

    public Object transformToPresentation(Object object, Class presentationClass){
        if (object != null)
            if (!(object instanceof Boolean))
                return mGson.fromJson(mGson.toJson(object), presentationClass);
        return object;
    }

    public List<Object> transformAllToPresentation(List<Object> list, Class presentationClass){
        List<Object> transformedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            transformedList.add(transformToPresentation(list.get(i), presentationClass));
        }
        return transformedList;
    }
}
