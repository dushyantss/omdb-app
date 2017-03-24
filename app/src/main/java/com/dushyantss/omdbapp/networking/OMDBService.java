package com.dushyantss.omdbapp.networking;

import com.dushyantss.omdbapp.models.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dushyant Singh Shekhawat
 * on 24-03-2017.
 */

public interface OMDBService {
    @GET(".")
    Call<Model> search(@Query("t") String name, @Query("type") String type);
}
