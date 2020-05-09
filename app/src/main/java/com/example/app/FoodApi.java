package com.example.app;

import android.widget.EditText;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodApi {
        @GET("api/v0/product/{code}.json")
        Call<ResFoodResponse> getFoodResponse(@Path("code") String code);


}
