package com.example.app.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.Constant;
import com.example.app.R;
import com.example.app.Singletons;
import com.example.app.presentation.controller.MainController;
import com.example.app.presentation.model.Ingredients;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private TextView txtDetail1;
    private TextView txtDetail2;
    private Float PourcentageMax;
    private Float PourcentageMin;
    private Integer PourcentageMaxInt;
    private Integer PourcentageMinInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txtDetail1 = findViewById(R.id.details_txt);
        txtDetail2 = findViewById(R.id.details_txt2);

        Intent intent = getIntent();
        String ingredientJson = intent.getStringExtra(Constant.KEY_INGREDIENTS); //if it's a string you stored.
        Ingredients ingredients = Singletons.getGson().fromJson(ingredientJson, Ingredients.class);
        
        showDetails(ingredients);
    }

    private void showDetails(Ingredients ingredients) {

            PourcentageMax = ingredients.getPercent_max();
            PourcentageMin = ingredients.getPercent_min();

        if (PourcentageMax != null) {

            PourcentageMaxInt = Math.round(ingredients.getPercent_max());
            txtDetail1.setText(String.valueOf(PourcentageMaxInt));

        } else {
            txtDetail1.setText("Le pourcentage max de l'ingrédient\n" +ingredients.getText()+ "\nest inconnu");
        }
        if (PourcentageMin != null) {

            PourcentageMinInt = Math.round(ingredients.getPercent_min());
            txtDetail2.setText(String.valueOf(PourcentageMinInt));

        } else {
            txtDetail2.setText("Le pourcentage min de l'ingrédient\n" +ingredients.getText()+ "\nest inconnu");
        }
    }
}
