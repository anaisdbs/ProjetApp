package com.example.app.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.Constant;
import com.example.app.R;
import com.example.app.Singletons;
import com.example.app.presentation.controller.ErreurController;
import com.example.app.presentation.controller.MainController;
import com.example.app.presentation.model.Ingredients;


public class ErreurActivity extends AppCompatActivity {

    public Button retour_menu;
    private ErreurController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreur);

        retour_menu = findViewById(R.id.button_menu);

        controller = new ErreurController(
                this
        );
        controller.onStart();

        retour_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onButtonClick(retour_menu);
            }
        });
    }

    public void navigateToInfoMenu(){
        Intent myIntent = new Intent(ErreurActivity.this, MainActivity.class);
        ErreurActivity.this.startActivity(myIntent);
    }
}
