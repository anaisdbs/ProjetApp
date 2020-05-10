package com.example.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.app.data.FoodApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static FoodApi foodApiInstance;
    private static SharedPreferences sharedPreferences;


    public static Gson getGson(){
        if(gsonInstance == null) {
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static FoodApi getFoodApi(){
        if(foodApiInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            foodApiInstance = retrofit.create(FoodApi.class);
        }
        return foodApiInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        if(sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("application_anais", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}
