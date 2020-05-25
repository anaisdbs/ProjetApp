package com.example.app.presentation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.Constant;
import com.example.app.R;
import com.example.app.presentation.controller.MainController;


public class MainActivity extends AppCompatActivity {

    private MainController controller;
    public EditText coderentre;
    public Button recherche;
    public String coderentre_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recherche =  findViewById(R.id.button_recherche);
        coderentre = findViewById(R.id.mon_code_barre);

        controller = new MainController(
                this
        );
        controller.onStart();

        recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onButtonClick(recherche);
            }
        });
    }

    public void navigateToInfoProduit(){
        controller.recupererCode();
        Intent myIntent = new Intent(MainActivity.this, InfoProduit.class);
        myIntent.putExtra(Constant.KEY_CODE_PRODUIT, coderentre_string);
        MainActivity.this.startActivity(myIntent);
    }
}
