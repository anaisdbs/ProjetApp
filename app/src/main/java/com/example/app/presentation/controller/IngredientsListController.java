package com.example.app.presentation.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import com.example.app.Constant;
import com.example.app.Singletons;
import com.example.app.presentation.model.Ingredients;
import com.example.app.presentation.model.ResFoodResponse;
import com.example.app.presentation.view.IngredientsList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientsListController {

    private SharedPreferences sharedPreferences3;
    private Gson gson3;
    private IngredientsList view3;
    private String jsonIngredient;
    public String erreur;
    public String code;
    public String ancien_code;


    public IngredientsListController(IngredientsList ingredientsList, Gson gson, SharedPreferences sharedPreferences) {
        this.view3 = ingredientsList;
        this.gson3 = gson;
        this.sharedPreferences3 = sharedPreferences;
    }

    public void onStart(){

        Intent intent2 = view3.getIntent();
        code = intent2.getStringExtra(Constant.KEY_CODE_PRODUIT);
        ancien_code = intent2.getStringExtra(Constant.KEY_CODE2);

        List<Ingredients> ingredientsList = getDataFromCache();

        if(ingredientsList != null && ancien_code.equals(code)) {
              view3.showList(ingredientsList);
          } else {
              makeApiCall();
        }
    }

    private void makeApiCall(){

        Call<ResFoodResponse> call = Singletons.getFoodApi().getFoodResponse(code);
        call.enqueue(new Callback<ResFoodResponse>() {
            @Override
            public void onResponse(Call<ResFoodResponse> call, Response<ResFoodResponse> response) {
                if (response.isSuccessful() && response.body().getProduct()!=null) {
                    if(response.body().getProduct().getIngredients()==null){ //si la liste d'ingrédient est vide on est redirigé vers la page d'erreur
                        List<Ingredients> ingredientsList = null;
                        saveList(ingredientsList);
                        getDataFromCache();
                        erreur = Constant.ERROR_PRODUCT;
                        view3.navigateToErreur();
                    }
                    else {
                        List<Ingredients> ingredientsList = response.body().getProduct().getIngredients();
                        saveList(ingredientsList);
                        erreur = Constant.ERROR_PRODUCT;
                        view3.showList(ingredientsList);
                    }

                } else{
                    view3.navigateToErreur();
                }
            }
            @Override
            public void onFailure(Call<ResFoodResponse> call, Throwable t) {
                erreur = Constant.ERROR_API;
                view3.navigateToErreur();
            }
        });
    }

    private void saveList(List<Ingredients> ingredientsList) {
        String jsonString = gson3.toJson(ingredientsList);
        sharedPreferences3
                .edit()
                .putString(Constant.KEY_INGREDIENTS_LIST, jsonString)
                .apply();
    }

    private List<Ingredients> getDataFromCache(){
        jsonIngredient = sharedPreferences3.getString(Constant.KEY_INGREDIENTS_LIST, null);
        if(jsonIngredient == null){
            return null;
        }else {
            Type listType = new TypeToken<List<Ingredients>>() {
            }.getType();
            return gson3.fromJson(jsonIngredient, listType);
        }
    }

    public void onItemClick(Ingredients ingredients) {
        view3.navigateToDetails(ingredients);
    }
}
