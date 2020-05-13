package com.example.app.presentation.controller;

import android.content.SharedPreferences;
import android.widget.Button;

import com.example.app.Constant;
import com.example.app.Singletons;
import com.example.app.presentation.model.Ingredients;
import com.example.app.presentation.model.Product;
import com.example.app.presentation.model.ResFoodResponse;
import com.example.app.presentation.view.InfoProduit;
import com.example.app.presentation.view.IngredientsList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoProduitController {

    private SharedPreferences sharedPreferences2;
    private Gson gson2;
    private InfoProduit view2;

    public String ancien_code;
    private String code2 = "3045320104127";
    public String name;
    public String origins;
    public String allergens;
    public String nutriscore_grade;
    public String image_url;



    public InfoProduitController(InfoProduit infoProduit, Gson gson, SharedPreferences sharedPreferences) {
        this.view2 = infoProduit;
        this.gson2 = gson;
        this.sharedPreferences2 = sharedPreferences;
    }


    public void onStart(){

        ancien_code = sharedPreferences2.getString(Constant.KEY_CODE, null);

      if(ancien_code.equals(view2.code)) {
          view2.showProduit();
          view2.showImage();
          } else {
          makeApiCall();
      }
    }

    private void makeApiCall(){

        Call<ResFoodResponse> call = Singletons.getFoodApi().getFoodResponse(view2.code);
        call.enqueue(new Callback<ResFoodResponse>() {
            @Override
            public void onResponse(Call<ResFoodResponse> call, Response<ResFoodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    name = response.body().getProduct().getProduct_name();
                    origins = response.body().getProduct().getOrigins();
                    allergens = response.body().getProduct().getAllergens();
                    nutriscore_grade =response.body().getProduct().getNutriscore_grade();
                    image_url = response.body().getProduct().getImage_url();
                    view2.showProduit();
                    view2.showImage();

                    List<Ingredients> ingredientsList = response.body().getProduct().getIngredients();
                    saveCode();
                    saveProduit();
                    saveList(ingredientsList);
                } else{
                    view2.showError();
                }
            }
            @Override
            public void onFailure(Call<ResFoodResponse> call, Throwable t) {
                view2.showError();
            }
        });
    }

    private void saveList(List<Ingredients> ingredientsList) {
        String jsonString = gson2.toJson(ingredientsList);
        sharedPreferences2
                .edit()
                .putString(Constant.KEY_INGREDIENTS_LIST, jsonString)
                .apply();
    }

    private void saveCode(){
        sharedPreferences2
                .edit()
                .putString(Constant.KEY_CODE, view2.code)
                .apply();
    }

    private void saveProduit(){
        sharedPreferences2
                .edit()
                .putString("nom", name)
                .apply();
        sharedPreferences2
                .edit()
                .putString("nom", origins)
                .apply();
        sharedPreferences2
                .edit()
                .putString("nom", allergens)
                .apply();
        sharedPreferences2
                .edit()
                .putString("nom", nutriscore_grade)
                .apply();
    }

    public void onButtonClick(Button button){
        view2.navigateToIngredientsList();
    }
}
