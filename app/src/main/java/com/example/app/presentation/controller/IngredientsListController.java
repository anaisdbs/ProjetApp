package com.example.app.presentation.controller;

import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.Constant;
import com.example.app.Singletons;
import com.example.app.presentation.model.Ingredients;
import com.example.app.presentation.model.ResFoodResponse;
import com.example.app.presentation.view.IngredientsList;
import com.example.app.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientsListController {

    private SharedPreferences sharedPreferencesI;
    private Gson gsonI;
    private IngredientsList viewI;

    private String ancien_code;
    private String code2 = "3045320104127";


    public IngredientsListController(IngredientsList ingredientsList, Gson gson, SharedPreferences sharedPreferences) {
        this.viewI = ingredientsList;
        this.gsonI = gson;
        this.sharedPreferencesI = sharedPreferences;
    }


    public void onStart(){

        List<Ingredients> ingredientsList = getDataFromCache();
        ancien_code = sharedPreferencesI.getString(Constant.KEY_CODE, null);

      if(ingredientsList != null && ancien_code.equals(viewI.code)) {
              viewI.showList(ingredientsList);
          } else {
              makeApiCall();
          }
    }

    private void makeApiCall(){

        Call<ResFoodResponse> call = Singletons.getFoodApi().getFoodResponse(viewI.code);
        call.enqueue(new Callback<ResFoodResponse>() {
            @Override
            public void onResponse(Call<ResFoodResponse> call, Response<ResFoodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Ingredients> ingredientsList = response.body().getProduct().getIngredients();
                    saveCode();
                    saveList(ingredientsList);
                    viewI.showList(ingredientsList);
                } else{
                    viewI.showError();
                }
            }
            @Override
            public void onFailure(Call<ResFoodResponse> call, Throwable t) {
                viewI.showError();
            }
        });
    }

    private void saveList(List<Ingredients> ingredientsList) {
        String jsonString = gsonI.toJson(ingredientsList);
        sharedPreferencesI
                .edit()
                .putString(Constant.KEY_INGREDIENTS_LIST, jsonString)
                .apply();
    }

    private void saveCode(){
        sharedPreferencesI
                .edit()
                .putString(Constant.KEY_CODE, viewI.code)
                .apply();
    }



    private List<Ingredients> getDataFromCache(){

        String jsonIngredient = sharedPreferencesI.getString(Constant.KEY_INGREDIENTS_LIST, null);
        if(jsonIngredient == null){
            return null;
        }else {
            Type listType = new TypeToken<List<Ingredients>>() {
            }.getType();
            return gsonI.fromJson(jsonIngredient, listType);
        }
    }

    public void onItemClick(Ingredients ingredients) {
        viewI.navigateToDetails(ingredients);

    }
}
