package com.example.app.presentation.model;

import java.util.List;

public class Product {

    private String product_name;

    private String image_url;
    private String origins;
    private String allergens;
    private String nutriscore_grade;

    private List<Ingredients> ingredients;

    public String getImage_url() {
        return image_url;
    }

    public String getOrigins() {
        return origins;
    }

    public String getAllergens() {
        return allergens;
    }

    public String getNutriscore_grade() {
        return nutriscore_grade;
    }

    public String getProduct_name() {
        return product_name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }




}
