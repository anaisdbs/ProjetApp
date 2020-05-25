package com.example.app.presentation.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.presentation.controller.DetailsController;
import com.example.app.presentation.model.Ingredients;


public class DetailsActivity extends AppCompatActivity {

    private TextView txtDetail1;
    private TextView txtDetail2;
    private TextView noming;
    private Float PourcentageMax;
    private Float PourcentageMin;
    private Integer PourcentageMaxInt;
    private Integer PourcentageMinInt;
    private String myIngredient;

    private DetailsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txtDetail1 = findViewById(R.id.details_txt);
        txtDetail2 = findViewById(R.id.details_txt2);
        noming = findViewById(R.id.nom_ing);

        controller = new DetailsController(
                this
        );
        controller.onStart();

        showName(controller.ingredients);
        showDetails(controller.ingredients);
    }

    private void showName(Ingredients ingredients){
        myIngredient = ingredients.getText().replaceAll("_","");
        noming.setText(myIngredient.substring(0,1).toUpperCase() + myIngredient.substring(1).toLowerCase());
    }

    private void showDetails(Ingredients ingredients) {

            PourcentageMax = ingredients.getPercent_max();
            PourcentageMin = ingredients.getPercent_min();

        if (PourcentageMax != null) {
            PourcentageMaxInt = Math.round(ingredients.getPercent_max());
            txtDetail1.setText((PourcentageMaxInt)+ " %");
        } else {
            txtDetail1.setText("Le pourcentage max de l'ingrédient\n" +ingredients.getText()+ "\nest inconnu");
        }

        if (PourcentageMin != null) {
            PourcentageMinInt = Math.round(ingredients.getPercent_min());
            txtDetail2.setText((PourcentageMinInt)+" %");
        } else {
            txtDetail2.setText("Le pourcentage min de l'ingrédient\n" +ingredients.getText()+ "\nest inconnu");
        }
    }
}
