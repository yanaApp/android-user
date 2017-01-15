package com.icaboalo.yana.presentation.exceptions;

import android.content.Context;
import android.util.Log;

import com.icaboalo.yana.R;
import com.icaboalo.yana.data.exception.DataNotFoundException;
import com.icaboalo.yana.data.exception.NetworkConnectionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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
        if (exception instanceof NetworkConnectionException) {
            NetworkConnectionException networkConnectionException = (NetworkConnectionException) exception;
            if (networkConnectionException.getMessage().isEmpty())
                return context.getString(R.string.exception_message_no_connection);
            else return networkConnectionException.getMessage();
        } else if (exception instanceof UnknownHostException || exception instanceof SocketTimeoutException)
            return context.getString(R.string.exception_message_no_connection);
        else if (exception instanceof HttpException) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(((HttpException) exception).response().errorBody().string());
                return jsonObject.getString("error");
            } catch (IOException | JSONException e) {
                try {
                    if (jsonObject.has("error")) {
                        jsonObject = new JSONObject(((HttpException) exception).response().errorBody().string());
                        if (jsonObject.get("error") instanceof JSONObject) {
                            if (jsonObject.getJSONObject("error").has("message"))
                                return jsonObject.getJSONObject("error").getString("message");
                            else if (jsonObject.getJSONObject("error").has("error_description"))
                                return jsonObject.getJSONObject("error").getString("error_description");
                            else if (jsonObject.getJSONObject("error").has("error"))
                                return jsonObject.getJSONObject("error").getString("error");
                        } else if (jsonObject.has("error_description"))
                            return jsonObject.getString("error_description");
                        else if (jsonObject.get("error") instanceof String)
                            return jsonObject.getString("error");
                    }
                    else if (jsonObject.has("detail")) {
                        jsonObject = new JSONObject(((HttpException) exception).response().errorBody().string());
                        if (jsonObject.get("detail") instanceof StringJoiner)
                            return jsonObject.getString("detail");
                    }
                    else if (jsonObject.has("non_field_errors")) {
                        jsonObject = new JSONObject(((HttpException) exception).response().errorBody().string());
                        if (jsonObject.get("non_field_errors") instanceof JSONArray)
                            if (jsonObject.getJSONArray("non_field_errors").get(0) instanceof String)
                                return jsonObject.getJSONArray("non_field_errors").getString(0);
                    }
                } catch (JSONException | IOException ignored) {
                }
                return exception.getMessage();
            }
        }
//        String s = Arrays.toString(exception.getStackTrace()) + "\n" + exception.getMessage() + "\n"
//                + exception.getLocalizedMessage();
//        File file = new File(Constants.EXTERNAL_DIR, "/unknown_errors.txt");
//        if (!file.exists())
//            file.mkdir();
//        GenericUseCase.getInstance().writeToFile(file.getAbsolutePath(), s).subscribe();
        return context.getString(R.string.unknown_error);
    }
}
