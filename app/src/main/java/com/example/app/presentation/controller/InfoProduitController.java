package com.example.app.presentation.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;

import com.example.app.Constant;
import com.example.app.Singletons;
import com.example.app.presentation.model.Ingredients;
import com.example.app.presentation.model.Product;
import com.example.app.presentation.model.ResFoodResponse;
import com.example.app.presentation.view.InfoProduit;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoProduitController {

    private SharedPreferences sharedPreferences2;
    private Gson gson2;
    private InfoProduit view2;

    public String ancien_code;
    public String name;
    private String origins;
    private String allergens;
    private String nutriscore_grade;
    private String image_url;
    public Product product;
    private String jsonProduit;
    public String erreur;
    public String code;

    public InfoProduitController(InfoProduit infoProduit, Gson gson, SharedPreferences sharedPreferences) {
        this.view2 = infoProduit;
        this.gson2 = gson;
        this.sharedPreferences2 = sharedPreferences;
    }

    public void onStart(){

        Intent intent2 = view2.getIntent();
        code = intent2.getStringExtra(Constant.KEY_CODE_PRODUIT);

        product = getDataFromCacheProduit();

        ancien_code = sharedPreferences2.getString(Constant.KEY_CODE, null);

      if(product != null && ancien_code.equals(code)) {
          view2.showProduit();
          view2.showImage();
          } else {
          makeApiCall();
      }
    }

    private void makeApiCall(){

        Call<ResFoodResponse> call = Singletons.getFoodApi().getFoodResponse(code);
        call.enqueue(new Callback<ResFoodResponse>() {
            @Override
            public void onResponse(Call<ResFoodResponse> call, Response<ResFoodResponse> response) {
                if (response.isSuccessful() && response.body().getProduct() != null) {

                    name = response.body().getProduct().getProduct_name();
                    origins = response.body().getProduct().getOrigins();
                    allergens = response.body().getProduct().getAllergens();
                    nutriscore_grade =response.body().getProduct().getNutriscore_grade();
                    image_url = response.body().getProduct().getImage_url();

                    createProduit();
                    saveCode();
                    saveProduit(product);
                    List<Ingredients> ingredientsList = response.body().getProduct().getIngredients();
                    saveList(ingredientsList);

                    view2.showProduit();
                    view2.showImage();

                } else{
                    jsonProduit = null;
                    erreur = Constant.ERROR_PRODUCT;
                    view2.navigateToErreur();
                }
            }
            @Override
            public void onFailure(Call<ResFoodResponse> call, Throwable t) {
                erreur = Constant.ERROR_API;
                view2.navigateToErreur();

            }
        });
    }

    private void createProduit(){
        product = new Product(name,image_url,origins,allergens,nutriscore_grade);
    }

    private void saveCode(){
        sharedPreferences2
                .edit()
                .putString(Constant.KEY_CODE, code)
                .apply();
    }

    private void saveProduit(Product product){
        String jsonString = gson2.toJson(product);
        sharedPreferences2
                .edit()
                .putString(Constant.KEY_SAVE_PRODUIT, jsonString)
                .apply();
    }

    private void saveList(List<Ingredients> ingredientsList) {
        String jsonString = gson2.toJson(ingredientsList);
        sharedPreferences2
                .edit()
                .putString(Constant.KEY_INGREDIENTS_LIST, jsonString)
                .apply();
    }

    private Product getDataFromCacheProduit(){
        jsonProduit  = sharedPreferences2.getString(Constant.KEY_SAVE_PRODUIT, null);
        if (jsonProduit == null){
            return null;
        }else {
            Product productsave = gson2.fromJson(jsonProduit, Product.class);
            return productsave;
        }
    }

    public void onButtonClick(Button button){
        view2.navigateToIngredientsList();
    }
}
