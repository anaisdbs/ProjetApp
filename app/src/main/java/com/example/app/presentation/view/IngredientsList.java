package com.example.app.presentation.view;

import android.content.Intent;
import android.os.Bundle;

import android.view.WindowManager;

import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.Constant;
import com.example.app.R;
import com.example.app.Singletons;
import com.example.app.presentation.controller.IngredientsListController;
import com.example.app.presentation.model.Ingredients;

import java.util.List;

public class IngredientsList extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public SearchView editsearch;
    private IngredientsListController controllerI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        controllerI = new IngredientsListController(this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        controllerI.onStart();

        createSearch();
    }

    private void createSearch(){
        editsearch = findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);
    }


    public void showError() {
        Toast.makeText(this,Constant.ERROR, Toast.LENGTH_SHORT ).show();
    }

    public void showList(List<Ingredients> ingredientsList){

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
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
        myIntent.putExtra(Constant.ERROR_NAME, controllerI.erreur);
        IngredientsList.this.startActivity(myIntent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) { //
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return false;
    }

}
