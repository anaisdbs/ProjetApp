package com.example.app.presentation.controller;

import android.content.SharedPreferences;
import android.widget.EditText;

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


    private EditText MonCode;
  //  private String code3 = viewI.code;
    private String code2 = "3045320104127";


    public IngredientsListController(IngredientsList ingredientsList, Gson gson, SharedPreferences sharedPreferences) {
        this.viewI = ingredientsList;
        this.gsonI = gson;
        this.sharedPreferencesI = sharedPreferences;
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

        Call<ResFoodResponse> call = Singletons.getFoodApi().getFoodResponse(viewI.code);
        call.enqueue(new Callback<ResFoodResponse>() {
            @Override
            public void onResponse(Call<ResFoodResponse> call, Response<ResFoodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Ingredients> ingredientsList = response.body().getProduct().getIngredients();
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
