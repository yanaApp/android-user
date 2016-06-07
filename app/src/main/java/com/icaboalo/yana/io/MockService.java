package com.icaboalo.yana.io;

import com.icaboalo.yana.io.model.ContactMockModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by icaboalo on 07/06/16.
 */
public interface MockService {

    @GET("/contact.json")
    Call<ArrayList<ContactMockModel>> getContacts();
}
