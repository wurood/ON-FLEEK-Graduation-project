package com.example.apiclient;

/**
 * Created by wurood on 11/28/2017.
 */

import java.util.List;

import retrofit.Call;
import retrofit2.http.POST;

/**
 * Created by Raquib-ul-Alam Kanak on 1/3/16.
 * Website: http://alamkanak.github.io
 */
public interface MyJsonService {


    @POST("/list")
    Call<List<Event>>loadRepo();

}