package com.example.app.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.app.Singletons;
import com.example.app.R;
import com.example.app.presentation.controller.MainController;
import com.example.app.presentation.model.Ingredients;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MainController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController(
                this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        controller.onStart();
    }

    public void showError() {
        Toast.makeText(this,"Api Erreur", Toast.LENGTH_SHORT ).show();
    }

    public   void showList(List<Ingredients> ingredientsList){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // define an adapter
        mAdapter = new ListAdapter(ingredientsList, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Ingredients item) {
                controller.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void navigateToDetails(Ingredients ingredients) {
        Toast.makeText(this,"Click ok", Toast.LENGTH_SHORT ).show();

    }
}
