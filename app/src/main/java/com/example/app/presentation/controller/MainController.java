package com.example.app.presentation.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.app.Constant;
import com.example.app.Singletons;
import com.example.app.data.FoodApi;
import com.example.app.presentation.model.Ingredients;
import com.example.app.presentation.model.ResFoodResponse;
import com.example.app.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity  view;


    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences) {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){

        List<Ingredients> ingredientsList = getDataFromCache();

       /* if(ingredientsList != null){
            view.showList(ingredientsList);
        }else{
        makeApiCall();
        réussir à vérifier que le code précédent est différent ou pas du nouveau code d'entrée
        afficher la liste du produit précédent si pas de d'internet au moment du clique sur le bouton "recherche" du menu
        }*/
        makeApiCall();

    }
    private void makeApiCall(){

        Call<ResFoodResponse> call = Singletons.getFoodApi().getFoodResponse("5038862136532");
        call.enqueue(new Callback<ResFoodResponse>() {
            @Override
            public void onResponse(Call<ResFoodResponse> call, Response<ResFoodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Ingredients> ingredientsList = response.body().getProduct().getIngredients();
                    saveList(ingredientsList);
                    view.showList(ingredientsList);
                } else{
                    view.showError();
                }
            }
            @Override
            public void onFailure(Call<ResFoodResponse> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<Ingredients> ingredientsList) {
        String jsonString = gson.toJson(ingredientsList);
        sharedPreferences
                .edit()
                .putString("jsonIngredientsList", jsonString)
                .apply();
    }

    private List<Ingredients> getDataFromCache(){
        String jsonIngredient = sharedPreferences.getString(Constant.KEY_INGREDIENTS_LIST, null);
        if(jsonIngredient == null){
            return null;
        }else {
            Type listType = new TypeToken<List<Ingredients>>() {
            }.getType();
            return gson.fromJson(jsonIngredient, listType);
        }
    } 
}
