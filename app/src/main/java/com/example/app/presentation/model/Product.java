package com.example.app.presentation.model;

import java.util.List;

public class Product {

    private String product_name;

    private String image_url;
    private String origins;
    private String allergens;
    private String nutriscore_grade;

    private List<Ingredients> ingredients;

    public Product(String product_name, String image_url, String origins, String allergens, String nutriscore_grade) {
        this.product_name = product_name;
        this.image_url = image_url;
        this.origins = origins;
        this.allergens = allergens;
        this.nutriscore_grade = nutriscore_grade;
    }

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
