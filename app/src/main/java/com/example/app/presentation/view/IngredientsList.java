package com.example.app.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.Constant;
import com.example.app.R;
import com.example.app.Singletons;
import com.example.app.presentation.controller.IngredientsListController;
import com.example.app.presentation.controller.MainController;
import com.example.app.presentation.model.Ingredients;

import java.util.List;

public class IngredientsList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private IngredientsListController controllerI;
    public String code;
    public String ancien_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_list);

        Intent intent2 = getIntent();
        code = intent2.getStringExtra(Constant.KEY_CODE_PRODUIT); //if it's a string you stored.
        ancien_code = intent2.getStringExtra("baba");

        Toast.makeText(this, code, Toast.LENGTH_SHORT ).show();

        controllerI = new IngredientsListController(this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        controllerI.onStart();


    }

    public void showError() {
        Toast.makeText(this,"Api Erreur", Toast.LENGTH_SHORT ).show();
    }

    public void showList(List<Ingredients> ingredientsList){

        recyclerView = findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // define an adapter
        mAdapter = new ListAdapter(ingredientsList, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Ingredients item) {
                controllerI.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void navigateToDetails(Ingredients ingredients) {
        Intent myIntent = new Intent(IngredientsList.this, DetailsActivity.class);
        myIntent.putExtra(Constant.KEY_INGREDIENTS, Singletons.getGson().toJson(ingredients));
        IngredientsList.this.startActivity(myIntent);
    }

    public void navigateToErreur() {
        Intent myIntent = new Intent(IngredientsList.this, ErreurActivity.class);
        IngredientsList.this.startActivity(myIntent);
    }
}
