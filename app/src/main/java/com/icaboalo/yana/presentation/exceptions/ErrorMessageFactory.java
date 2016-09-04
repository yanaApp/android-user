package com.icaboalo.yana.presentation.exceptions;

import android.content.Context;

import com.icaboalo.yana.R;
import com.icaboalo.yana.data.exception.DataNotFoundException;
import com.icaboalo.yana.data.exception.NetworkConnectionException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

    public ErrorMessageFactory() {
    }

    /**
     * Creates a String representing an error message.
     *
     * @param context   Context needed to retrieve string resources.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return {@link String} an error message.
     */
    public static String create(Context context, Exception exception){
        String message = context.getString(R.string.exception_message_generic);
        if (exception instanceof DataNotFoundException)
            message = context.getString(R.string.exception_message_user_not_found);
        else if (exception instanceof NetworkConnectionException)
            message = context.getString(R.string.exception_message_no_connection);
        else if (exception instanceof UnknownHostException)
            message = context.getString(R.string.exception_message_no_connection);
        else if (exception instanceof HttpException){
            try {
                message = new JSONObject(((HttpException) exception).response().errorBody().string()).getString("error_description");
            } catch (JSONException | IOException e) {
                try {
                    String fieldName = "";
                    JSONObject json = new JSONObject(((HttpException) exception).response().errorBody().string());
                    List<String> fieldNames = new ArrayList<>();
                    for (int i = 0; i < json.names().length(); i++) {
                        fieldName = json.names().optString(i);
                        if (fieldName.toLowerCase().contains("error"))
                            fieldNames.add(fieldName);
                    }
                    for (String string : fieldNames)
                        if (string.equalsIgnoreCase("error"))
                            fieldName = string;
                    if (!fieldName.equalsIgnoreCase("error"))
                        fieldName = fieldNames.get(0);
                    message = new JSONObject(((HttpException) exception).response().errorBody().string()).getString(fieldName);
                } catch (JSONException | IOException e1) {
                    message = "Error de red";
                }
            }
        }
        return message;
    }
}
