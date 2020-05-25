package com.example.app.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.Constant;
import com.example.app.R;
import com.example.app.presentation.controller.ErreurController;


public class ErreurActivity extends AppCompatActivity {

    public Button retour_menu;
    private ErreurController controller;
    private TextView textViewErreur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreur);

        retour_menu = findViewById(R.id.button_menu);
        textViewErreur = findViewById(R.id.textViewErreur);

        controller = new ErreurController(
                this
        );
        controller.onStart();

        typeErreur();

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

    public void typeErreur(){
        if (controller.erreur.equals(Constant.ERROR_API)){
            textViewErreur.setText(R.string.AlertApi);
        }else{
            textViewErreur.setText(R.string.AlertCode);
        }
    }
}
