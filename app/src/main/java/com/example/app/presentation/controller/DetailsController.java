
package com.example.app.presentation.controller;


import android.content.Intent;

import com.example.app.Constant;
import com.example.app.Singletons;
import com.example.app.presentation.model.Ingredients;
import com.example.app.presentation.view.DetailsActivity;

public class DetailsController {

    public Ingredients ingredients;

    private DetailsActivity view5;

    public DetailsController(DetailsActivity detailsActivity) {
        this.view5 = detailsActivity;
    }

    public void onStart(){
        Intent intent = view5.getIntent();
        String ingredientJson = intent.getStringExtra(Constant.KEY_INGREDIENTS);
        ingredients = Singletons.getGson().fromJson(ingredientJson, Ingredients.class);
    }


}
