
package com.example.app.presentation.controller;

import android.content.Intent;
import android.widget.Button;

import com.example.app.Constant;
import com.example.app.presentation.view.ErreurActivity;

public class ErreurController {

    private ErreurActivity view4;
    public String erreur;

    public ErreurController(ErreurActivity erreurActivity) {
        this.view4 = erreurActivity;
    }

    public void onStart(){
        Intent intent2 = view4.getIntent();
        erreur = intent2.getStringExtra(Constant.ERROR_NAME);
    }

    public void onButtonClick(Button retour_menu){
        view4.navigateToInfoMenu();
    }

}
